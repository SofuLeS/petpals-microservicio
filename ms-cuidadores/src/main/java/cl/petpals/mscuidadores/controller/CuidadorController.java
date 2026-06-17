package cl.petpals.mscuidadores.controller;

import cl.petpals.mscuidadores.dto.CuidadorRequestDto;
import cl.petpals.mscuidadores.dto.CuidadorResponseDto;
import cl.petpals.mscuidadores.service.CuidadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Cuidador", description = "gestion de cuidadores de mascota")
@RestController
@RequestMapping("/api/cuidadores")
@RequiredArgsConstructor
public class CuidadorController {

    private final CuidadorService cuidadorService;


    @Operation(summary = "Listar todos los cuidadores",
            description = "Mostrar todos los jugadores"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente",
                    content = @Content(schema = @Schema(implementation = CuidadorResponseDto.class ))
            ),
            @ApiResponse(responseCode = "500", description = "Error servidor")
    })
    @GetMapping
    public ResponseEntity<List<CuidadorResponseDto>> obtenerTods(){
        return ResponseEntity.ok(cuidadorService.obtenerTodos());
    }

    @Operation(summary = "Obtener cuidador por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuidador encontrado"),
            @ApiResponse(responseCode = "404", description = "Cuidador no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CuidadorResponseDto> obtenerPorId(@PathVariable Long id){
        return cuidadorService.obtenerPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nuevo cuidador")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cuidador creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<CuidadorResponseDto> crear(@Valid @RequestBody CuidadorRequestDto dto){
        return  ResponseEntity.status(201).body(cuidadorService.guardar(dto));
    }

    @Operation(summary = "Actualizar cuidador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuidador actualizado"),
            @ApiResponse(responseCode = "404", description = "Cuidador no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CuidadorResponseDto> actualozar(@PathVariable Long id, @Valid @RequestBody CuidadorRequestDto dto){
        return cuidadorService.actualizar(id,dto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar cuidador")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Cuidador no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id){
        if (cuidadorService.obtenerPorId(id).isEmpty()){
            Map<String, String> error = new LinkedHashMap<>();
            error.put("error", " Cuidador con id " +id+ " no existe");
            error.put("motivo", "No se encontro cuidaro con esa id en la base de datos");
            return ResponseEntity.status(404).body(error);
        }
        cuidadorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar cuidadores por nombre")
    @GetMapping("/buscar")
    public ResponseEntity<List<CuidadorResponseDto>> buscarPorNombre(@RequestParam String nombre){
        return ResponseEntity.ok(cuidadorService.buscarPorNombre(nombre));
    }

    @Operation(summary = "Buscar cuidadores por especialidad/categoría")
    @GetMapping("/especialidad")
    public ResponseEntity<List<CuidadorResponseDto>> buscarPorEspecialidad(@RequestParam String animal){
        return ResponseEntity.ok(cuidadorService.buscarPorEspecialidad(animal));
    }

    @Operation(summary = "Listar cuidadores disponibles")
    @GetMapping("/disponibilidad")
    public ResponseEntity<List<CuidadorResponseDto>> listarDisponible(){
        return ResponseEntity.ok(cuidadorService.listarDisponibles());
    }

    @Operation(summary = "Buscar cuidadores por cantidad mínima de mascotas cuidadas")
    @GetMapping("/mascotas-cuidadas")
    public ResponseEntity<List<CuidadorResponseDto>> buscarPorMascotasCuidadas(@RequestParam Integer cantidad){
        return ResponseEntity.ok(cuidadorService.buscarPorMascotasCuidadas(cantidad));
    }

    @Operation(summary = "Buscar cuidadores por años mínimos de experiencia")
    @GetMapping("/experiencia")
    public ResponseEntity<List<CuidadorResponseDto>> buscarPorExperiencia(@RequestParam Integer anos){
        return ResponseEntity.ok(cuidadorService.buscarPorAniosExperincia(anos));
    }

    @Operation(summary = "Buscar cuidadores por apellido")
    @GetMapping("/buscar-apellido")
    public ResponseEntity<List<CuidadorResponseDto>> buscarPorApellido(@RequestParam String apellido) {
        return ResponseEntity.ok(cuidadorService.buscarPorApellido(apellido));
    }

    @Operation(summary = "Listar cuidadores ordenados por mascotas cuidadas (mayor a menor)")
    @GetMapping("/top-calificacion")
    public ResponseEntity<List<CuidadorResponseDto>> topPorCalificacion() {
        return ResponseEntity.ok(cuidadorService.listarPorCalificacion());
    }

    @Operation(summary = "Listar cuidadores ordenados por mascotas cuidadas (mayor a menor)")
    @GetMapping("/top-mascotas")
    public ResponseEntity<List<CuidadorResponseDto>> topPorMascotas() {
        return ResponseEntity.ok(cuidadorService.listarPorMascotas());
    }

}
