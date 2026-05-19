package com.historial_petpals.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioResponse {
    private Integer id;
    private String nombreServicio;
    private String descripcion;
    private double precio;
}
