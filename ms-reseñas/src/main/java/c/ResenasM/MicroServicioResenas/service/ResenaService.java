package com.ResenasM.MicroServicioResenas.service;

import com.ResenasM.MicroServicioResenas.config.Data;
import com.ResenasM.MicroServicioResenas.exception.ResourceNotFoundException;
import com.ResenasM.MicroServicioResenas.model.ResenasModel;
import com.ResenasM.MicroServicioResenas.repository.ResenasRepository;
import com.ResenasM.MicroServicioResenas.dto.ResenaResponseDTO;
import com.ResenasM.MicroServicioResenas.dto.ResenasRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResenaService implements IResenaService {

    @Autowired
    private ResenasRepository resenasRepository;

    @Autowired
    private Data webClientData;


    @Override
    public ResenaResponseDTO guardar(ResenasRequestDTO dto) {

        // Esto va a validar que antes de guardar un dato vaya y le pregunte al
        // otro microservicio si existe o valida id.
        try {
            webClientData.obtenerDuenoId(dto.getIdDueno());
        } catch (Exception e) {
            throw new ResourceNotFoundException("No se puede crear la reseña: El dueño con ID " + dto.getIdDueno() + " no existe.");
        }
        // 2. Validamos que el Cuidador exista en el otro microservicio
        try {
            webClientData.obtenerCuidadorId(dto.getIdCuidador());
        } catch (Exception e) {
            throw new ResourceNotFoundException("No se puede crear la reseña: El cuidador con ID " + dto.getIdCuidador() + " no existe.");
        }
        //
        //Limpiamos
        // Evita q se guarden mensajes raros
        String comentarioLimpio = dto.getComentarios().trim();

        // Creamos la entidad y pasamos los datos del DTO
        ResenasModel nuevaResena = new ResenasModel();
        nuevaResena.setEstrellas(dto.getEstrellas());
        nuevaResena.setComentarios(comentarioLimpio);

        // Pasamos losid
        nuevaResena.setIdReserva(dto.getIdReserva());
        nuevaResena.setIdDueno(dto.getIdDueno());
        nuevaResena.setIdCuidador(dto.getIdCuidador());

        //guardamos en la base de datos { fecha.}
        ResenasModel resenaGuardada = resenasRepository.save(nuevaResena);

        // resultado a DTO para responder a Postman
        return MapDTO(resenaGuardada);
    }

    @Override
    public List<ResenaResponseDTO> listarPorCuidador(Long idCuidador) {
        // Buscamos todas las reseñas que pertenecen a ese cuidador
        return resenasRepository.findByIdCuidador(idCuidador)
                .stream()
                .map(this::MapDTO)
                .collect(Collectors.toList());
    }

    //Toma el valor que tiene el modelo BD y lo copia dentro del DTO.
    private ResenaResponseDTO MapDTO(ResenasModel modelo) {
        ResenaResponseDTO response = new ResenaResponseDTO();
        response.setIdResena(modelo.getIdResena());
        response.setEstrellas(modelo.getEstrellas());
        response.setComentarios(modelo.getComentarios());
        response.setIdReserva(modelo.getIdReserva());
        response.setIdDueno(modelo.getIdDueno());
        response.setIdCuidador(modelo.getIdCuidador());
        response.setFechaResena(modelo.getFechaResena());
        return response;
    }
}