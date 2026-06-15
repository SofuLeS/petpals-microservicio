package cl.petpals.ms_reservas.repository;

import cl.petpals.ms_reservas.model.EstadoReserva;
import cl.petpals.ms_reservas.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByEstadoReserva(EstadoReserva estadoReserva);

    List<Reserva> findByIdDueno(Long idDueno);
    List<Reserva> findByIdCuidador(Long idCuidador);

    //Buscar al cuidaror y su estado
    List<Reserva> findByIdCuidadorAndEstadoReserva(Long idCuidador, EstadoReserva estadoReserva);
    List<Reserva> findByFechaReservaBetween(LocalDate desde, LocalDate hasta);
    List<Reserva> findByIdMascota(Long idMascota);
}
