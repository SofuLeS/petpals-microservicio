package com.historial_petpals.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaResponseDTO {
    private Long id_mascota;
    private String nombre;
    private String raza;
    private Integer edad;
    private String alergias;
    private String TipoMascota;

    private Long idDueno;


    private String mensaje;
}
