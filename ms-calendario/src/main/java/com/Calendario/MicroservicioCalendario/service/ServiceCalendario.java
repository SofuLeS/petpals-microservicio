package com.Calendario.MicroservicioCalendario.service;

import com.Calendario.MicroservicioCalendario.dtos.RequestCalendarioDTO;
import com.Calendario.MicroservicioCalendario.dtos.ResponseCalendarioDTO;
import com.Calendario.MicroservicioCalendario.model.ModelCalendario;
import com.Calendario.MicroservicioCalendario.repository.RepositoryCalendario;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ServiceCalendario {

    private final RepositoryCalendario repositoryCalendario;
    private final WebClient.Builder webClientBuilder;

    public ServiceCalendario(RepositoryCalendario repositoryCalendario, WebClient.Builder webClientBuilder) {
        this.repositoryCalendario = repositoryCalendario;
        this.webClientBuilder = webClientBuilder;
    }

    // Ahora acepta el RequestCalendarioDTO que viene desde el controlador
    public ResponseCalendarioDTO guardarCalendario(RequestCalendarioDTO dto) {
        ModelCalendario nuevoCalendario = new ModelCalendario();
        nuevoCalendario.setIdCuidador(dto.getIdCuidador());
        nuevoCalendario.setFecha(dto.getFecha());
        nuevoCalendario.setHoraInicio(dto.getHoraInicio());
        nuevoCalendario.setHoraFin(dto.getHoraFin());

        ModelCalendario guardado = repositoryCalendario.save(nuevoCalendario);
        return convertirADtoConCuidador(guardado);
    }

    public List<ResponseCalendarioDTO> buscarPorFecha(LocalDate fecha) {
        List<ModelCalendario> agendas = repositoryCalendario.findByFecha(fecha);

        return agendas.stream()
                .map(this::convertirADtoConCuidador)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<ResponseCalendarioDTO> obtenerAleatorios() {
        List<ModelCalendario> agendasAleatorias = repositoryCalendario.findCuidadoresAleatorios();

        return agendasAleatorias.stream()
                .map(this::convertirADtoConCuidador)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<ResponseCalendarioDTO> obtenerTodos() {
        return repositoryCalendario.findAll().stream()
                .map(this::convertirADtoConCuidador)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public ResponseCalendarioDTO obtenerPorId(ModelCalendario modelo) {
        return convertirADtoConCuidador(modelo);
    }

    private ResponseCalendarioDTO convertirADtoConCuidador(ModelCalendario modelo) {
        try {
            Map<?, ?> cuidadorJson = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8085/api/cuidadores/" + modelo.getIdCuidador())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (cuidadorJson != null) {
                ResponseCalendarioDTO dto = new ResponseCalendarioDTO();
                dto.setIdCalendario(modelo.getId());
                dto.setIdCuidador(modelo.getIdCuidador());
                dto.setFecha(modelo.getFecha());
                dto.setHoraInicio(modelo.getHoraInicio());
                dto.setHoraFin(modelo.getHoraFin());

                String nombre = cuidadorJson.get("nombre") != null ? cuidadorJson.get("nombre").toString() : "";
                Object apellidoObj = cuidadorJson.get("apellidos") != null ? cuidadorJson.get("apellidos") : cuidadorJson.get("apellido");
                String apellido = apellidoObj != null ? apellidoObj.toString() : "";
                Object telefono = cuidadorJson.get("telefono");

                dto.setNombreCuidador((nombre + " " + apellido).trim());
                dto.setTelefonoCuidador(telefono != null ? telefono.toString() : "Telefono no disponible");

                return dto;
            }
        } catch (Exception e) {
            System.out.println("El cuidador no esta registrado ID: " + modelo.getIdCuidador());
        }
        return null;
    }
}