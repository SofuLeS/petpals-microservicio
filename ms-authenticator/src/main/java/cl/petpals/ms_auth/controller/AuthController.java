package cl.petpals.ms_auth.controller;

import cl.petpals.ms_auth.dto.AuthRequestDto;
import cl.petpals.ms_auth.dto.AuthResponseDto;
import cl.petpals.ms_auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registro")
    public ResponseEntity<AuthResponseDto> registrar(@RequestBody AuthRequestDto dto){
        return ResponseEntity.status(201).body(authService.registrar(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto dto){
        return ResponseEntity.ok(authService.login(dto));
    }
}
