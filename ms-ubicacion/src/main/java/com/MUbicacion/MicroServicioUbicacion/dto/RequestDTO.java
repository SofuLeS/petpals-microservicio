package com.MUbicacion.MicroServicioUbicacion.dto;

import lombok.Data;
//Aqui seran todos los datos que se veran en el post , el usuario no pone un id porque se genera de manera auto en la base de datos.
@Data
public class RequestDTO {
    private Long idCuidador;
    private String comuna;
    private String ciudad;
    private String region;
}

