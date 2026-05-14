package com.ResenasM.MicroServicioResenas.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ResenasRequestDTO {
    @NotNull(message = "El ID de la reserva es obligatorio")
    private Long idReserva;

    @NotNull(message = "El ID del dueño es obligatorio")
    private Long idDueno;

    @NotNull(message = "El ID del cuidador es obligatorio")
    private Long idCuidador;

    @Min(1) @Max(5)
    private Integer estrellas;

    @NotBlank(message = "El comentario no puede estar vacío")
    @Size(max = 100)
    private String comentarios;
}