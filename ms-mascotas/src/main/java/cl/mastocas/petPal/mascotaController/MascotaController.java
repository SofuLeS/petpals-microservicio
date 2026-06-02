package cl.mastocas.petPal.mascotaController;

import cl.mastocas.petPal.mascotasDTO.MascotaRequestDTO;
import cl.mastocas.petPal.mascotasDTO.MascotaResponseDTO;
import cl.mastocas.petPal.mascotasService.IMascotaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 Se comunica exclusivamente con el Service.
*/
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final IMascotaService mascotaService;

    // Obtener todas las mascotas
    @GetMapping
    public ResponseEntity<List<MascotaResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(mascotaService.obtenerTodos());
    }

    //Obtener una mascota específica
    @GetMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return mascotaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear nueva mascota
    @PostMapping
    public ResponseEntity<MascotaResponseDTO> crear(@Valid @RequestBody MascotaRequestDTO dto) {
        return ResponseEntity.status(201).body(mascotaService.guardar(dto));
    }

    //Actualizar datos de una mascota
    @PutMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody MascotaRequestDTO dto) {
        // Lanza un objeto o lanza excepción
        return ResponseEntity.ok(mascotaService.actualizar(id, dto));
    }
    // Obtener mascotas por el ID del dueño
    @GetMapping("/dueno/{idDueno}")
    public ResponseEntity<List<MascotaResponseDTO>> obtenerPorIdDueno(@PathVariable Long idDueno) {
        List<MascotaResponseDTO> mascotas = mascotaService.buscarPorDueno(idDueno);
        return ResponseEntity.ok(mascotas);
    }

    // Eliminar mascota
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mascotaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}