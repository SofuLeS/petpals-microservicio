package com.MUbicacion.MicroServicioUbicacion.dto;

//Aqui van a estar todas las respuestas del sistema (salidas) y aqui si incluimos el id porque lo muestra en la salida.
import lombok.Data;
@Data
public class ResponseDTO {
    private Long id;
    private Long idCuidador;
    private String comuna;
    private String ciudad;
    private String region;


}
