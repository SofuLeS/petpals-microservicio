package cl.petpals.ms_auth.service;

import cl.petpals.ms_auth.Repository.UsuarioRepository;
import cl.petpals.ms_auth.dto.AuthRequestDto;
import cl.petpals.ms_auth.dto.AuthResponseDto;
import cl.petpals.ms_auth.modelo.Rol;
import cl.petpals.ms_auth.modelo.Usuario;
import cl.petpals.ms_auth.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponseDto registrar(AuthRequestDto dto){
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        usuario.setRol(Rol.valueOf(dto.getRol()));
        usuarioRepository.save(usuario);
        String token = jwtUtil.generarToken(usuario.getCorreo(), usuario.getRol().name());
        return new AuthResponseDto(token, usuario.getCorreo(), usuario.getRol().name());
    }

    public AuthResponseDto login(AuthRequestDto dto){
        Usuario usuario = usuarioRepository.findByCorreo(dto.getCorreo()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!passwordEncoder.matches(dto.getContrasena(), usuario.getContrasena())){
            throw new RuntimeException("Contraseña incorrecta");
        }
        String token = jwtUtil.generarToken(usuario.getCorreo(), usuario.getRol().name());
        return new AuthResponseDto(token, usuario.getCorreo(), usuario.getRol().name());
    }

}
