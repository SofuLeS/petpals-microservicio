package cl.petpals.mscuidadores.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuidadorRequestDto {

    @NotBlank(message = "El rut no debe de estar vacio")
    private String rut;

    @NotBlank(message = "El nombre no debe de estaer vacio")
    private String nombre;

    @NotBlank(message = "Los apellidos no deben de ir vacios")
    private String apellidos;

    @Positive(message = "El numero de telefono no debe estar en negativo")
    @NotNull(message = "El numero de telefenofo no puede estar vacio")
    private int telefono;

    @NotBlank(message = "El correo no deberia de estar vacio")
    private String email;

    @Positive(message = "La edad no puede estar en un numero en negativo")
    @NotNull(message = "La edad no puede estar vacia")
    private int edad;

    @NotNull(message = "La id no puede ir vacia")
    private Long categoriaId;

    @NotNull(message = "La disponibilidad no puede ir vacia")
    private Boolean disponibilidad;
}
