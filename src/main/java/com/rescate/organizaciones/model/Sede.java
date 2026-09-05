package com.rescate.organizaciones.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "sede")
public class Sede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sede_id")
    private Integer sedeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacion_id", nullable = false)
    @JsonIgnore
    private Organizacion organizacion;

    @NotBlank(message = "El nombre de la sede es obligatorio")
    @Size(max = 150)
    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @NotBlank(message = "El distrito es obligatorio")
    @Size(max = 60)
    @Column(name = "distrito", nullable = false, length = 60)
    private String distrito;

    @Size(max = 200)
    @Column(name = "direccion", length = 200)
    private String direccion;

    @Column(name = "latitud", precision = 10, scale = 7)
    private BigDecimal latitud;

    @Column(name = "longitud", precision = 10, scale = 7)
    private BigDecimal longitud;

    @Size(max = 60)
    @Column(name = "horario_atencion", length = 60)
    private String horarioAtencion;

    @Column(name = "capacidad_kg", precision = 10, scale = 2)
    private BigDecimal capacidadKg;

    @Column(name = "es_principal", nullable = false)
    private Boolean esPrincipal = false;

    public Sede() {}

    public Integer getSedeId() {
        return sedeId;
    }

    public void setSedeId(Integer sedeId) {
        this.sedeId = sedeId;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

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
