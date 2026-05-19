package com.Calendario.MicroservicioCalendario.config;


import com.Calendario.MicroservicioCalendario.model.ModelCalendario;
import com.Calendario.MicroservicioCalendario.repository.RepositoryCalendario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.LocalDate;
import java.time.LocalTime;

@Configuration
public class WebClientConfig {

    //Configuracion para el telefono
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    // 2. Inicializador de Datos
    @Bean
    CommandLineRunner initDatabase(RepositoryCalendario repositoryCalendario) {
        return args -> {
            if (repositoryCalendario.count() > 0) {
                System.out.println(" Ya tenemos datos en Calendario ");
                return;
            }

            // Insertamos datos de prueba con formato: (id, idCuidador, fecha, horaInicio, horaFin)
            // Usamos null en el ID para que MySQL use el Auto_Increment sin reclamar
            repositoryCalendario.save(new ModelCalendario(null, 101L, LocalDate.of(2026, 11, 3), LocalTime.of(14, 0), LocalTime.of(13, 46)));
            repositoryCalendario.save(new ModelCalendario(null, 101L, LocalDate.of(2026, 11, 4), LocalTime.of(13, 0), LocalTime.of(12, 23)));
            repositoryCalendario.save(new ModelCalendario(null, 102L, LocalDate.of(2026, 11, 3), LocalTime.of(15, 0), LocalTime.of(14, 50)));
            repositoryCalendario.save(new ModelCalendario(null, 103L, LocalDate.of(2026, 11, 5), LocalTime.of(11, 0), LocalTime.of(10, 23)));

            System.out.println("Datos encontrados exitosamente");
        };
    }
}