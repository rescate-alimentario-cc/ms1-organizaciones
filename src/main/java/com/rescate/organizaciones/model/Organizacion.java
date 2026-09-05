package com.rescate.organizaciones.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "organizacion")
public class Organizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organizacion_id")
    private Integer organizacionId;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 150)
    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @NotBlank(message = "El tipo es obligatorio")
    @Pattern(regexp = "DONANTE|ONG", message = "El tipo debe ser DONANTE o ONG")
    @Column(name = "tipo", nullable = false, length = 10)
    private String tipo;

    @NotBlank(message = "El RUC es obligatorio")
    @Size(min = 11, max = 11, message = "El RUC debe tener 11 dígitos")
    @Column(name = "ruc", nullable = false, unique = true, length = 11)
    private String ruc;

    @NotBlank(message = "La categoría es obligatoria")
    @Size(max = 30)
    @Column(name = "categoria", nullable = false, length = 30)
    private String categoria;

    @Size(max = 120)
    @Column(name = "email", length = 120)
    private String email;

    @Size(max = 20)
    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @OneToMany(mappedBy = "organizacion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("organizacion")
    private List<Sede> sedes = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (this.fechaRegistro == null) {
            this.fechaRegistro = LocalDate.now();
        }
        if (this.activo == null) {
            this.activo = true;
        }
    }

    public Organizacion() {}

    public Integer getOrganizacionId() {
        return organizacionId;
    }

    public void setOrganizacionId(Integer organizacionId) {
        this.organizacionId = organizacionId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public List<Sede> getSedes() {
        return sedes;
    }

    public void setSedes(List<Sede> sedes) {
        this.sedes = sedes;
    }
}
