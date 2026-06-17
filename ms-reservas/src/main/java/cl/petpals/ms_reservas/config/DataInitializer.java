package cl.petpals.ms_reservas.config;

import cl.petpals.ms_reservas.model.EstadoReserva;
import cl.petpals.ms_reservas.model.Reserva;
import cl.petpals.ms_reservas.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ReservaRepository reservaRepository;

    @Override
    public void run(String... args) {
        if (reservaRepository.count() > 0) {
            log.info("DataInitializer: La base de datos ya contiene datos.");
            return;
        }
        log.info("DataInitializer: BD vacía detectada, insertando datos de prueba...");

        reservaRepository.save(new Reserva(null, 1L, 1L, 1L, 1L,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(3),
                EstadoReserva.PENDIENTE));

        reservaRepository.save(new Reserva(null, 2L, 2L, 1L, 2L,
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(4),
                EstadoReserva.ACEPTADA));

        reservaRepository.save(new Reserva(null, 1L, 3L, 2L, 1L,
                LocalDateTime.now().plusDays(5),
                LocalDateTime.now().plusDays(7),
                EstadoReserva.PENDIENTE));

        reservaRepository.save(new Reserva(null, 3L, 4L, 3L, 3L,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2),
                EstadoReserva.FINALIZADA));

        log.info("DataInitializer: Reservas insertadas correctamente. Total: {}", reservaRepository.count());
    }
}