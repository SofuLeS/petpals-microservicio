package com.MUbicacion.MicroServicioUbicacion.controller;

import com.MUbicacion.MicroServicioUbicacion.dto.RequestDTO;
import com.MUbicacion.MicroServicioUbicacion.dto.ResponseDTO;
import com.MUbicacion.MicroServicioUbicacion.service.ServiceUbicacion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ubicaciones")
@Tag(name = "Ubicaciones", description = "Controlador para la gestión y geolocalización de cuidadores en PetPals")
public class ControllerUbicacion {

    @Autowired
    private ServiceUbicacion service;

    @PostMapping
    @Operation(summary = "Registrar ubicación", description = "Crea una nueva ubicación asociándola a un cuidador validado externamente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ubicación creada con éxito"),
            @ApiResponse(responseCode = "404", description = "El Cuidador no existe o el microservicio remoto está fuera de línea")
    })
    public ResponseEntity<ResponseDTO> crear(@RequestBody RequestDTO request) {
        ResponseDTO respuesta = service.crearUbicacion(request);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/comuna/{comuna}")
    @Operation(summary = "Buscar por comuna", description = "Filtra y retorna un listado de cuidadores disponibles en una comuna específica.")

    public ResponseEntity<List<ResponseDTO>> buscarPorComuna(@PathVariable String comuna) {
        List<ResponseDTO> ubicaciones = service.buscarPorComuna(comuna);
        return ResponseEntity.ok(ubicaciones);
    }

    @GetMapping
    @Operation(summary = "Mostrar todas las ubicaciones", description = "Retorna el historial completo de zonas e identificadores registrados.")

    public ResponseEntity<List<ResponseDTO>> mostrarTodas() {
        return ResponseEntity.ok(service.obtenerTodas());
    }
}