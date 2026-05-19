package com.servicios_mascotas.config;

import com.servicios_mascotas.model.ServicioMascotas;
import com.servicios_mascotas.repository.SRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final SRespository sRespository;

    @Override
    public void run(String... args){
        if (sRespository.count() > 0){
            log.info("PetPals: La base de datos ya tiene datos.");
            return;
        }
        log.info("PetPals: Insertando datos de prueba iniciales...");

       sRespository.save(new  ServicioMascotas(null,"Paseo", "Paseo de una hora", 1000.0));
       sRespository.save(new  ServicioMascotas(null,"Guarderia", "se cuida a su mascota por 5 horas de noche o dia", 1500.0));
       sRespository.save(new  ServicioMascotas(null,"Ducha", "Ducha express a mascota", 1000.0));

        log.info(">>> DataInitializer: Servicios insertados correctamente.",
              sRespository.count());







    }
}
