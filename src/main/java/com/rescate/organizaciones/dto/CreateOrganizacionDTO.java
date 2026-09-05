package com.rescate.organizaciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateOrganizacionDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 150)
    private String nombre;

    @NotBlank(message = "El tipo es obligatorio")
    @Pattern(regexp = "DONANTE|ONG", message = "El tipo debe ser DONANTE o ONG")
    private String tipo;

    @NotBlank(message = "El RUC es obligatorio")
    @Size(min = 11, max = 11, message = "El RUC debe tener 11 dígitos")
    private String ruc;

    @NotBlank(message = "La categoría es obligatoria")
    @Size(max = 30)
    private String categoria;

    @Size(max = 120)
    private String email;

    @Size(max = 20)
    private String telefono;

    private Boolean activo = true;

    public CreateOrganizacionDTO() {}

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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
