package cl.petpals.ms_auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Respuesta tras autenticación exitosa")
public class AuthResponseDto {

    @Schema(description = "Token JWT generado")
    private String token;

    @Schema(description = "Correo del usuario autenticado")
    private String correo;

    @Schema(description = "Rol del usuario autenticado")
    private String rol;
}
