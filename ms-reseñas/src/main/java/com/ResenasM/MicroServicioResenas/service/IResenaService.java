package com.ResenasM.MicroServicioResenas.service;

import com.ResenasM.MicroServicioResenas.dto.ResenaResponseDTO;
import com.ResenasM.MicroServicioResenas.dto.ResenasRequestDTO;
import java.util.List;

public interface IResenaService {
    ResenaResponseDTO guardar(ResenasRequestDTO dto);

    List<ResenaResponseDTO> listarPorCuidador(Long idCuidador);
}