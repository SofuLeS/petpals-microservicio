package cl.petpals.ms_pago.config;

import cl.petpals.ms_pago.modelo.EstadoPago;
import cl.petpals.ms_pago.modelo.MetodoPago;
import cl.petpals.ms_pago.modelo.Pago;
import cl.petpals.ms_pago.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PagoRepository pagoRepository;

    @Override
    public void run(String... args) {
        if (pagoRepository.count() > 0) {
            log.info("DataInitializer: La base de datos ya contiene datos.");
            return;
        }
        log.info("DataInitializer: BD vacía detectada, insertando datos de prueba...");

        Pago p1 = new Pago();
        p1.setIdReserva(1L); p1.setIdDueno(1L);
        p1.setMonto(new BigDecimal("15000")); p1.setMetodoPago(MetodoPago.EFECTIVO);
        p1.setEstadoPago(EstadoPago.COMPLETADO); p1.setFechaPago(LocalDateTime.now());
        p1.setDescripcion("Pago por servicio de cuidado básico");

        Pago p2 = new Pago();
        p2.setIdReserva(2L); p2.setIdDueno(2L);
        p2.setMonto(new BigDecimal("25000")); p2.setMetodoPago(MetodoPago.TRANSFERENCIA);
        p2.setEstadoPago(EstadoPago.PENDIENTE); p2.setFechaPago(LocalDateTime.now());
        p2.setDescripcion("Pago por servicio premium");

        Pago p3 = new Pago();
        p3.setIdReserva(3L); p3.setIdDueno(1L);
        p3.setMonto(new BigDecimal("18500")); p3.setMetodoPago(MetodoPago.TARJETA_DEBITO);
        p3.setEstadoPago(EstadoPago.PENDIENTE); p3.setFechaPago(LocalDateTime.now());
        p3.setDescripcion("Pago por cuidado de mascota exótica");

        Pago p4 = new Pago();
        p4.setIdReserva(4L); p4.setIdDueno(3L);
        p4.setMonto(new BigDecimal("12000")); p4.setMetodoPago(MetodoPago.TARJETA_CREDITO);
        p4.setEstadoPago(EstadoPago.REEMBOLSADO); p4.setFechaPago(LocalDateTime.now());
        p4.setDescripcion("Pago reembolsado por cancelación");

        pagoRepository.save(p1); pagoRepository.save(p2);
        pagoRepository.save(p3); pagoRepository.save(p4);

        log.info("DataInitializer: Pagos insertados correctamente. Total: {}", pagoRepository.count());
    }
}