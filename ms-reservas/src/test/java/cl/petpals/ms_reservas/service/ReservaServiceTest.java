package cl.petpals.ms_reservas.service;

import cl.petpals.ms_reservas.model.EstadoReserva;
import cl.petpals.ms_reservas.model.Reserva;
import cl.petpals.ms_reservas.repository.ReservaRepository;
import cl.petpals.ms_reservas.service.HistorialAsyncService;
import cl.petpals.ms_reservas.service.ReservaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests del ReservaService")
public class ReservaServiceTest {

    @Mock private ReservaRepository reservaRepository;
    @Mock private HistorialAsyncService historialAsyncService;
    @Mock private WebClient.Builder webClientBuilder;
    @InjectMocks private ReservaService reservaService;

    private Reserva reservaFake() {
        return new Reserva(1L, 1L, 1L, 1L, 1L,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(3),
                EstadoReserva.PENDIENTE);
    }

    @Test
    @DisplayName("Debe retornar todas las reservas")
    void testObtenerTodas() {
        when(reservaRepository.findAll()).thenReturn(List.of(reservaFake()));
        var resultado = reservaService.obtenerTodas();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Debe retornar reserva por ID")
    void testObtenerPorId() {
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reservaFake()));
        var resultado = reservaService.obtenerPorId(1L);
        assertTrue(resultado.isPresent());
        assertEquals(EstadoReserva.PENDIENTE, resultado.get().getEstadoReserva());
    }

    @Test
    @DisplayName("Debe retornar vacío si reserva no existe")
    void testObtenerPorIdNoExiste() {
        when(reservaRepository.findById(99L)).thenReturn(Optional.empty());
        var resultado = reservaService.obtenerPorId(99L);
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Debe listar reservas por cuidador")
    void testListarPorCuidador() {
        when(reservaRepository.findByIdCuidador(1L)).thenReturn(List.of(reservaFake()));
        var resultado = reservaService.listarPorCuidador(1L);
        assertFalse(resultado.isEmpty());
    }

    @Test
    @DisplayName("Debe listar reservas por estado")
    void testListarPorEstado() {
        when(reservaRepository.findByEstadoReserva(EstadoReserva.PENDIENTE))
                .thenReturn(List.of(reservaFake()));
        var resultado = reservaService.listarPorEstado(EstadoReserva.PENDIENTE);
        assertFalse(resultado.isEmpty());
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar reserva inexistente")
    void testEliminarNoExiste() {
        when(reservaRepository.existsById(99L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> reservaService.eliminar(99L));
    }

    @Test
    @DisplayName("Debe actualizar estado de reserva existente")
    void testActualizarEstado() {
        Reserva reserva = reservaFake();
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any())).thenReturn(reserva);
        var resultado = reservaService.actualizarEstado(1L, EstadoReserva.ACEPTADA);
        assertTrue(resultado.isPresent());
    }
}
