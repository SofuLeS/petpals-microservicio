package com.MUbicacion.MicroServicioUbicacion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo de datos requerido para registrar una localización")
public class RequestDTO {

    @Schema(description = "ID único del cuidador a asociar", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idCuidador;

    @Schema(description = "Nombre de la comuna", example = "Viña del Mar", requiredMode = Schema.RequiredMode.REQUIRED)
    private String comuna;

    @Schema(description = "Nombre de la ciudad", example = "Valparaíso")
    private String ciudad;

    @Schema(description = "Región del país", example = "Región de Valparaíso")
    private String region;
}