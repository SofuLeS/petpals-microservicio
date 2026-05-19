package com.Calendario.MicroservicioCalendario.dtos;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ResponseCalendarioDTO {
    private Long idCalendario;
    private Long idCuidador;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    // datos del cuidador
    private String nombreCuidador;
    private String telefonoCuidador;
}
