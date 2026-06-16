package com.dueno.duenos.config;

import com.dueno.duenos.model.Dueno;
import com.dueno.duenos.repository.DuenoRepository;
import com.dueno.duenos.service.DuenoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final DuenoRepository duenoRepository;

    @Override
    public void run(String... args){
        // Esto evita duplicar datos en cada reinicio del servidor
        if (duenoRepository.count() > 0){
            log.info("DataInitializer:  La base de datos ya contiene datos");
            return;
        }
        log.info("DataInitializer: BD vacía detectada, insertando datos de prueba... ");

        duenoRepository.save(new Dueno(null,"11111111-1","Catalina Graciela", "Reyes Zambrano",56948627,"Cat@gmail.com","Valparaiso , joaquin lepeley" ));
        duenoRepository.save(new Dueno(null,"22222222-2","Carolina Andrea", "Soto Espinoza",56948627,"Carol@gmail.com","Valparaiso , playa ancha" ));
        duenoRepository.save(new Dueno(null,"33333333-3","Sebastian Andres ", "Solis Olivares",56948627,"sebas@gmail.com","Valparaiso , calle melipilla" ));
        duenoRepository.save(new Dueno(null,"44444444-4","Antonela Paz", "Merino Zuñiga",56948627,"Antonella@gmail.com","Valparaiso , calle prat" ));
        log.info("DataInitializer: Dueños insertados correctamente" , duenoRepository.count());
















    }















}
