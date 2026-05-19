package c.ResenasM.MicroServicioResenas.service;


import c.ResenasM.MicroServicioResenas.dto.ResenaRequestDTO;
import c.ResenasM.MicroServicioResenas.dto.ResenaResponseDTO;

import java.util.List;

public interface IResenaService {

    ResenaResponseDTO guardar(ResenaRequestDTO dto);

    List<ResenaResponseDTO> listarPorCuidador(Long idCuidador);
}