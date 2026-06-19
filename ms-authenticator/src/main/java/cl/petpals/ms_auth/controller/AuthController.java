package cl.petpals.ms_auth.controller;

import cl.petpals.ms_auth.dto.AuthRequestDto;
import cl.petpals.ms_auth.dto.AuthResponseDto;
import cl.petpals.ms_auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para registro y login de usuarios")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Registrar nuevo usuario", description = "Crea un usuario nuevo y retorna un JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o correo ya registrado")
    })
    @PostMapping("/registro")
    public ResponseEntity<AuthResponseDto> registrar(@Valid @RequestBody AuthRequestDto dto) {
        return ResponseEntity.status(201).body(authService.registrar(dto));
    }

    @Operation(summary = "Iniciar sesión", description = "Autentica al usuario y retorna un JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login exitoso"),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody AuthRequestDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
}
