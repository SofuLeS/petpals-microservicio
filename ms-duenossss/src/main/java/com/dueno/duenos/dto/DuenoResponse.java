package com.dueno.duenos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;

@Schema(description = "Datos de un Dueño")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DuenoResponse {

    @Schema(description = "ID unico del Dueño",
            example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "RUT chileno del Dueño",
            example = "25751348-k" , requiredMode = Schema.RequiredMode.REQUIRED)
    private String rut;

    @Schema(description = "Nombres del Dueño",
            example = "Ana Antonella", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombres;

    @Schema(description = "Apellidos del Dueño",
            example = "Merino Zuñiga", requiredMode = Schema.RequiredMode.REQUIRED)
    private String apellidos;

    @Schema(description = "Telefono del Dueño",
            example = "569486274", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer telefono;

    @Schema(description = "Correo electronico Dueño",
            example = "ana@email.com" ,requiredMode = Schema.RequiredMode.REQUIRED)
    private String correo;

    @Schema(description = "Direccion del Dueño",
            example = "Valparaiso , calle prat", requiredMode = Schema.RequiredMode.REQUIRED)
    private String direccion;
}
