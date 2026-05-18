package com.historial_petpals.service;

import com.historial_petpals.dto.*;
import com.historial_petpals.model.Historial;
import com.historial_petpals.repository.HistorialRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class HistorialService {

    private final HistorialRepo historialRepo;
    private final WebClient.Builder webClientBuilder;


    @Value("${ms.cuidadores.url}")
    private String cuidadoresUrl;


    private CuidadorResponseDto obtenerCuidador(Long idCuidador){
        return webClientBuilder
                .baseUrl(cuidadoresUrl)
                .build().get()
                .uri("/api/cuidadores/" + idCuidador)
                .retrieve()
                .bodyToMono(CuidadorResponseDto.class)
                .block();
    }

    @Value("${ms.mascotas.url}")
    private String mascotasUrl;

    private MascotaResponseDTO obtenerMascota(Long idMascota){
        return webClientBuilder.baseUrl(mascotasUrl).build()
                .get().uri("/api/mascotas/" + idMascota)
                .retrieve()
                .bodyToMono(MascotaResponseDTO.class)
                .block();
    }
    @Value("${ms.dueno.url}")
    private String duenoUrl;
    private DuenoResponse obtenerDueno(Long idDueno){
        return webClientBuilder.baseUrl(duenoUrl).build()
                .get().uri("/api/dueno" + idDueno).retrieve()
                .bodyToMono(DuenoResponse.class).block();
    }

    @Value("${ms.reserva.url}")
    private String reservaUrl;
    private ReservaResponse obtenerReserva(Long idReserva){
        return webClientBuilder.baseUrl(reservaUrl).build()
                .get().uri("/api/reserva" + idReserva).retrieve()
                .bodyToMono(ReservaResponse.class).block();
    }

    @Value("${ms.servicio.url}")
    private String servicioUrl;
    private ServicioResponse obtenerServicio(Long idServicio){
        return webClientBuilder.baseUrl(servicioUrl).build()
                .get().uri("/api/servicio" + idServicio).retrieve()
                .bodyToMono(ServicioResponse.class).block();
    }


    public HistorialResponse mapToDTO(Historial historial){
        return new HistorialResponse(
                historial.getId(),
                historial.getIdReservas(),
                historial.getIdDueno(),
                historial.getIdCuidador(),
                historial.getIdMascota(),
                historial.getIdServicio()
        );

    }

    public HistorialResponse guardar(HistorialRequest dto){
        Historial historial = new Historial(
                null,
                dto.getIdReservas(),
                dto.getIdDueno(),
                dto.getIdCuidador(),
                dto.getIdMascota(),
                dto.getIdServicio()
        );
        return mapToDTO(historialRepo.save(historial));
    }
}
