package cl.petpals.ms_reservas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuidadorResponseDto {
    private Long id;
    private String nombre;
    private String apellido;
    private Boolean disponibilidad;
}
