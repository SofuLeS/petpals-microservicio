package cl.petpals.ms_auth.config;

import cl.petpals.ms_auth.Repository.UsuarioRepository;
import cl.petpals.ms_auth.modelo.Rol;
import cl.petpals.ms_auth.modelo.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setNombre("Admin PetPals");
            admin.setCorreo("admin@petpals.cl");
            admin.setContrasena(passwordEncoder.encode("admin123"));
            admin.setRol(Rol.ADMIN);
            usuarioRepository.save(admin);

            Usuario dueno = new Usuario();
            dueno.setNombre("Dueño Demo");
            dueno.setCorreo("dueno@petpals.cl");
            dueno.setContrasena(passwordEncoder.encode("dueno123"));
            dueno.setRol(Rol.DUENO);
            usuarioRepository.save(dueno);

            log.info("✅ Usuarios iniciales creados: admin@petpals.cl / dueno@petpals.cl");
        }
    }
}
