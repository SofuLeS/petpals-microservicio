package cl.petpals.ms_reservas.service;

import cl.petpals.ms_reservas.dto.HistorialDTO;
import cl.petpals.ms_reservas.model.Reserva;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HistorialAsyncService {

    @Value("${ms.historial.url}")
    private String historialUrl;

    private final RestTemplate restTemplate;
    public HistorialAsyncService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    public void enviarHistorial(Reserva reserva){
        try {
            HistorialDTO dto = new HistorialDTO();
            dto.setIdReservas(reserva.getId());
            dto.setIdDueño(reserva.getIdDueno());
            dto.setIdCuidador(reserva.getIdCuidador());
            dto.setIdMascota(reserva.getIdMascota());
            dto.setIdServicio(reserva.getIdServicio());
            dto.setFechaReserva(reserva.getFechaReserva());
            dto.setFechaFinReserva(reserva.getFechaFinReserva());
            restTemplate.postForObject(
                    historialUrl + "/api/historial",
                    dto,
                    Void.class);
        } catch (Exception e) {
            System.out.println("Error enviando historial: " + e.getMessage());
        }
    }



}
