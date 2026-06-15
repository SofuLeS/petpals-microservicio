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

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    //Crear Reserva
    @PostMapping
    public ResponseEntity<ReservaResponseDto> crear(@Valid @RequestBody ReservaRequestDto dto){
        return new ResponseEntity<>(reservaService.guardar(dto), HttpStatus.CREATED);
    }

    //Obtener por id
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDto> obtenerPorId(@PathVariable Long id){
        return reservaService.obtenerPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //Obtener todas
    @GetMapping
    public ResponseEntity<List<ReservaResponseDto>> obtenerTodas(){
        return ResponseEntity.ok(reservaService.obtenerTodas());
    }

    //Buscar por cuidador
    @GetMapping("/cuidador/{idCuidador}")
    public ResponseEntity<List<ReservaResponseDto>> porCuidador(@PathVariable Long idCuidador){
        return ResponseEntity.ok(reservaService.listarPorCuidador(idCuidador));
    }

    //Bucar por dueno
    @GetMapping("/dueno/{idDueno}")
    public  ResponseEntity<List<ReservaResponseDto>> porDueno(@PathVariable Long idDueno){
        return ResponseEntity.ok(reservaService.listarPorDueno(idDueno));
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

    @GetMapping("/estado")
    public ResponseEntity<List<ReservaResponseDto>> porEstado(@RequestParam EstadoReserva estado) {
        return ResponseEntity.ok(reservaService.listarPorEstado(estado));
    }

    @GetMapping("/mascota/{idMascota}")
    public ResponseEntity<List<ReservaResponseDto>> porMascota(@PathVariable Long idMascota) {
        return ResponseEntity.ok(reservaService.listarPorMascota(idMascota));
    }

    @GetMapping("/fecha")
    public ResponseEntity<List<ReservaResponseDto>> porFecha(
            @RequestParam LocalDate desde, @RequestParam LocalDate hasta) {
        return ResponseEntity.ok(reservaService.listarPorFecha(desde, hasta));
    }

    @GetMapping("/cuidador/{idCuidador}/estado")
    public ResponseEntity<List<ReservaResponseDto>> porCuidadorYEstado(
            @PathVariable Long idCuidador, @RequestParam EstadoReserva estado) {
        return ResponseEntity.ok(reservaService.listarPorCuidadorYEstado(idCuidador, estado));
    }
}
