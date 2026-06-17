package cl.petpals.ms_pago.controller;

import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import cl.petpals.ms_pago.dto.PagoRequestDto;
import cl.petpals.ms_pago.dto.PagoResponseDto;
import cl.petpals.ms_pago.modelo.EstadoPago;
import cl.petpals.ms_pago.modelo.MetodoPago;
import cl.petpals.ms_pago.service.PagoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(PagoController.class)
@DisplayName("Test del pagoController")
public class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PagoService pagoService;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private PagoResponseDto pagoDto;

    @BeforeEach
    void setUp() {
        pagoDto = new PagoResponseDto(
                1L,
                1L,
                1L,
                new BigDecimal("15000"),
                MetodoPago.EFECTIVO,
                EstadoPago.PENDIENTE,
                LocalDateTime.now(),
                "Pago prueba"
        );
    }

    @Test
    @DisplayName("GET /api/pagos - Listar todos")
    void testObtenerTodos() throws Exception {
        when(pagoService.obtenerTodos()).thenReturn(List.of(pagoDto));

        mockMvc.perform(get("/api/pagos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].metodoPago").value("EFECTIVO"))
                .andExpect(jsonPath("$[0].estadoPago").value("PENDIENTE"));
    }

    @Test
    @DisplayName("GET /api/pagos/{id} - Obtener por ID")
    void testObtenerPorId() throws Exception {
        when(pagoService.obtenerPorId(1L)).thenReturn(Optional.of(pagoDto));

        mockMvc.perform(get("/api/pagos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.monto").value(15000));
    }

    @Test
    @DisplayName("GET /api/pagos/{id} - No encontrado devuelve 404")
    void testObtenerPorIdNoEncontrado() throws Exception {
        when(pagoService.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/pagos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/pagos - Crear pago")
    void testCrear() throws Exception {
        PagoRequestDto request = new PagoRequestDto(
                1L, 1L, new BigDecimal("15000"), MetodoPago.EFECTIVO, "Pago prueba"
        );

        when(pagoService.guardar(any(PagoRequestDto.class))).thenReturn(pagoDto);

        mockMvc.perform(post("/api/pagos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.metodoPago").value("EFECTIVO"))
                .andExpect(jsonPath("$.estadoPago").value("PENDIENTE"));
    }

    @Test
    @DisplayName("GET /api/pagos/reserva/{id} - Por reserva")
    void testPorReserva() throws Exception {
        when(pagoService.listarPorReserva(1L)).thenReturn(List.of(pagoDto));

        mockMvc.perform(get("/api/pagos/reserva/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idReserva").value(1L));
    }

    @Test
    @DisplayName("GET /api/pagos/dueno/{id} - Por dueño")
    void testPorDueno() throws Exception {
        when(pagoService.listarPorDueno(1L)).thenReturn(List.of(pagoDto));

        mockMvc.perform(get("/api/pagos/dueno/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idDueno").value(1L));
    }

    @Test
    @DisplayName("GET /api/pagos/estado?estadoPago= - Por estado")
    void testPorEstado() throws Exception {
        when(pagoService.listarPorEstado(EstadoPago.PENDIENTE)).thenReturn(List.of(pagoDto));

        mockMvc.perform(get("/api/pagos/estado").param("estadoPago", "PENDIENTE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estadoPago").value("PENDIENTE"));
    }

    @Test
    @DisplayName("PATCH /api/pagos/{id}/estado - Actualizar estado")
    void testActualizarEstado() throws Exception {
        PagoResponseDto actualizado = new PagoResponseDto(
                1L, 1L, 1L, new BigDecimal("15000"),
                MetodoPago.EFECTIVO, EstadoPago.COMPLETADO, LocalDateTime.now(), "Pago prueba"
        );

        when(pagoService.actualizarEstado(eq(1L), eq(EstadoPago.COMPLETADO)))
                .thenReturn(Optional.of(actualizado));

        mockMvc.perform(patch("/api/pagos/1/estado").param("nuevoEstado", "COMPLETADO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estadoPago").value("COMPLETADO"));
    }

    @Test
    @DisplayName("DELETE /api/pagos/{id} - Eliminar pago")
    void testEliminar() throws Exception {
        doNothing().when(pagoService).eliminar(1L);

        mockMvc.perform(delete("/api/pagos/1"))
                .andExpect(status().isNoContent());

        verify(pagoService, times(1)).eliminar(1L);
    }

    @Test
    @DisplayName("GET /api/pagos/metodo?metodoPago= - Por método")
    void testPorMetodo() throws Exception {
        when(pagoService.listarPorMetodo(MetodoPago.EFECTIVO)).thenReturn(List.of(pagoDto));

        mockMvc.perform(get("/api/pagos/metodo").param("metodoPago", "EFECTIVO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].metodoPago").value("EFECTIVO"));
    }

    @Test
    @DisplayName("GET /api/pagos/monto?min=&max= - Por rango de monto")
    void testPorMonto() throws Exception {
        when(pagoService.listarPorRangoMonto(10000.0, 20000.0)).thenReturn(List.of(pagoDto));

        mockMvc.perform(get("/api/pagos/monto")
                        .param("min", "10000")
                        .param("max", "20000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].monto").value(15000));
    }

}
