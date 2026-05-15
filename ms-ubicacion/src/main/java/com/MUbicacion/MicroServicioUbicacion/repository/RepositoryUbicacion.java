
package com.MUbicacion.MicroServicioUbicacion.repository;

import com.MUbicacion.MicroServicioUbicacion.model.ModelUbicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
// porque extends? Asi heredamos los metodos basicos (CRUD)
@Repository
public interface RepositoryUbicacion extends JpaRepository<ModelUbicacion, Long> {

    // Buscar todas las ubicaciones que coincidan con una comuna
    List<ModelUbicacion> findByComunaIgnoreCase(String Comuna);

    // Buscar la ubicación específica de un cuidador
    ModelUbicacion findByIdCuidador(Long idCuidador);
    //porque devolvemos una lista  y luego no?, porque encuna comuna pueden haber muchos cuidadores pero el id solo le pertenece a uno.
}