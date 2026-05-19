package com.historial_petpals.repository;

import com.historial_petpals.model.Historial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialRepo extends JpaRepository<Historial, Long> {
    List<Historial> findByIdDueno(Integer idDueno);
    List<Historial> findByIdCuidador(Long idCuidador);
    List<Historial> findByIdMascota(Long idMascota);
    List<Historial> findByIdServicio(Long idServicio);





}
