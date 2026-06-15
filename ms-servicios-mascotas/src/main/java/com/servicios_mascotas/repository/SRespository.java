package com.servicios_mascotas.repository;

import com.servicios_mascotas.model.ServicioMascotas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SRespository extends JpaRepository<ServicioMascotas,Long> {

    List<ServicioMascotas> findByNombreServicioContainingIgnoreCase(String nombreServicio);
    List<ServicioMascotas> findByDescripcionContainingIgnoreCase(String descripcion);
}
