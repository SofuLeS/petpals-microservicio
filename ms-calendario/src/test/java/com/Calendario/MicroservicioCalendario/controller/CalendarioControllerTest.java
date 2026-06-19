package com.Calendario.MicroservicioCalendario.controller;

import com.Calendario.MicroservicioCalendario.dtos.RequestCalendarioDTO;
import com.Calendario.MicroservicioCalendario.dtos.ResponseCalendarioDTO;
import com.Calendario.MicroservicioCalendario.service.ServiceCalendario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(ControllerCalendario.class)
public class CalendarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ServiceCalendario serviceCalendario;

    @Autowired
    private ObjectMapper objectMapper;

    private RequestCalendarioDTO calRequest;
    private ResponseCalendarioDTO calResponse;

    @BeforeEach
    void setUp() {
        calRequest = new RequestCalendarioDTO();
        calRequest.setIdCuidador(1L);
        calRequest.setFecha(LocalDate.of(2026, 6, 20));
        calRequest.setHoraInicio(LocalTime.parse("09:00"));
        calRequest.setHoraFin(LocalTime.parse("18:00"));

        calResponse = new ResponseCalendarioDTO();
        calResponse.setIdCalendario(100L);
        calResponse.setIdCuidador(1L);
        calResponse.setFecha(LocalDate.of(2026, 6, 20));
        calResponse.setHoraInicio(LocalTime.parse("09:00"));
        calResponse.setHoraFin(LocalTime.parse("18:00"));
        calResponse.setNombreCuidador("Juan Perez");
        calResponse.setTelefonoCuidador("+56912345678");
    }

    @Test
    void testGuardarCalendario() throws Exception {
        Mockito.when(serviceCalendario.guardarCalendario(Mockito.any(RequestCalendarioDTO.class))).thenReturn(calResponse);

        mockMvc.perform(post("/api/calendario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(calRequest)))
                .andExpect(status().isOk()) // Pon .isCreated() si devuelve un HTTP 201
                .andExpect(jsonPath("nombreCuidador", is("Juan Perez")))
                .andExpect(jsonPath("idCalendario", is(100)));
    }

    @Test
    void testBuscarPorFecha() throws Exception {
        List<ResponseCalendarioDTO> lista = Arrays.asList(calResponse);
        Mockito.when(serviceCalendario.buscarPorFecha(LocalDate.of(2026, 6, 20))).thenReturn(lista);

        mockMvc.perform(get("/api/calendario/fecha/2026-06-20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".", hasSize(1)))
                .andExpect(jsonPath(" NombreCuidador", is("Juan Perez")));
    }

    @Test
    void testObtenerTodos() throws Exception {
        List<ResponseCalendarioDTO> lista = Arrays.asList(calResponse);
        Mockito.when(serviceCalendario.obtenerTodos()).thenReturn(lista);

        mockMvc.perform(get("/api/calendario")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".", hasSize(1)))
                .andExpect(jsonPath("nombre Cuidador", is("Juan Perez")));
    }
}