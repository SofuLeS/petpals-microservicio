package com.historial_petpals.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistorialRequest {

    @NotNull(message = "El id de reserva es obligatorio")
    private Long idReservas;

    @NotNull(message = "El ID dueño es obligatorio")
    private Integer idDueno;

    @NotNull(message = "EL id de cuidador es obligatorio")
    private Long idCuidador;

    @NotNull(message = "El id de mascota es obligatorio")
    private Long idMascota;

    @NotNull(message = "El id de servicio es obligatorio")
    private Long idServicio;

}
