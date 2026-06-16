package com.dueno.duenos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Informacion del dueño ")
public class DuenoRequest {

    //El id no va ya que MySql
    // lo genera de forma que incrementa


    @NotBlank(message = "El rut es obligatorio")
    @Schema(description = "RUT del dueño",
    example = "25751348-K")
    private String rut;

    @NotBlank(message = "los nombres no pueden estar vacio")
    @Schema(description = "Nombres del dueño",
    example = "Ana María")
    private String nombres;

    @NotBlank(message = "Los apellidos no pueden estar vacio")
    @Schema(description = "Apellidos del dueño",
    example = "García Soto")
    private String apellidos;


    @NotNull(message = "El numero de telefono es obligacion")
    @Schema(description = "Telefono del dueño",
    example = "56853297514")
    private Integer telefono;


    @NotBlank(message = "El correo no puede estar vacio")
    @Schema(description = "Correo electronico del dueño",
    example = "ana@gmail.com")
    private String correo;


    @NotBlank(message = "La direccion es obligatoria")
    @Schema(description = "Direccion del dueño",
    example = "Valparaiso")
    private String direccion;








}
