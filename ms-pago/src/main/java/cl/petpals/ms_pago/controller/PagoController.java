package cl.petpals.ms_pago.controller;

import cl.petpals.ms_pago.dto.PagoRequestDto;
import cl.petpals.ms_pago.dto.PagoResponseDto;
import cl.petpals.ms_pago.modelo.EstadoPago;
import cl.petpals.ms_pago.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @PostMapping
    public ResponseEntity<PagoResponseDto> crear(@Valid @RequestBody PagoRequestDto dto){
        return ResponseEntity.status(201).body(pagoService.guardar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDto> obtenerPorId(@PathVariable Long id){
        return pagoService.obtenerPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PagoResponseDto>> obtenerTodos(){
        return ResponseEntity.ok(pagoService.obtenerTodos());
    }

    @GetMapping("/reserva/{idReserva}")
    public ResponseEntity<List<PagoResponseDto>> porReserva(@PathVariable Long idReserva){
        return ResponseEntity.ok(pagoService.listarPorReserva(idReserva));
    }

    @GetMapping("/dueno/{idDueno}")
    public ResponseEntity<List<PagoResponseDto>> porDueno(@PathVariable Long idDueno){
        return ResponseEntity.ok(pagoService.listarPorDueno(idDueno));
    }

    @GetMapping("/estado")
    public ResponseEntity<List<PagoResponseDto>> porEstado(@RequestParam EstadoPago estadoPago){
        return ResponseEntity.ok(pagoService.listarPorEstado(estadoPago));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<PagoResponseDto> actualizarEstado(@PathVariable Long id, @RequestParam EstadoPago nuevoEstado){
        return pagoService.actualizarEstado(id,nuevoEstado).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
