package com.historial_petpals.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialResponse {

    private Long id;
    private Long idReservas;
    private Integer idDueno;
    private Long idCuidador;
    private Long idMascota;
    private Long idServicio;



}
