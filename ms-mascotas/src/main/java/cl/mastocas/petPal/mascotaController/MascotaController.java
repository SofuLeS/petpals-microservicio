package cl.mastocas.petPal.mascotaController;

import cl.mastocas.petPal.mascotasDTO.MascotaRequestDTO;
import cl.mastocas.petPal.mascotasDTO.MascotaResponseDTO;
import cl.mastocas.petPal.mascotasService.IMascotaService;
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
@RequestMapping("/api/mascotas")
@Tag(name = "Mascotas", description = "Controlador para la gestión y consulta de las mascotas registradas en PetPals")
public class MascotaController {

    @Autowired
    private IMascotaService mascotaService;

    @PostMapping
    @Operation(summary = "Registrar una nueva mascota", description = "Crea una mascota en el sistema asociándola obligatoriamente al ID de un dueño existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mascota registrada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o error de validación externo")
    })

    public ResponseEntity<MascotaResponseDTO> crearMascota(@Valid @RequestBody MascotaRequestDTO dto) {
        MascotaResponseDTO respuesta = mascotaService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @GetMapping
    @Operation(summary = "Obtener todas las mascotas", description = "Retorna el listado completo de mascotas registradas en la base de datos.")
    @ApiResponse(responseCode = "200", description = "Listado de mascotas cargado correctamente")

    public ResponseEntity<List<MascotaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(mascotaService.obtenerTodas());
    }

    @GetMapping("/dueno/{idDueno}")
    @Operation(summary = "Listar mascotas por dueño", description = "Busca y devuelve el listado de todas las mascotas asociadas al ID de un dueño específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda completada de forma exitosa"),
            @ApiResponse(responseCode = "404", description = "ID de dueño no encontrado (No existe)")
    })
    public ResponseEntity<List<MascotaResponseDTO>> listarPorDueno(@PathVariable Long idDueno) {
        return ResponseEntity.ok(mascotaService.buscarPorDueno(idDueno));
    }
}