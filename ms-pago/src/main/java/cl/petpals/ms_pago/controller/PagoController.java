package cl.petpals.ms_pago.controller;

import cl.petpals.ms_pago.dto.PagoRequestDto;
import cl.petpals.ms_pago.dto.PagoResponseDto;
import cl.petpals.ms_pago.modelo.EstadoPago;
import cl.petpals.ms_pago.modelo.MetodoPago;
import cl.petpals.ms_pago.service.PagoService;
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

import java.util.List;

@Tag(name = "Pagos", description = "Gestión de pagos de reservas")
@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @Operation(summary = "Crear pago", description = "Registra un nuevo pago asociado a una reserva")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pago creado correctamente",
                    content = @Content(schema = @Schema(implementation = PagoResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<PagoResponseDto> crear(@Valid @RequestBody PagoRequestDto dto){
        return ResponseEntity.status(201).body(pagoService.guardar(dto));
    }

    @Operation(summary = "Obtener pago por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pago encontrado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDto> obtenerPorId(@PathVariable Long id){
        return pagoService.obtenerPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar todos los pagos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente",
                    content = @Content(schema = @Schema(implementation = PagoResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<PagoResponseDto>> obtenerTodos(){
        return ResponseEntity.ok(pagoService.obtenerTodos());
    }

    @Operation(summary = "Listar pagos por reserva")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    })
    @GetMapping("/reserva/{idReserva}")
    public ResponseEntity<List<PagoResponseDto>> porReserva(@PathVariable Long idReserva){
        return ResponseEntity.ok(pagoService.listarPorReserva(idReserva));
    }

    @Operation(summary = "Listar pagos por dueño")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    })
    @GetMapping("/dueno/{idDueno}")
    public ResponseEntity<List<PagoResponseDto>> porDueno(@PathVariable Long idDueno){
        return ResponseEntity.ok(pagoService.listarPorDueno(idDueno));
    }

    @Operation(summary = "Listar pagos por estado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Estado inválido. Valores válidos: PENDIENTE, COMPLETADO, FALLIDO, REEMBOLSADO")
    })
    @GetMapping("/estado")
    public ResponseEntity<List<PagoResponseDto>> porEstado(@RequestParam EstadoPago estadoPago){
        return ResponseEntity.ok(pagoService.listarPorEstado(estadoPago));
    }

    @Operation(summary = "Actualizar estado de pago")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado"),
            @ApiResponse(responseCode = "400", description = "Estado inválido")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<PagoResponseDto> actualizarEstado(@PathVariable Long id, @RequestParam EstadoPago nuevoEstado){
        return pagoService.actualizarEstado(id, nuevoEstado).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar pago")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pago eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cambiar método de pago", description = "Solo permitido si el pago está en estado PENDIENTE")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Método de pago actualizado"),
            @ApiResponse(responseCode = "400", description = "El pago no está en estado PENDIENTE"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @PatchMapping("/{id}/metodo-pago")
    public ResponseEntity<PagoResponseDto> cambiarMetodoPago(@PathVariable Long id, @RequestBody MetodoPago nuevoMetodo) {
        return ResponseEntity.ok(pagoService.cambiarMetodoPago(id, nuevoMetodo));
    }

    @Operation(summary = "Listar pagos por método de pago")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Método inválido. Valores válidos: TARJETA_DEBITO, TARJETA_CREDITO, TRANSFERENCIA, EFECTIVO")
    })
    @GetMapping("/metodo")
    public ResponseEntity<List<PagoResponseDto>> porMetodo(@RequestParam MetodoPago metodoPago) {
        return ResponseEntity.ok(pagoService.listarPorMetodo(metodoPago));
    }

    @Operation(summary = "Listar pagos por rango de monto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Valores de monto inválidos")
    })
    @GetMapping("/monto")
    public ResponseEntity<List<PagoResponseDto>> porMonto(
            @RequestParam Double min, @RequestParam Double max) {
        return ResponseEntity.ok(pagoService.listarPorRangoMonto(min, max));
    }
}