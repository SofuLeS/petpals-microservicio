package c.ResenasM.MicroServicioResenas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo de petición para registrar o actualizar una reseña")
public class ResenaRequestDTO {

    @NotNull(message = "El ID de la reserva es obligatorio")
    @Schema(description = "ID de la reserva asociada a la reseña", example = "101", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idReserva;

    @NotNull(message = "El ID del dueño es obligatorio")
    @Schema(description = "ID del dueño que califica el servicio", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idDueno;

    @NotNull(message = "El ID del cuidador es obligatorio")
    @Schema(description = "ID del cuidador que recibe la calificación", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idCuidador;

    @Min(1) @Max(5)
    @Schema(description = "Cantidad de estrellas otorgadas (Rango de 1 a 5)", example = "5", allowableValues = {"1", "2", "3", "4", "5"})
    private Integer estrellas;

    @NotBlank(message = "El comentario no puede estar vacío")
    @Size(max = 100)
    @Schema(description = "Comentario u opinión detallada sobre el servicio", example = "Excelente servicio, muy recomendado.", maxLength = 100)
    private String comentarios;
}