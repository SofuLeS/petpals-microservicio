package com.Calendario.MicroservicioCalendario.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo de petición para registrar un bloque de disponibilidad en el calendario")
public class RequestCalendarioDTO {

    @NotNull(message = "El ID del cuidador es obligatorio")
    @Schema(description = "ID del cuidador asignado a este bloque", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idCuidador;

    @NotNull(message = "La fecha es obligatoria")
    @Schema(description = "Fecha de disponibilidad (YYYY-MM-DD)", example = "2026-06-20", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate fecha;

    @NotNull(message = "La hora de inicio es obligatoria")
    @Schema(description = "Hora de inicio del bloque (HH:mm:ss)", example = "09:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalTime horaInicio;

    @NotNull(message = "La hora de término es obligatoria")
    @Schema(description = "Hora de término del bloque (HH:mm:ss)", example = "18:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalTime horaFin;
}