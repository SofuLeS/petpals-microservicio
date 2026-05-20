package com.MUbicacion.MicroServicioUbicacion.service;

import com.MUbicacion.MicroServicioUbicacion.dto.RequestDTO;
import com.MUbicacion.MicroServicioUbicacion.dto.ResponseDTO;
import com.MUbicacion.MicroServicioUbicacion.model.ModelUbicacion;
import com.MUbicacion.MicroServicioUbicacion.repository.RepositoryUbicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceUbicacion {

    @Autowired
    private RepositoryUbicacion repositoryUbicacion;

    @Autowired
    private WebClient.Builder webClient;

    //Métodode conversión centralizado (DTO Map)
    private ResponseDTO MapDTO(ModelUbicacion modelo) {
        ResponseDTO response = new ResponseDTO();
        response.setId(modelo.getId());
        response.setIdCuidador(modelo.getIdCuidador());
        response.setComuna(modelo.getComuna());
        response.setCiudad(modelo.getCiudad());
        response.setRegion(modelo.getRegion());
        return response;
    }

    //Método guardar
    public ResponseDTO crearUbicacion(RequestDTO request) {
        try {
            webClient.build().get()
                    .uri("http://localhost:8085/api/cuidadores/" + request.getIdCuidador())
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuidador no encontrado");
        }

        //si existia y paso creamos la entidad
        ModelUbicacion modelo = new ModelUbicacion();
        modelo.setIdCuidador(request.getIdCuidador());
        modelo.setComuna(request.getComuna());
        modelo.setCiudad(request.getCiudad());
        modelo.setRegion(request.getRegion());

        ModelUbicacion guardado = repositoryUbicacion.save(modelo);

        //retornamos la respuesta usando el mapeador automático
        return MapDTO(guardado);
    }

    //Método obtener todas las ubicaciones
    public List<ResponseDTO> obtenerTodas() {
        return repositoryUbicacion.findAll().stream()
                .map(this::MapDTO) // Reutiliza el método MapDTO automáticamente
                .collect(Collectors.toList());
    }

    //Consulta con filtro (buscar comuna) donde buscaremos un cuidador cercano.
    public List<ResponseDTO> buscarPorComuna(String comuna) {
        return repositoryUbicacion.findByComunaIgnoreCase(comuna).stream() // procesando los datos.
                .map(this::MapDTO)
                .toList();
    }
}