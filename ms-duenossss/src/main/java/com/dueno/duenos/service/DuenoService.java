package com.dueno.duenos.service;

import com.dueno.duenos.dto.DuenoRequest;
import com.dueno.duenos.dto.DuenoResponse;
import com.dueno.duenos.model.Dueno;
import com.dueno.duenos.repository.DuenoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DuenoService {
    @Autowired
    private final DuenoRepository duenoRepository;

    //Creamos un metodo para transformar el objeto Dueno a DuenoResponse
    private DuenoResponse mapToDTO(Dueno dueno){
        return new DuenoResponse( // aqui estamos retornando el DuenoResponse
                dueno.getId(),
                dueno.getRut(),
                dueno.getNombres(),
                dueno.getApellidos(),
                dueno.getTelefono(),
                dueno.getCorreo(),
                dueno.getDireccion()
        );
    }

    //Metodo de tipo DuenoResponse que debe retornar el mismo tipo DuenoResponse
    //Sin embargo, el metodo en el Repository recibe por parametro objeto tipo Dueno
    public DuenoResponse guardar(DuenoRequest dto){

       //Aqui preparamos el objeto tipo Dueno para mandarselo al repository
        Dueno dueno = new Dueno( //Nuevo Objeto creado
                null,
                dto.getRut(),
                dto.getNombres(),
                dto.getApellidos(),
                dto.getTelefono(),
                dto.getCorreo(),
                dto.getDireccion()
        );
        //Fin preparacion

        //Como el return debe ser de tipo DuenoResponse, utilizamos el metodo mapToDTO para
        //Transformar el nuevo objeto creado de tipo Dueno a DuenoResponse para que el return
        //no arroje error "Requiere DuenoResponse"
        return  mapToDTO(duenoRepository.save(dueno));//Repository recibe tipo Dueno
    }

    public List<DuenoResponse> mostrarTodos(){
        return duenoRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<DuenoResponse> obtenerXId(Long id){
        return duenoRepository.findById(id).map(this::mapToDTO);
    }

    public void eliminar(Long id){
        duenoRepository .deleteById(id);
    }


    public Optional<DuenoResponse> actualizar(Long id, DuenoRequest dto){
        return  duenoRepository.findById(id).map( existente ->
        {
            existente.setRut(dto.getRut());
            existente.setNombres(dto.getNombres());
            existente.setApellidos(dto.getApellidos());
            existente.setTelefono(dto.getTelefono());
            existente.setCorreo(dto.getCorreo());
            existente.setDireccion(dto.getDireccion());
            return mapToDTO(duenoRepository.save(existente));
        });
    }


    public Optional<DuenoResponse> actualizarCampo(Long id, DuenoRequest dto){
            return duenoRepository.findById(id).map( existente ->
             {
                 if (dto.getRut() != null){
                     existente.setRut(dto.getRut());
                 }
                 if (dto.getNombres() != null){
                     existente.setNombres(dto.getNombres());
                 }
                 if (dto.getApellidos() != null){
                     existente.setApellidos(dto.getApellidos());
                 }
                 if (dto.getTelefono() != null){
                     existente.setTelefono(dto.getTelefono());
                 }
                 if (dto.getCorreo() != null){
                     existente.setCorreo(dto.getCorreo());
                 }
                 if (dto.getDireccion() != null){
                     existente.setDireccion(dto.getDireccion());
                 }
                 return mapToDTO(duenoRepository.save(existente));

                    });
    }

}
