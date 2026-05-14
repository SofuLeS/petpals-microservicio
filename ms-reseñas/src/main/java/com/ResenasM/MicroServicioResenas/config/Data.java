package com.ResenasM.MicroServicioResenas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Component
public class Data {

    private final WebClient webClientDueno;
    private final WebClient webClientCuidador;

    // SE va a configurar ambas url tanto como las del dueño y cuidadores
    public Data(
            @Value("${dueno-service.url}") String duenoServidor,
            @Value("${cuidador-service.url}") String cuidadorServidor) {
        this.webClientDueno = WebClient.builder().baseUrl(duenoServidor).build();
        this.webClientCuidador = WebClient.builder().baseUrl(cuidadorServidor).build();
    }

    // Método para verificar dueno
    public Map<String, Object> obtenerDuenoId(Long id){
        return this.webClientDueno.get()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Dueño no encontrado")))
                .bodyToMono(Map.class)
                .block();
    }

    // NUEVO: Método para verificar al Cuidador
    public Map<String, Object> obtenerCuidadorId(Long id){
        return this.webClientCuidador.get()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Cuidador no encontrado")))
                .bodyToMono(Map.class)
                .block();
    }
}