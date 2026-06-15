package com.servicios_mascotas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SMRequest {


    @NotBlank(message = "Debe indicar el nombre del servicio")
    @Schema(description = "Nombre del servicio", example = "Paseo")
    private String nombreServicio;

    @NotBlank(message = "La descripcion es obligatoria")
    @Schema(description = "descripcion breve del servicio", example = "Paseo de una hora")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @Schema(description = "Costo del servicio", example = "1000.0")
    private double precio;

}
