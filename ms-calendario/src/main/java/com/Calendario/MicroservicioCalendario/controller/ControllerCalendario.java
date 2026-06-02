package com.Calendario.MicroservicioCalendario.controller;


import com.Calendario.MicroservicioCalendario.dtos.ResponseCalendarioDTO;
import com.Calendario.MicroservicioCalendario.service.ServiceCalendario;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/calendario")
public class ControllerCalendario {

    private final ServiceCalendario serviceCalendario;

    public ControllerCalendario(ServiceCalendario serviceCalendario) {
        this.serviceCalendario = serviceCalendario;
    }

    //Buscar por fecha
    @GetMapping("/buscar")
    public ResponseEntity<List<ResponseCalendarioDTO>> buscarPorFecha(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(serviceCalendario.buscarPorFecha(fecha));
    }


    @GetMapping("/aleatorios")
    public ResponseEntity<List<ResponseCalendarioDTO>> obtenerAleatorios() {
        return ResponseEntity.ok(serviceCalendario.obtenerAleatorios());
    }
    @GetMapping
    public ResponseEntity<List<ResponseCalendarioDTO>> mostrarTodos() {
        return ResponseEntity.ok(serviceCalendario.obtenerTodos());
    }
}