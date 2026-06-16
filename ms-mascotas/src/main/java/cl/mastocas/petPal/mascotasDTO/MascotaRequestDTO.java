package cl.mastocas.petPal.mascotasDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo de petición para registrar o actualizar una mascota")
public class MascotaRequestDTO {

    @NotBlank(message = "El nombre de la mascota es obligatorio")
    @Size(max = 50)
    @Schema(description = "Nombre de la mascota", example = "Toby", maxLength = 50)
    private String nombre;

    @NotBlank(message = "El tipo de mascota es obligatorio")
    @Schema(description = "Tipo o especie de la mascota (Perro, Gato, etc.)", example = "Perro")
    private String tipoMascota;

    @Size(max = 50)
    @Schema(description = "Raza de la mascota", example = "Golden Retriever")
    private String raza;

    @Min(value = 0, message = "La edad no puede ser negativa")
    @Schema(description = "Edad de la mascota en años", example = "3")
    private Integer edad;

    @Size(max = 100, message = "El texto de alergias no puede superar los 100 caracteres")
    @Schema(description = "Alergias o condiciones médicas especiales de la mascota", example = "Alergia al gluten y medicamentos específicos")
    private String alergias;

    @NotNull(message = "El ID del dueño es obligatorio")
    @Schema(description = "ID del dueño al que pertenece la mascota", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idDueno;
}