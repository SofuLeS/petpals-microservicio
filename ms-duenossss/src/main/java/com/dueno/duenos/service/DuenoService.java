package com.dueno.duenos.service;

import com.dueno.duenos.dto.DuenoRequest;
import com.dueno.duenos.dto.DuenoResponse;
import com.dueno.duenos.model.Dueno;
import com.dueno.duenos.repository.DuenoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DuenoService {
    private final DuenoRepository duenoRepository;


    private DuenoResponse mapToDTO(Dueno dueno){
        return new DuenoResponse(
                dueno.getId(),
                dueno.getRut(),
                dueno.getNombres(),
                dueno.getApellidos(),
                dueno.getTelefono(),
                dueno.getCorreo(),
                dueno.getDireccion()
        );
    }

    public DuenoResponse guardar(DuenoRequest dto){

        Dueno dueno = new Dueno(
                null,
                dto.getRut(),
                dto.getNombres(),
                dto.getApellidos(),
                dto.getTelefono(),
                dto.getCorreo(),
                dto.getDireccion()
        );
        return  mapToDTO(duenoRepository.save(dueno));
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



}
