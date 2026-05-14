package cl.mastocas.petPal.mascotasDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MascotaRequestDTO {

    // no vamos a incluir id porque lo genera autom.
    // anotaciones
    // si falla terminariamos con datos sin sentido.


    @NotBlank(message = "El nombre de la mascota no puede estar vacio.")
    private String nombre;

    @NotBlank(message = "La raza debe ser obligatoria.")
    private String raza;

    @NotNull(message = "La edad es obligatoria.")
    @Positive(message = "La edad debe ser un numero mayor a 0.")
    private Integer edad;

    private String alergias; // Este lo vamos hacer opcional.

    @NotBlank(message = "El tipo de Mascotas debe ser obligatorio")
    private String TipoMascota;

    @NotNull(message = "El id del dueño es obligatorio.")
    private Long idDueno;
}