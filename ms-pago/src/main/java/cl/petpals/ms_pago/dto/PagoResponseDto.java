package cl.petpals.ms_pago.dto;

import cl.petpals.ms_pago.modelo.EstadoPago;
import cl.petpals.ms_pago.modelo.MetodoPago;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoResponseDto {
    private Long id;
    private Long idReserva;
    private Long idDueno;
    private BigDecimal monto;
    private MetodoPago metodoPago;
    private EstadoPago estadoPago;
    private LocalDateTime fechaPago;
    private String descripcion;
}
