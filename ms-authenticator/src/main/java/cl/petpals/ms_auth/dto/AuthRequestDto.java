package cl.petpals.ms_auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Datos requeridos para autenticación o registro")
public class AuthRequestDto {

    @Schema(description = "Nombre del usuario", example = "Juan Pérez")
    private String nombre;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener formato válido")
    @Schema(description = "Correo electrónico del usuario", example = "juan@petpals.cl")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @Schema(description = "Contraseña del usuario", example = "secreto123")
    private String contrasena;

    @Schema(description = "Rol del usuario: ADMIN, DUENO o CUIDADOR", example = "DUENO")
    private String rol;
}
