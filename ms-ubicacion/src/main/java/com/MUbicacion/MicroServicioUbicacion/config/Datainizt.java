package com.MUbicacion.MicroServicioUbicacion.config;

import com.MUbicacion.MicroServicioUbicacion.model.ModelUbicacion;
import com.MUbicacion.MicroServicioUbicacion.repository.RepositoryUbicacion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Datainizt implements CommandLineRunner {

    // Spring los inyectará automáticamente gracias a @RequiredArgsConstructor
    private final RepositoryUbicacion repositoryUbicacion;

    @Override
    public void run(String... args) throws Exception {

        if (repositoryUbicacion.count() > 0) {
            log.info("La base de datos ya tiene ubicaciones registradas.");
            return;
        }

        // Estructura sugerida: (id, idCuidador, ciudad, comuna, region)
        // Usamos null en el ID para que MySQL use el auto increment

        repositoryUbicacion.save(new ModelUbicacion(null, 101L, "Puente Alto", "santiago", "Metropolitana"));
        repositoryUbicacion.save(new ModelUbicacion(null, 102L, "Valparaíso", "Valparaiso", "V Región"));
        repositoryUbicacion.save(new ModelUbicacion(null, 103L, "Viña del Mar", "Valparaiso", "V Region"));
        repositoryUbicacion.save(new ModelUbicacion(null, 104L, "Maipu", "Santiago", "Metropolitana"));

        log.info("¡Completado! {} ubicaciones de prueba insertadas.", repositoryUbicacion.count());
    }
}