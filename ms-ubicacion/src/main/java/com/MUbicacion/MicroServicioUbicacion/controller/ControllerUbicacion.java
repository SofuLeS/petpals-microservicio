package com.MUbicacion.MicroServicioUbicacion.controller;


import com.MUbicacion.MicroServicioUbicacion.dto.RequestDTO;
import com.MUbicacion.MicroServicioUbicacion.dto.ResponseDTO;
import com.MUbicacion.MicroServicioUbicacion.model.ModelUbicacion;
import com.MUbicacion.MicroServicioUbicacion.repository.RepositoryUbicacion;
import com.MUbicacion.MicroServicioUbicacion.service.ServiceUbicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


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
    @GetMapping("/comuna/{comuna}")
    public ResponseEntity<List<ResponseDTO>> buscarPorComuna(@PathVariable String comuna) {
        List<ResponseDTO> ubicaciones = service.buscarPorComuna(comuna);
        return ResponseEntity.ok(ubicaciones);
    }
    @GetMapping
    public ResponseEntity<List<ResponseDTO>> mostrarTodas() {
        return ResponseEntity.ok(service.obtenerTodas());
    }
}