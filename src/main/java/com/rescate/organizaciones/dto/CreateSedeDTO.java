package com.rescate.organizaciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class CreateSedeDTO {

    @NotBlank(message = "El nombre de la sede es obligatorio")
    @Size(max = 150)
    private String nombre;

    @NotBlank(message = "El distrito es obligatorio")
    @Size(max = 60)
    private String distrito;

    @Size(max = 200)
    private String direccion;

    private BigDecimal latitud;
    private BigDecimal longitud;

    @Size(max = 60)
    private String horarioAtencion;

    private BigDecimal capacidadKg;
    private Boolean esPrincipal = false;

    public CreateSedeDTO() {}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    public String getHorarioAtencion() {
        return horarioAtencion;
    }

    public void setHorarioAtencion(String horarioAtencion) {
        this.horarioAtencion = horarioAtencion;
    }

    public BigDecimal getCapacidadKg() {
        return capacidadKg;
    }

    public void setCapacidadKg(BigDecimal capacidadKg) {
        this.capacidadKg = capacidadKg;
    }

    public Boolean getEsPrincipal() {
        return esPrincipal;
    }

    public void setEsPrincipal(Boolean esPrincipal) {
        this.esPrincipal = esPrincipal;
    }
}
