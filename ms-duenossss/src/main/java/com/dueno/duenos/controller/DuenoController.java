package com.dueno.duenos.controller;

import com.dueno.duenos.dto.DuenoRequest;
import com.dueno.duenos.dto.DuenoResponse;
import com.dueno.duenos.model.Dueno;
import com.dueno.duenos.service.DuenoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.*;
import static org.springframework.hateoas
        .server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
@Tag(name = "Dueño", description = "gestion de dueños")
@RestController
@RequestMapping("/api/duenos")
@RequiredArgsConstructor

public class DuenoController {

    @Autowired
    private final DuenoService duenoService;


    @Operation(summary = "Guardar dueños",
     description = "Guarda a los dueños que se ingresan"
    )
    @ApiResponses({
         @ApiResponse(responseCode = "200", description = "Exito",
         content = @Content(schema = @Schema(implementation = DuenoResponse.class ))
         ),
         @ApiResponse(responseCode = "201", description = "Dueño creado correctamente"),
         @ApiResponse(responseCode = "409", description = "Ya existe un dueño con esos datos"),
         @ApiResponse(responseCode = "500", description = "Error servidor")
    })
    @PostMapping
    public ResponseEntity<DuenoResponse> guardar(@Valid @RequestBody DuenoRequest dto) {
        return ResponseEntity.status(201).body(duenoService.guardar(dto));
    }








    @Operation(summary = "listar Dueños",
    description =  "Retorna a todos los Dueños registrados")
    @ApiResponses({
         @ApiResponse(responseCode = "200", description = "Dueño encontrado",
                 content = @Content(schema = @Schema(implementation = DuenoResponse.class ))),
         @ApiResponse(responseCode = "400", description = "ID inválido"),
         @ApiResponse(responseCode = "404", description = "Dueño no encontrado",content = @Content),
         @ApiResponse(responseCode = "500", description = "Error servidor")
    })
    @GetMapping
    public ResponseEntity<List<DuenoResponse>> obtenerTodos(){
        return ResponseEntity.ok(duenoService.mostrarTodos());
    }





    @Operation(summary = "Obtener dueños por ID",
     description = "Se obtiene el dueño del id ingresado")
    @ApiResponses({
         @ApiResponse(responseCode = "200", description = "Exito"),
         @ApiResponse(responseCode = "404" ,description = "Dueño no encontrado"),
         @ApiResponse(responseCode = "400", description = "ID invalido"),
         @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("{id}")
    public ResponseEntity<DuenoResponse> obtenerXId(@PathVariable Long id) {
        return duenoService.obtenerXId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }




    @Operation(summary = "Eliminar dueño",
        description = "Elimina un dueño segun su ID")
    @ApiResponses({
          @ApiResponse(responseCode = "204", description = "Dueño eliminado correctamente"),
          @ApiResponse(responseCode = "404", description = "Dueño no encontrado"),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        if (duenoService.obtenerXId(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        duenoService.eliminar(id);
        return ResponseEntity.noContent().build(); //204
    }




    @Operation(summary = "Actualizar dueño",
    description = "Actualiza los datos de un dueño existente")
    @ApiResponses({
         @ApiResponse(responseCode = "200", description = "Dueño actualizado correctamente"),
         @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
         @ApiResponse(responseCode = "404", description = "Dueño no encontrado"),
         @ApiResponse(responseCode = "409", description = "Conflicto de datos"),
         @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("{id}")
    public ResponseEntity<DuenoResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody DuenoRequest dto){
        return duenoService.actualizar(id,dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }




    @Operation(summary = "Actualizar parcialmente un dueño",
    description = "Actualiza uno o más campos de un dueño existente")
    @ApiResponses({
         @ApiResponse(responseCode = "200", description = "Dueño actualizado correctamente"),
         @ApiResponse(responseCode = "400", description = "Datos inválidos"),
         @ApiResponse(responseCode = "404", description = "Dueño no encontrado"),
         @ApiResponse(responseCode = "409", description = "Conflicto de datos"),
         @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PatchMapping("{id}")
    public ResponseEntity<DuenoResponse> actualizarCampo(
            @PathVariable Long id,
            @RequestBody DuenoRequest dto){
        return duenoService.actualizarCampo(id,dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



}
