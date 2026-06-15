package com.servicios_mascotas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos de los servicios")
public class SMResponse {

    @Schema(description = "ID unico del servicio",
            example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Nombre del servicio",
            example = "Paseo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombreServicio;

    @Schema(description = "descripcion breve del servicio",
            example = "Paseo de una hora", requiredMode = Schema.RequiredMode.REQUIRED)
    private String descripcion;

    @Schema(description = "Costo del servicio",
            example = "1000.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private double precio;
}
