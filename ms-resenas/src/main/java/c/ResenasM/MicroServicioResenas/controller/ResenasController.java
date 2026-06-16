package c.ResenasM.MicroServicioResenas.controller;

import c.ResenasM.MicroServicioResenas.dto.ResenaRequestDTO;
import c.ResenasM.MicroServicioResenas.dto.ResenaResponseDTO;
import c.ResenasM.MicroServicioResenas.service.IResenaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resenas")
@Tag(name = "Reseñas", description = "Controlador para la gestión y consulta de reseñas de los cuidadores de PetPals")
public class ResenasController {

    @Autowired
    private IResenaService resenaService;

    // Endpoint para crear una reseña
    @PostMapping
    @Operation(summary = "Crear una nueva reseña", description = "Registra una calificación con comentarios asociando una reserva, un dueño y un cuidador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reseña creada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o faltantes")
    })
    public ResponseEntity<ResenaResponseDTO> crearResena(@Valid @RequestBody ResenaRequestDTO dto) {
        ResenaResponseDTO respuesta = resenaService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    // Obtener todas las reseñas
    @GetMapping
    @Operation(summary = "Obtener todas las reseñas", description = "Retorna el listado completo de comentarios y estrellas registrados en el sistema.")
    @ApiResponse(responseCode = "200", description = "Listado de reseñas cargado correctamente")
    public ResponseEntity<List<ResenaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(resenaService.obtenerTodasLasResenas());
    }

    // Obtener reseñas de un cuidador específico
    @GetMapping("/cuidador/{idCuidador}")
    @Operation(summary = "Listar reseñas por cuidador", description = "Busca y retorna todas las evaluaciones que pertenecen a un cuidador específico mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda completada de forma exitosa"),
            @ApiResponse(responseCode = "404", description = "ID de cuidador no encontrado en los registros")
    })
    public ResponseEntity<List<ResenaResponseDTO>> listarPorCuidador(@PathVariable Long idCuidador) {
        return ResponseEntity.ok(resenaService.listarPorCuidador(idCuidador));
    }
}