package cl.petpals.ms_reservas.controller;

import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import cl.petpals.ms_reservas.dto.ReservaRequestDto;
import cl.petpals.ms_reservas.dto.ReservaResponseDto;
import cl.petpals.ms_reservas.model.EstadoReserva;
import cl.petpals.ms_reservas.service.ReservaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(ReservaController.class)
@DisplayName("Test Reserva Controller")
public class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservaService reservaService;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private ReservaResponseDto reservaDto;

    @BeforeEach
    void setUp() {
        reservaDto = new ReservaResponseDto(
                1L, 1L, 1L, 1L, 1L,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(3),
                EstadoReserva.PENDIENTE
        );
    }

    @Test
    @DisplayName("GET /api/reservas - Listar todas")
    void testObtenerTodas() throws Exception {
        when(reservaService.obtenerTodas()).thenReturn(List.of(reservaDto));

        mockMvc.perform(get("/api/reservas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].estadoReserva").value("PENDIENTE"));
    }

    @Test
    @DisplayName("GET /api/reservas/{id} - Obtener por ID")
    void testObtenerPorId() throws Exception {
        when(reservaService.obtenerPorId(1L)).thenReturn(Optional.of(reservaDto));

        mockMvc.perform(get("/api/reservas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.idDueno").value(1L));
    }

    @Test
    @DisplayName("GET /api/reservas/{id} - No encontrado devuelve 404")
    void testObtenerPorIdNoEncontrado() throws Exception {
        when(reservaService.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/reservas/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/reservas - Crear reserva")
    void testCrear() throws Exception {
        ReservaRequestDto request = new ReservaRequestDto(
                1L, 1L, 1L, 1L,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(3)
        );

        when(reservaService.guardar(any(ReservaRequestDto.class))).thenReturn(reservaDto);

        mockMvc.perform(post("/api/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estadoReserva").value("PENDIENTE"));
    }

    @Test
    @DisplayName("GET /api/reservas/cuidador/{id} - Por cuidador")
    void testPorCuidador() throws Exception {
        when(reservaService.listarPorCuidador(1L)).thenReturn(List.of(reservaDto));

        mockMvc.perform(get("/api/reservas/cuidador/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idCuidador").value(1L));
    }

    @Test
    @DisplayName("GET /api/reservas/dueno/{id} - Por dueño")
    void testPorDueno() throws Exception {
        when(reservaService.listarPorDueno(1L)).thenReturn(List.of(reservaDto));

        mockMvc.perform(get("/api/reservas/dueno/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idDueno").value(1L));
    }

    @Test
    @DisplayName("GET /api/reservas/estado?estado= - Por estado")
    void testPorEstado() throws Exception {
        when(reservaService.listarPorEstado(EstadoReserva.PENDIENTE)).thenReturn(List.of(reservaDto));

        mockMvc.perform(get("/api/reservas/estado").param("estado", "PENDIENTE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estadoReserva").value("PENDIENTE"));
    }

    @Test
    @DisplayName("GET /api/reservas/mascota/{id} - Por mascota")
    void testPorMascota() throws Exception {
        when(reservaService.listarPorMascota(1L)).thenReturn(List.of(reservaDto));

        mockMvc.perform(get("/api/reservas/mascota/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idMascota").value(1L));
    }

    @Test
    @DisplayName("GET /api/reservas/fecha?desde=&hasta= - Por rango de fecha")
    void testPorFecha() throws Exception {
        when(reservaService.listarPorFecha(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(List.of(reservaDto));

        mockMvc.perform(get("/api/reservas/fecha")
                        .param("desde", "2026-06-01")
                        .param("hasta", "2026-06-30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @DisplayName("GET /api/reservas/cuidador/{id}/estado?estado= - Por cuidador y estado")
    void testPorCuidadorYEstado() throws Exception {
        when(reservaService.listarPorCuidadorYEstado(1L, EstadoReserva.PENDIENTE))
                .thenReturn(List.of(reservaDto));

        mockMvc.perform(get("/api/reservas/cuidador/1/estado").param("estado", "PENDIENTE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estadoReserva").value("PENDIENTE"));
    }

    @Test
    @DisplayName("PATCH /api/reservas/{id}/estado - Actualizar estado")
    void testActualizarEstado() throws Exception {
        ReservaResponseDto actualizado = new ReservaResponseDto(
                1L, 1L, 1L, 1L, 1L,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(3),
                EstadoReserva.ACEPTADA
        );

        when(reservaService.actualizarEstado(eq(1L), eq(EstadoReserva.ACEPTADA)))
                .thenReturn(Optional.of(actualizado));

        mockMvc.perform(patch("/api/reservas/1/estado").param("nuevoEstado", "ACEPTADA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estadoReserva").value("ACEPTADA"));
    }

    @Test
    @DisplayName("DELETE /api/reservas/{id} - Eliminar reserva")
    void testEliminar() throws Exception {
        doNothing().when(reservaService).eliminar(1L);

        mockMvc.perform(delete("/api/reservas/1"))
                .andExpect(status().isNoContent());

        verify(reservaService, times(1)).eliminar(1L);
    }

}
