package cl.petpals.ms_auth.dto;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String nombre;
    private String correo;
    private String contrasena;
    private String rol;
}
