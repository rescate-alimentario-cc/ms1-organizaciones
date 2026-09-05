package com.rescate.organizaciones.repository;

import com.rescate.organizaciones.model.Organizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizacionRepository extends JpaRepository<Organizacion, Integer> {
    List<Organizacion> findByTipoAndActivo(String tipo, Boolean activo);
    List<Organizacion> findByTipo(String tipo);
    List<Organizacion> findByActivo(Boolean activo);
    Optional<Organizacion> findByRuc(String ruc);
}
