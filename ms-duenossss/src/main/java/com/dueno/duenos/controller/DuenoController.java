package com.dueno.duenos.controller;

import com.dueno.duenos.dto.DuenoRequest;
import com.dueno.duenos.dto.DuenoResponse;
import com.dueno.duenos.model.Dueno;
import com.dueno.duenos.service.DuenoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/duenos")
@RequiredArgsConstructor
public class DuenoController {

    @Autowired
    private final DuenoService duenoService;


    @PostMapping
    public ResponseEntity<DuenoResponse> guardar(@Valid @RequestBody DuenoRequest dto) {
        return ResponseEntity.status(201).body(duenoService.guardar(dto));
    }


    @GetMapping
    public ResponseEntity<List<DuenoResponse>> obtenerTodos(){
        return ResponseEntity.ok(duenoService.mostrarTodos());
    }

    @GetMapping("{id}")
    public ResponseEntity<DuenoResponse> obtenerXId(@PathVariable Long id) {
        return duenoService.obtenerXId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        if (duenoService.obtenerXId(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        duenoService.eliminar(id);
        return ResponseEntity.noContent().build(); //204
    }
    @PutMapping("{id}")
    public ResponseEntity<DuenoResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody DuenoRequest dto){
        return duenoService.actualizar(id,dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PatchMapping("{id}")
    public ResponseEntity<DuenoResponse> actualizarCampo(
            @PathVariable Long id,
            @RequestBody DuenoRequest dto){
        return duenoService.actualizarCampo(id,dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



}
