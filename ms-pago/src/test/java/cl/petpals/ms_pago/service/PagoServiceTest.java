package cl.petpals.ms_pago.service;

import cl.petpals.ms_pago.modelo.EstadoPago;
import cl.petpals.ms_pago.modelo.MetodoPago;
import cl.petpals.ms_pago.modelo.Pago;
import cl.petpals.ms_pago.repository.PagoRepository;
import cl.petpals.ms_pago.service.PagoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests del PagoService")
public class PagoServiceTest {

    @Mock private PagoRepository pagoRepository;
    @Mock private WebClient.Builder webClientBuilder;
    @InjectMocks private PagoService pagoService;

    private Pago pagoFake() {
        Pago p = new Pago();
        p.setId(1L); p.setIdReserva(1L); p.setIdDueno(1L);
        p.setMonto(new BigDecimal("15000")); p.setMetodoPago(MetodoPago.EFECTIVO);
        p.setEstadoPago(EstadoPago.PENDIENTE); p.setFechaPago(LocalDateTime.now());
        p.setDescripcion("Pago prueba");
        return p;
    }

    @Test
    @DisplayName("Debe retornar todos los pagos")
    void testObtenerTodos() {
        when(pagoRepository.findAll()).thenReturn(List.of(pagoFake()));
        var resultado = pagoService.obtenerTodos();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Debe retornar pago por ID")
    void testObtenerPorId() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pagoFake()));
        var resultado = pagoService.obtenerPorId(1L);
        assertTrue(resultado.isPresent());
        assertEquals(MetodoPago.EFECTIVO, resultado.get().getMetodoPago());
    }

    @Test
    @DisplayName("Debe retornar vacío si pago no existe")
    void testObtenerPorIdNoExiste() {
        when(pagoRepository.findById(99L)).thenReturn(Optional.empty());
        var resultado = pagoService.obtenerPorId(99L);
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Debe listar pagos por estado")
    void testListarPorEstado() {
        when(pagoRepository.findByEstadoPago(EstadoPago.PENDIENTE)).thenReturn(List.of(pagoFake()));
        var resultado = pagoService.listarPorEstado(EstadoPago.PENDIENTE);
        assertFalse(resultado.isEmpty());
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar pago inexistente")
    void testEliminarNoExiste() {
        when(pagoRepository.existsById(99L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> pagoService.eliminar(99L));
    }

    @Test
    @DisplayName("No debe cambiar método de pago si no está PENDIENTE")
    void testCambiarMetodoPagoNoPermitido() {
        Pago pago = pagoFake();
        pago.setEstadoPago(EstadoPago.COMPLETADO);
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));
        assertThrows(RuntimeException.class,
                () -> pagoService.cambiarMetodoPago(1L, MetodoPago.TRANSFERENCIA));
    }

    @Test
    @DisplayName("Debe cambiar método de pago si está PENDIENTE")
    void testCambiarMetodoPagoPermitido() {
        Pago pago = pagoFake();
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));
        when(pagoRepository.save(any())).thenReturn(pago);
        var resultado = pagoService.cambiarMetodoPago(1L, MetodoPago.TRANSFERENCIA);
        assertNotNull(resultado);
    }
}