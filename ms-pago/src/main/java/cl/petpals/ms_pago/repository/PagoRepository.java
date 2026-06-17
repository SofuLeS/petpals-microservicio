package cl.petpals.ms_pago.repository;

import cl.petpals.ms_pago.modelo.EstadoPago;
import cl.petpals.ms_pago.modelo.MetodoPago;
import cl.petpals.ms_pago.modelo.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByIdReserva(Long idReserva);
    List<Pago> findByIdDueno(Long idDueno);
    List<Pago> findByEstadoPago(EstadoPago estadoPago);
    List<Pago> findByMetodoPago(MetodoPago metodoPago);
    List<Pago> findByMontoBetween(BigDecimal min, BigDecimal max);
}
