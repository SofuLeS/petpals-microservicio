package com.Calendario.MicroservicioCalendario.controller;

import com.Calendario.MicroservicioCalendario.dtos.RequestCalendarioDTO;
import com.Calendario.MicroservicioCalendario.dtos.ResponseCalendarioDTO;
import com.Calendario.MicroservicioCalendario.model.ModelCalendario;
import com.Calendario.MicroservicioCalendario.repository.RepositoryCalendario;
import com.Calendario.MicroservicioCalendario.service.ServiceCalendario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/calendario")
@Tag(name = "Calendario", description = "Controlador para gestionar las agendas y disponibilidades de los cuidadores")
public class ControllerCalendario {

    private final ServiceCalendario serviceCalendario;
    private final RepositoryCalendario repositoryCalendario;

    public ControllerCalendario(ServiceCalendario serviceCalendario, RepositoryCalendario repositoryCalendario) {
        this.serviceCalendario = serviceCalendario;
        this.repositoryCalendario = repositoryCalendario;
    }

    @PostMapping
    @Operation(summary = "Crear bloque de calendario", description = "Registra un nuevo espacio de tiempo disponible para un cuidador.")
    @ApiResponse(responseCode = "201", description = "Bloque de calendario agendado con éxito")
    public ResponseEntity<ResponseCalendarioDTO> crearCalendario(@Valid @RequestBody RequestCalendarioDTO dto) {
        ResponseCalendarioDTO creado = serviceCalendario.guardarCalendario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/fecha/{fecha}")
    @Operation(summary = "Buscar agendas por fecha", description = "Retorna todos los bloques disponibles en un día específico con datos de sus cuidadores.")
    public ResponseEntity<List<ResponseCalendarioDTO>> buscarPorFecha(
            @PathVariable("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(serviceCalendario.buscarPorFecha(fecha));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener agenda por ID", description = "Busca un registro específico de la agenda por su identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro encontrado"),
            @ApiResponse(responseCode = "404", description = "No se encontró el ID en las agendas o el cuidador no está disponible")
    })
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
    @Operation(summary = "Obtener agendas aleatorias", description = "Devuelve una selección de turnos al azar para sugerencias en la app.")
    public ResponseEntity<List<ResponseCalendarioDTO>> obtenerAleatorios() {
        return ResponseEntity.ok(serviceCalendario.obtenerAleatorios());
    }

    @GetMapping
    @Operation(summary = "Mostrar todas las agendas", description = "Lista el historial completo de todos los turnos del sistema.")
    public ResponseEntity<List<ResponseCalendarioDTO>> mostrarTodos() {
        return ResponseEntity.ok(serviceCalendario.obtenerTodos());
    }
}