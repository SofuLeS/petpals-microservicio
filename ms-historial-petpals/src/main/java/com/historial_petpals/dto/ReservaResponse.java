package com.historial_petpals.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaResponse {

    private Long id;
    private Long idDueno;
    private Long idMascota;
    private Long idCuidador;
    private Long idServicio;
    private LocalDateTime fechaReserva;
    private LocalDateTime fechaFinReserva;
    private String estadoReserva;
}
