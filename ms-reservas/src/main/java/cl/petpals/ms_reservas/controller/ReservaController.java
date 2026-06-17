package cl.petpals.ms_reservas.controller;

import cl.petpals.ms_reservas.dto.ReservaRequestDto;
import cl.petpals.ms_reservas.dto.ReservaResponseDto;
import cl.petpals.ms_reservas.model.EstadoReserva;
import cl.petpals.ms_reservas.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Reservas", description = "Gestión de reservas de cuidado de mascotas")
@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @Operation(summary = "Crear reserva", description = "Crea una nueva reserva de cuidado")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reserva creada correctamente",
                    content = @Content(schema = @Schema(implementation = ReservaResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<ReservaResponseDto> crear(@Valid @RequestBody ReservaRequestDto dto){
        return new ResponseEntity<>(reservaService.guardar(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener reserva por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDto> obtenerPorId(@PathVariable Long id){
        return reservaService.obtenerPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar todas las reservas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<ReservaResponseDto>> obtenerTodas(){
        return ResponseEntity.ok(reservaService.obtenerTodas());
    }

    @Operation(summary = "Listar reservas por cuidador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "Sin reservas para ese cuidador")
    })
    @GetMapping("/cuidador/{idCuidador}")
    public ResponseEntity<List<ReservaResponseDto>> porCuidador(@PathVariable Long idCuidador){
        return ResponseEntity.ok(reservaService.listarPorCuidador(idCuidador));
    }

    @Operation(summary = "Listar reservas por dueño")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "Sin reservas para ese dueño")
    })
    @GetMapping("/dueno/{idDueno}")
    public ResponseEntity<List<ReservaResponseDto>> porDueno(@PathVariable Long idDueno){
        return ResponseEntity.ok(reservaService.listarPorDueno(idDueno));
    }

    @Operation(summary = "Actualizar estado de reserva")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
            @ApiResponse(responseCode = "400", description = "Estado inválido")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<ReservaResponseDto> actualizarEstado(@PathVariable Long id, @RequestParam EstadoReserva nuevoEstado){
        return reservaService.actualizarEstado(id, nuevoEstado).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar reserva")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Reserva eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        reservaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar reservas por estado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Estado inválido. Valores válidos: PENDIENTE, CONFIRMADA, CANCELADA, COMPLETADA")
    })
    @GetMapping("/estado")
    public ResponseEntity<List<ReservaResponseDto>> porEstado(@RequestParam EstadoReserva estado) {
        return ResponseEntity.ok(reservaService.listarPorEstado(estado));
    }

    @Operation(summary = "Listar reservas por mascota")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    })
    @GetMapping("/mascota/{idMascota}")
    public ResponseEntity<List<ReservaResponseDto>> porMascota(@PathVariable Long idMascota) {
        return ResponseEntity.ok(reservaService.listarPorMascota(idMascota));
    }

    @Operation(summary = "Listar reservas por rango de fecha")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Formato de fecha inválido (usar yyyy-MM-dd)")
    })
    @GetMapping("/fecha")
    public ResponseEntity<List<ReservaResponseDto>> porFecha(
            @RequestParam LocalDate desde, @RequestParam LocalDate hasta) {
        return ResponseEntity.ok(reservaService.listarPorFecha(desde, hasta));
    }

    @Operation(summary = "Listar reservas por cuidador y estado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
            @ApiResponse(responseCode = "400", description = "Estado inválido")
    })
    @GetMapping("/cuidador/{idCuidador}/estado")
    public ResponseEntity<List<ReservaResponseDto>> porCuidadorYEstado(
            @PathVariable Long idCuidador, @RequestParam EstadoReserva estado) {
        return ResponseEntity.ok(reservaService.listarPorCuidadorYEstado(idCuidador, estado));
    }
}