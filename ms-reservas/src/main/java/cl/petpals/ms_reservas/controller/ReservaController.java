package cl.petpals.ms_reservas.controller;

import cl.petpals.ms_reservas.dto.ReservaRequestDto;
import cl.petpals.ms_reservas.dto.ReservaResponseDto;
import cl.petpals.ms_reservas.model.EstadoReserva;
import cl.petpals.ms_reservas.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    //Crear Reserva
    @PostMapping
    public ResponseEntity<ReservaResponseDto> crear(@Valid @RequestBody ReservaRequestDto dto){
        return new ResponseEntity<>(reservaService.guardar(dto), HttpStatus.CREATED);
    }

    //Obtener todas
    @GetMapping
    public ResponseEntity<ReservaResponseDto> obtenerPorId(@PathVariable Long id){
        return reservaService.obtenerPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //Actualizar el estado
    @PatchMapping("/{id}/estado")
    public ResponseEntity<ReservaResponseDto> actializarEstado(@PathVariable Long id, @RequestParam EstadoReserva nuevoEstado){
        return reservaService.actualizarEstado(id, nuevoEstado).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        reservaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
