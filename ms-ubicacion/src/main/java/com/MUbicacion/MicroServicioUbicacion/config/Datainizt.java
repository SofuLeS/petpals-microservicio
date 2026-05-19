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

    private final RepositoryUbicacion repositoryUbicacion;

    @Override
    public void run(String... args) throws Exception {

        // Si ya existen datos, no volvemos a insertar para no duplicar
        if (repositoryUbicacion.count() > 0) {
            log.info("La base de datos ya tiene ubicaciones registradas.");
            return;
        }

        // Estructura
        repositoryUbicacion.save(new ModelUbicacion(null, 1L, "Santiago", "Puente Alto", "Metropolitana"));
        repositoryUbicacion.save(new ModelUbicacion(null, 1L, "Valparaíso", "Valparaíso", "Valparaíso"));
        repositoryUbicacion.save(new ModelUbicacion(null, 1L, "Valparaíso", "Viña del Mar", "Valparaíso"));
        repositoryUbicacion.save(new ModelUbicacion(null, 1L, "Santiago", "Maipú", "Metropolitana"));

        log.info("¡Ubicaciones de prueba inicializadas con éxito en la base de datos!");
    }
}