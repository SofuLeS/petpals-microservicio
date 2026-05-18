package cl.petpals.ms_reservas.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaRequestDto {

    @NotNull(message = "El ID dueño es obligatorio")
    private Long idDueno;

    @NotNull(message = "El id de mascota es obligatorio")
    private Long idMascota;

    @NotNull(message = "EL id de cuidador es obligatorio")
    private Long idCuidador;

    @NotNull(message = "El id de servicio es obligatorio")
    private Long idServicio;

    @NotNull(message = "La fecha es obligatoria")
    @FutureOrPresent(message = "La fecha no puede estar en pasado")
    private LocalDateTime fechaReserva;

    @NotNull(message = "La fecha de finalizacion obligatoria")
    @FutureOrPresent(message = "La fecha no puede estar en pasado")
    private LocalDateTime fechaFinReserva;

}
