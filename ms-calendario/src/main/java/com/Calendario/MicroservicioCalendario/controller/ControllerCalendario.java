package com.Calendario.MicroservicioCalendario.controller;

import com.Calendario.MicroservicioCalendario.dtos.ResponseCalendarioDTO;
import com.Calendario.MicroservicioCalendario.model.ModelCalendario;
import com.Calendario.MicroservicioCalendario.repository.RepositoryCalendario;
import com.Calendario.MicroservicioCalendario.service.ServiceCalendario;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/calendario")
public class ControllerCalendario {

    private final ServiceCalendario serviceCalendario;
    private final RepositoryCalendario repositoryCalendario;

    public ControllerCalendario(ServiceCalendario serviceCalendario, RepositoryCalendario repositoryCalendario) {
        this.serviceCalendario = serviceCalendario;
        this.repositoryCalendario = repositoryCalendario;
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<ResponseCalendarioDTO>> buscarPorFecha(
            @PathVariable("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(serviceCalendario.buscarPorFecha(fecha));

    }

    @PostMapping
    public ResponseEntity<ResponseCalendarioDTO> crearCalendario(@RequestBody ModelCalendario nuevoCalendario) {
        ResponseCalendarioDTO creado = serviceCalendario.guardarCalendario(nuevoCalendario);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCalendarioDTO> obtenerPorId(@PathVariable Long id) {
        java.util.Optional<ModelCalendario> calendarioOpt = repositoryCalendario.findById(id);

        if (calendarioOpt.isPresent()) {
            ResponseCalendarioDTO dto = serviceCalendario.obtenerPorId(calendarioOpt.get());
            if (dto != null) {
                return ResponseEntity.ok(dto);
            }
        }

        return ResponseEntity.notFound().build();
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