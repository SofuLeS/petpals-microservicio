package cl.petpals.ms_pago.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaResponseDto {

    private Long id;
    private Long idDueno;
    private Long idCuidador;
    private String estadoReserva;
}
