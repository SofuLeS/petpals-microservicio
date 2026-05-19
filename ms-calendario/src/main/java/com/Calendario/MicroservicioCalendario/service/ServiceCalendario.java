package com.Calendario.MicroservicioCalendario.service;


import com.Calendario.MicroservicioCalendario.dtos.ResponseCalendarioDTO;
import com.Calendario.MicroservicioCalendario.model.ModelCalendario;
import com.Calendario.MicroservicioCalendario.repository.RepositoryCalendario;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceCalendario {

    private final RepositoryCalendario repositoryCalendario;
    private final WebClient.Builder webClientBuilder;

    // Constructor manual para evitar problemas de Lombok
    public ServiceCalendario(RepositoryCalendario repositoryCalendario, WebClient.Builder webClientBuilder) {
        this.repositoryCalendario = repositoryCalendario;
        this.webClientBuilder = webClientBuilder;
    }

    // busqueda por fecha y conexion con otros micro servicios .
    public List<ResponseCalendarioDTO> buscarPorFecha(LocalDate fecha) {
        List<ModelCalendario> agendas = repositoryCalendario.findByFecha(fecha);

        return agendas.stream()
                .map(this::convertirADtoConCuidador)
                .collect(Collectors.toList());
    }

    // cosulta de fechas aleatorias
    public List<ResponseCalendarioDTO> obtenerAleatorios() {
        List<ModelCalendario> agendasAleatorias = repositoryCalendario.findCuidadoresAleatorios();

        return agendasAleatorias.stream()
                .map(this::convertirADtoConCuidador)
                .collect(Collectors.toList());
    }

    // transformaos a dto los datos
    private ResponseCalendarioDTO convertirADtoConCuidador(ModelCalendario modelo) {
        ResponseCalendarioDTO dto = new ResponseCalendarioDTO();
        dto.setIdCalendario(modelo.getId());
        dto.setIdCuidador(modelo.getIdCuidador());
        dto.setFecha(modelo.getFecha());
        dto.setHoraInicio(modelo.getHoraInicio());
        dto.setHoraFin(modelo.getHoraFin());

        try {
            //llamamos a su endpoint de cuidador.
            String nombreDelCuidador = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8085/api/cuidadores/" + modelo.getIdCuidador() + "/Nombre")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); //

            dto.setNombreCuidador(nombreDelCuidador);
            dto.setTelefonoCuidador("+56912345678"); // Dato simulado
        } catch (Exception e) {
            // Si el micro de ella está apagado, le ponemos un nombre por defecto para que no se caiga tu app
            dto.setNombreCuidador("Cuidador No Disponible MicroService off");
        }

        return dto;
    }
}