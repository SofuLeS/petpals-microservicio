package com.Calendario.MicroservicioCalendario.repository;

import com.Calendario.MicroservicioCalendario.model.ModelCalendario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface RepositoryCalendario extends JpaRepository<ModelCalendario, Long> {

    // Buscar los cuidadores disponibles un día específico
    List<ModelCalendario> findByFecha(LocalDate fecha);

    // SOLUCIÓN: Consulta a través de la entidad Java. Spring se encarga de buscar la tabla correcta.
    @Query(value = "SELECT c FROM ModelCalendario c ORDER BY FUNCTION('RAND') ASC LIMIT 3")
    List<ModelCalendario> findCuidadoresAleatorios();
}