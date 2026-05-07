package com.dueno.duenos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DuenoRequest {

    //El id no va ya que MySql
    // lo genera de forma que incrementa

    @NotBlank(message = "El rut es obligatorio")
    private String rut;

    @NotBlank(message = "los nombres no pueden estar vacio")
    private String nombres;

    @NotBlank(message = "Los apellidos no pueden estar vacio")
    private String apellidos;

    @NotNull(message = "El numero de telefono es obligacion")
    private Integer telefono;

    @NotBlank(message = "El correo no puede estar vacio")
    private String correo;

    @NotBlank(message = "La direccion es obligatoria")
    private String direccion;








}
