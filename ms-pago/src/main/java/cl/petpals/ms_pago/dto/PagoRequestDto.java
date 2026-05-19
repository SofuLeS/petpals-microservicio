package cl.petpals.ms_pago.dto;

import cl.petpals.ms_pago.modelo.MetodoPago;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoRequestDto {

    @NotNull(message = "El id de reserva es obligatorio")
    private Long idReserva;

    @NotNull(message = "El id de dueño es obligatorio")
    private Long idDueno;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe de ser mayor a 0")
    private BigDecimal monto;

    @NotNull(message = "El metdo de pago es obligatorio")
    private MetodoPago metodoPago;

    private String descripcion;
}
