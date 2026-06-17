package cl.petpals.mscuidadores.dto;

import jakarta.validation.constraints.*;
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

    @NotNull(message = "El telefono no puede estar vacio")
    @Min(value = 10000000L, message = "Telefono invalido")
    @Max(value = 99999999999L, message = "Telefono invalido")
    private Integer telefono;

    @NotBlank(message = "El correo no deberia de estar vacio")
    private String email;

    @Positive(message = "La edad no puede estar en un numero en negativo")
    @NotNull(message = "La edad no puede estar vacia")
    private int edad;

    @NotNull(message = "La id no puede ir vacia")
    private Long categoriaId;

    @Positive(message = "La edad no puede ser negativa")
    private Integer anosExperincia;

    @Positive(message = "Las mascotas cuidadas no pueden ser negativas")
    private Integer mascotasCuidadas;

    @NotNull(message = "La disponibilidad no puede ir vacia")
    private Boolean disponibilidad;
}
