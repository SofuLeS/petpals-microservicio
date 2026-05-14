package cl.petpals.ms_reservas.dto;

import cl.petpals.ms_reservas.model.EstadoReserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaResponseDto {

    private Long id;
    private Long idDueno;
    private Long idMascota;
    private Long idCuidador;
    private Long idServicio;
    private LocalDateTime fechaReserva;
    private EstadoReserva estadoReserva;
}
