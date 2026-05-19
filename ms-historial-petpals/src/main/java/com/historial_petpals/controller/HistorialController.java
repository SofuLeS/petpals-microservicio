package com.historial_petpals.controller;

import com.historial_petpals.dto.HistorialRequest;
import com.historial_petpals.dto.HistorialResponse;
import com.historial_petpals.service.HistorialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial")
@RequiredArgsConstructor
public class HistorialController {

    @Autowired
    private final HistorialService historialService;

    @PostMapping
    public ResponseEntity<HistorialResponse> guardar(@Valid @RequestBody HistorialRequest dto){
        System.out.print("Entrando controller historial");
        return ResponseEntity.status(201).body(historialService.guardar(dto));
    }
    @GetMapping
    public ResponseEntity<List<HistorialResponse>> obtenerTodos(){
        return ResponseEntity.ok(historialService.mostrarTodos());
    }
    @GetMapping("{id}")
    public ResponseEntity<HistorialResponse> obtenerXId(@PathVariable Long id) {
        return historialService.obtenerXId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }









}
