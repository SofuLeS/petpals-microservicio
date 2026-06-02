package com.MUbicacion.MicroServicioUbicacion.repository;

import com.MUbicacion.MicroServicioUbicacion.model.ModelUbicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RepositoryUbicacion extends JpaRepository<ModelUbicacion, Long> {

    // Buscar todas las ubicaciones que coincidan con una comuna
    List<ModelUbicacion> findByComunaIgnoreCase(String Comuna);

    // Buscar la ubicación específica de un cuidador
    ModelUbicacion findByIdCuidador(Long idCuidador);
}