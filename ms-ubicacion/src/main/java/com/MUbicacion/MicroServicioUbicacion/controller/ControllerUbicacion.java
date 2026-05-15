package com.MUbicacion.MicroServicioUbicacion.controller;


import com.MUbicacion.MicroServicioUbicacion.dto.RequestDTO;
import com.MUbicacion.MicroServicioUbicacion.dto.ResponseDTO;
import com.MUbicacion.MicroServicioUbicacion.service.ServiceUbicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/ubicaciones")
public class ControllerUbicacion {
    @Autowired
    private ServiceUbicacion service;

    @PostMapping
    public ResponseEntity<ResponseDTO> crear(@RequestBody RequestDTO request) {
    //llamamos al service
        ResponseDTO respuesta = service.crearUbicacion(request);
        return ResponseEntity.ok(respuesta); // devolvemos la respuesta

    }
}
