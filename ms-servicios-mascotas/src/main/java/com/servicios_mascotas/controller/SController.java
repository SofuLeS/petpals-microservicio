package com.servicios_mascotas.controller;

import com.servicios_mascotas.dto.SMRequest;
import com.servicios_mascotas.dto.SMResponse;
import com.servicios_mascotas.service.SServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.TableGenerator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
@Tag(name = "Servicios", description = "Gestion de servicios ofrecidos")
public class SController {

    @Autowired
    public SServices sServices;

    @Operation(summary = "Guardar servicios",
    description = "Guarda servicios que se ofrecen")
    @ApiResponses({
         @ApiResponse(responseCode = "201", description = "Servicio guardado correctamente"),
         @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
         @ApiResponse(responseCode = "409", description = "El servicio ya existe"),
         @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @PostMapping
    public ResponseEntity<SMResponse> guardar(@Valid @RequestBody SMRequest dto){
        return ResponseEntity.status(201).body(sServices.guardar(dto));
    }

    @Operation(summary = "Listar servicios",
            description = "Obtiene todos los servicios registrados")
    @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Lista de servicios obtenida correctamente"),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @GetMapping
    public ResponseEntity<List<SMResponse>> listar(){
        return ResponseEntity.ok(sServices.listar());
    }

    @Operation(summary = "Obtener servicio por ID",
            description = "Obtiene un servicio según el ID ingresado")
    @ApiResponses({
         @ApiResponse(responseCode = "200", description = "Servicio encontrado"),
         @ApiResponse(responseCode = "404", description = "Servicio no encontrado"),
         @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @GetMapping("{id}")
    public ResponseEntity<SMResponse> obtenerXId(@PathVariable Long id) {
        return sServices.obtenerXId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar servicio",
            description = "Elimina un servicio según el ID ingresado")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Servicio eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Servicio no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        if (sServices.obtenerXId(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        sServices.eliminar(id);
        return ResponseEntity.noContent().build(); //204
    }


    @Operation(summary = "Actualizar servicio",
            description = "Actualiza todos los datos de un servicio existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Servicio actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Servicio no encontrado"),
        @ApiResponse(responseCode = "409", description = "Conflicto de datos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("{id}")
    public ResponseEntity<SMResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody SMRequest dto){
        return sServices.actualizar(id,dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar parcialmente un servicio",
            description = "Actualiza uno o más campos de un servicio existente"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Servicio actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "Servicio no encontrado"),
        @ApiResponse(responseCode = "409", description = "Conflicto de datos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PatchMapping("{id}")
    public ResponseEntity<SMResponse> actualizarCampo(
            @PathVariable Long id,
            @RequestBody SMRequest dto){
        return sServices.actCampo(id,dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }













}
