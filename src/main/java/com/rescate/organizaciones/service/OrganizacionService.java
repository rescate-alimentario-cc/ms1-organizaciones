package com.rescate.organizaciones.service;

import com.rescate.organizaciones.dto.CreateOrganizacionDTO;
import com.rescate.organizaciones.dto.CreateSedeDTO;
import com.rescate.organizaciones.model.Organizacion;
import com.rescate.organizaciones.model.Sede;
import com.rescate.organizaciones.repository.OrganizacionRepository;
import com.rescate.organizaciones.repository.SedeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrganizacionService {

    private final OrganizacionRepository organizacionRepository;
    private final SedeRepository sedeRepository;

    public OrganizacionService(OrganizacionRepository organizacionRepository, SedeRepository sedeRepository) {
        this.organizacionRepository = organizacionRepository;
        this.sedeRepository = sedeRepository;
    }

    /** Listado paginado y filtrable. limit se acota a 1..500 y page empieza en 0. */
    @Transactional(readOnly = true)
    public Page<Organizacion> listOrganizaciones(String tipo, Boolean activo, String q, int limit, int page) {
        Specification<Organizacion> spec = (root, query, cb) -> cb.conjunction();
        if (tipo != null && !tipo.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("tipo"), tipo.trim().toUpperCase()));
        }
        if (activo != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("activo"), activo));
        }
        if (q != null && !q.isBlank()) {
            String patron = "%" + q.trim().toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("nombre")), patron),
                    cb.like(root.get("ruc"), patron)));
        }
        int tamano = Math.max(1, Math.min(limit, 500));
        return organizacionRepository.findAll(spec, PageRequest.of(Math.max(page, 0), tamano, Sort.by("organizacionId")));
    }

    @Transactional(readOnly = true)
    public Organizacion getOrganizacionById(Integer id) {
        return organizacionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Organización no encontrada con ID: " + id));
    }

    @Transactional
    public Organizacion createOrganizacion(CreateOrganizacionDTO dto) {
        if (organizacionRepository.findByRuc(dto.getRuc()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una organización registrada con el RUC: " + dto.getRuc());
        }

        Organizacion org = new Organizacion();
        org.setNombre(dto.getNombre());
        org.setTipo(dto.getTipo().toUpperCase());
        org.setRuc(dto.getRuc());
        org.setCategoria(dto.getCategoria());
        org.setEmail(dto.getEmail());
        org.setTelefono(dto.getTelefono());
        org.setActivo(dto.getActivo() != null ? dto.getActivo() : true);

        return organizacionRepository.save(org);
    }

    @Transactional(readOnly = true)
    public List<Sede> listSedesByOrganizacion(Integer organizacionId) {
        // Validar que la organización exista
        getOrganizacionById(organizacionId);
        return sedeRepository.findByOrganizacionOrganizacionId(organizacionId);
    }

    @Transactional
    public Sede createSede(Integer organizacionId, CreateSedeDTO dto) {
        Organizacion org = getOrganizacionById(organizacionId);

        Sede sede = new Sede();
        sede.setOrganizacion(org);
        sede.setNombre(dto.getNombre());
        sede.setDistrito(dto.getDistrito());
        sede.setDireccion(dto.getDireccion());
        sede.setLatitud(dto.getLatitud());
        sede.setLongitud(dto.getLongitud());
        sede.setHorarioAtencion(dto.getHorarioAtencion());
        sede.setCapacidadKg(dto.getCapacidadKg());
        sede.setEsPrincipal(dto.getEsPrincipal() != null ? dto.getEsPrincipal() : false);

        return sedeRepository.save(sede);
    }
}
