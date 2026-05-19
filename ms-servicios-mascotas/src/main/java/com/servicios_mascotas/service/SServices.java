package com.servicios_mascotas.service;

import com.servicios_mascotas.dto.SMRequest;
import com.servicios_mascotas.dto.SMResponse;
import com.servicios_mascotas.model.ServicioMascotas;
import com.servicios_mascotas.repository.SRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SServices {

    @Autowired
    private SRespository sRespository;

    private SMResponse mapToDTO(ServicioMascotas servicio){
        return new SMResponse( // aqui estamos retornando el SMResponse
                servicio.getId(),
                servicio.getNombreServicio(),
                servicio.getDescripcion(),
                servicio.getPrecio()
        );
    }

    public SMResponse guardar(SMRequest dto){
        ServicioMascotas newservicio = new ServicioMascotas(
                null,
                dto.getNombreServicio(),
                dto.getDescripcion(),
                dto.getPrecio()
        );
        return mapToDTO(sRespository.save(newservicio));
    }

    public List<SMResponse> listar(){
        return sRespository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<SMResponse> obtenerXId(Long id){
        return sRespository.findById(id).map(this::mapToDTO);
    }
    public void eliminar(Long id){
        sRespository.deleteById(id);
    }

    public Optional<SMResponse> actualizar(Long id, SMRequest dto){
        return  sRespository.findById(id).map( existente ->
        {
            existente.setNombreServicio(dto.getNombreServicio());
            existente.setDescripcion(dto.getDescripcion());
            existente.setPrecio(dto.getPrecio());
            return mapToDTO(sRespository.save(existente));
        });
    }

    public Optional<SMResponse> actCampo(Long id, SMRequest dto){
        return sRespository.findById(id).map( existente ->
        {
            if (dto.getNombreServicio() != null){
                existente.setNombreServicio(dto.getNombreServicio());
            }
            if(dto.getDescripcion() != null){
                existente.setDescripcion(dto.getDescripcion());
            }
            if (dto.getPrecio() != 0){
                existente.setPrecio(dto.getPrecio());
            }
            return mapToDTO(sRespository.save(existente));
        });
    }



















}
