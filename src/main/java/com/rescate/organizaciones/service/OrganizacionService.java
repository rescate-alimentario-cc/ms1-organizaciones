package com.rescate.organizaciones.service;

import com.rescate.organizaciones.dto.CreateOrganizacionDTO;
import com.rescate.organizaciones.dto.CreateSedeDTO;
import com.rescate.organizaciones.model.Organizacion;
import com.rescate.organizaciones.model.Sede;
import com.rescate.organizaciones.repository.OrganizacionRepository;
import com.rescate.organizaciones.repository.SedeRepository;
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

    @Transactional(readOnly = true)
    public List<Organizacion> listOrganizaciones(String tipo, Boolean activo) {
        if (tipo != null && activo != null) {
            return organizacionRepository.findByTipoAndActivo(tipo.toUpperCase(), activo);
        } else if (tipo != null) {
            return organizacionRepository.findByTipo(tipo.toUpperCase());
        } else if (activo != null) {
            return organizacionRepository.findByActivo(activo);
        }
        return organizacionRepository.findAll();
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
