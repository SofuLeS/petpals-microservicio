package com.Calendario.MicroservicioCalendario.service;


import com.Calendario.MicroservicioCalendario.dtos.ResponseCalendarioDTO;
import com.Calendario.MicroservicioCalendario.model.ModelCalendario;
import com.Calendario.MicroservicioCalendario.repository.RepositoryCalendario;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ServiceCalendario {

    private final RepositoryCalendario repositoryCalendario;
    private final WebClient.Builder webClientBuilder;

    //
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

    private ResponseCalendarioDTO convertirADtoConCuidador(ModelCalendario modelo) {
        ResponseCalendarioDTO dto = new ResponseCalendarioDTO();
        dto.setIdCalendario(modelo.getId());
        dto.setIdCuidador(modelo.getIdCuidador());
        dto.setFecha(modelo.getFecha());
        dto.setHoraInicio(modelo.getHoraInicio());
        dto.setHoraFin(modelo.getHoraFin());

        try {
            // Llamamos al endpoint GET por ID que sí sabemos que funciona en el 8085
            Map<?, ?> cuidadorJson = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8085/api/cuidadores/" + modelo.getIdCuidador())
                    .retrieve()
                    .bodyToMono(Map.class) // Lo leemos como un mapa clave,valor
                    .block();

            if (cuidadorJson != null) {
                // Extraemos los campos de cuidadores
                String nombre = (String) cuidadorJson.get("nombre");
                String apellidos = (String) cuidadorJson.get("apellidos");
                Object telefono = cuidadorJson.get("telefono");

                dto.setNombreCuidador(nombre + " " + apellidos);
                dto.setTelefonoCuidador(telefono != null ? telefono.toString() : "Sin teléfono");
            }
        } catch (Exception e) {
            // Si el micro de Cuidadores está apagado o no encuentra el ID, entra aquí
            dto.setNombreCuidador("Cuidador No Disponible (ID " + modelo.getIdCuidador() + ")");
            dto.setTelefonoCuidador("N/A");
        }

        return dto;
    }
    // Método para mostrar todos los calendarios
    public List<ResponseCalendarioDTO> obtenerTodos() {
        return repositoryCalendario.findAll().stream()
                .map(this::convertirADtoConCuidador)
                .collect(Collectors.toList());
    }
}