package cl.petpals.mscuidadores.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuidadorResponseDto {

    private Long id;
    private String rut;
    private String nombre;
    private String apellidos;
    private int telefono;
    private String email;
    private int edad;
    private String nombreCategoria;
    private Boolean disponibilidad;

}
