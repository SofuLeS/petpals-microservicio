package com.Calendario.MicroservicioCalendario.repository;

import com.Calendario.MicroservicioCalendario.model.ModelCalendario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface RepositoryCalendario extends JpaRepository<ModelCalendario, Long> {

    // Para buscar los cuidadores disponibles un día específico
    List<ModelCalendario> findByFecha(LocalDate fecha);

    // 3 registros al azar en caso que se quiera consultar un dias disponibles
    @Query(value = "SELECT * FROM calendarios ORDER BY RAND() LIMIT 3", nativeQuery = true)
    List<ModelCalendario> findCuidadoresAleatorios();
}