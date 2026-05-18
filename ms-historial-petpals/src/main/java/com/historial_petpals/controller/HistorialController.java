package com.historial_petpals.controller;

import com.historial_petpals.dto.HistorialRequest;
import com.historial_petpals.dto.HistorialResponse;
import com.historial_petpals.service.HistorialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
