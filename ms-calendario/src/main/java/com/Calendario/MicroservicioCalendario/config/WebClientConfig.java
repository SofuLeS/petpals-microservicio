package com.Calendario.MicroservicioCalendario.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class WebClientConfig {

    //Configuracion para el telefono
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}