package com.rescate.organizaciones.controller;

import com.rescate.organizaciones.dto.CreateOrganizacionDTO;
import com.rescate.organizaciones.dto.CreateSedeDTO;
import com.rescate.organizaciones.model.Organizacion;
import com.rescate.organizaciones.model.Sede;
import com.rescate.organizaciones.service.OrganizacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@CrossOrigin(origins = "*", exposedHeaders = "X-Total-Count")
@Tag(name = "MS1 - Organizaciones y Sedes", description = "Endpoints de gestión para Donantes y ONGs")
public class OrganizacionController {

    private final OrganizacionService organizacionService;

    public OrganizacionController(OrganizacionService organizacionService) {
        this.organizacionService = organizacionService;
    }

    @GetMapping("/health")
    @Operation(summary = "Health check del microservicio", description = "Retorna el estado operativo de MS1")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "ms1-organizaciones");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/organizations")
    @Operation(summary = "Listar organizaciones",
            description = "Listado paginado. Filtra por tipo (DONANTE, ONG), estado activo y texto (nombre o RUC). El total va en la cabecera X-Total-Count")
    public ResponseEntity<List<Organizacion>> listOrganizations(
            @Parameter(description = "Tipo de organización: DONANTE o ONG")
            @RequestParam(required = false) String tipo,
            @Parameter(description = "Estado activo: true o false")
            @RequestParam(required = false) Boolean activo,
            @Parameter(description = "Texto a buscar en nombre o RUC")
            @RequestParam(required = false) String q,
            @Parameter(description = "Registros por página (1-500)")
            @RequestParam(defaultValue = "100") int limit,
            @Parameter(description = "Número de página, desde 0")
            @RequestParam(defaultValue = "0") int page) {
        Page<Organizacion> resultado = organizacionService.listOrganizaciones(tipo, activo, q, limit, page);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(resultado.getTotalElements()))
                .body(resultado.getContent());
    }

    @GetMapping("/organizations/{id}")
    @Operation(summary = "Obtener organización por ID")
    public ResponseEntity<Organizacion> getOrganizationById(@PathVariable Integer id) {
        return ResponseEntity.ok(organizacionService.getOrganizacionById(id));
    }

    @PostMapping("/organizations")
    @Operation(summary = "Registrar nueva organización", description = "Registra un Donante o una ONG con RUC único")
    public ResponseEntity<Organizacion> createOrganization(@Valid @RequestBody CreateOrganizacionDTO dto) {
        Organizacion created = organizacionService.createOrganizacion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/organizations/{id}/sites")
    @Operation(summary = "Listar sedes de una organización", description = "Retorna las sedes asociadas a una organización específica")
    public ResponseEntity<List<Sede>> listSitesByOrganization(@PathVariable Integer id) {
        return ResponseEntity.ok(organizacionService.listSedesByOrganizacion(id));
    }

    @PostMapping("/organizations/{id}/sites")
    @Operation(summary = "Agregar sede a una organización", description = "Crea una nueva sede física asociada a la organización")
    public ResponseEntity<Sede> createSite(@PathVariable Integer id, @Valid @RequestBody CreateSedeDTO dto) {
        Sede created = organizacionService.createSede(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
