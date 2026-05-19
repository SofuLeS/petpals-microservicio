package com.servicios_mascotas.controller;

import com.servicios_mascotas.dto.SMRequest;
import com.servicios_mascotas.dto.SMResponse;
import com.servicios_mascotas.service.SServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
public class SController {

    @Autowired
    public SServices sServices;

    @PostMapping
    public ResponseEntity<SMResponse> guardar(@Valid @RequestBody SMRequest dto){
        return ResponseEntity.status(201).body(sServices.guardar(dto));
    }

    @GetMapping
    public ResponseEntity<List<SMResponse>> listar(){
        return ResponseEntity.ok(sServices.listar());
    }

    @GetMapping("{id}")
    public ResponseEntity<SMResponse> obtenerXId(@PathVariable Long id) {
        return sServices.obtenerXId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        if (sServices.obtenerXId(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        sServices.eliminar(id);
        return ResponseEntity.noContent().build(); //204
    }

    @PutMapping("{id}")
    public ResponseEntity<SMResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody SMRequest dto){
        return sServices.actualizar(id,dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("{id}")
    public ResponseEntity<SMResponse> actualizarCampo(
            @PathVariable Long id,
            @RequestBody SMRequest dto){
        return sServices.actCampo(id,dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }













}
