package com.ResenasM.MicroServicioResenas.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ResenaResponseDTO {
    private Long idResena;
    private Integer estrellas;
    private String comentarios;
    private Long idReserva;
    private Long idDueno;
    private Long idCuidador;
    private LocalDate fechaResena;
    //año,mes,dia
}