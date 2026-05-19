package com.MUbicacion.MicroServicioUbicacion.service;

import com.MUbicacion.MicroServicioUbicacion.dto.RequestDTO;
import com.MUbicacion.MicroServicioUbicacion.dto.ResponseDTO;
import com.MUbicacion.MicroServicioUbicacion.model.ModelUbicacion;
import com.MUbicacion.MicroServicioUbicacion.repository.RepositoryUbicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ServiceUbicacion {
    @Autowired
    private RepositoryUbicacion repositoryUbicacion;

    @Autowired
    private WebClient.Builder webClient;

    //Metodo guardar
    public ResponseDTO crearUbicacion(RequestDTO request) {
        Boolean existeCuidador = webClient.build().get()
                .uri("http://localhost:8085/api/cuidadores/" + request.getIdCuidador())
                .retrieve().bodyToMono(Boolean.class)// esperamos un true o false (si existe o no el cuidador)
                .block(); // esperamos la respuesta ... login
        if (existeCuidador == null || !existeCuidador) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuidador no encontrado");
        }
        // una salida clara 404, que indica en que fallo
        // si existia y paso creamos los gets
        // tranformarmos el modelo
        ModelUbicacion modelo = new ModelUbicacion();
        modelo.setIdCuidador(request.getIdCuidador());
        modelo.setComuna(request.getComuna());
        modelo.setCiudad(request.getCiudad());
        modelo.setRegion(request.getRegion());

        ModelUbicacion guardado = repositoryUbicacion.save(modelo);

        // una vez guardado todo debemos devolver la respuesta al usuario
        // esto se proyecta en el postman

        ResponseDTO response = new ResponseDTO();
        response.setId(guardado.getId());
        response.setIdCuidador(guardado.getIdCuidador());
        response.setComuna(guardado.getComuna());
        response.setCiudad(guardado.getCiudad());
        response.setRegion(guardado.getRegion());
        // retornamos la respuesta
        return response;
    }

    // haremos una consulta con filtro (buscar comuna) donde buscaremos un cuidador cercano.
    public List<ResponseDTO> buscarPorComuna(String comuna) {
        return repositoryUbicacion.findByComunaIgnoreCase(comuna).stream() //procesando los datos.
                .map(ubicacion -> {
                    ResponseDTO dto = new ResponseDTO();
                    dto.setId(ubicacion.getId());
                    dto.setIdCuidador(ubicacion.getIdCuidador());
                    dto.setComuna(ubicacion.getComuna());
                    dto.setCiudad(ubicacion.getCiudad());
                    dto.setRegion(ubicacion.getRegion());
                    return dto;
                }).toList();


    }
}