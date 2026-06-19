package com.ResenasM.MicroServicioResenas.controller;

import c.ResenasM.MicroServicioResenas.controller.ResenasController;
import org.springframework.beans.factory.annotation.Autowired;
import c.ResenasM.MicroServicioResenas.dto.ResenaRequestDTO;
import c.ResenasM.MicroServicioResenas.dto.ResenaResponseDTO;
import c.ResenasM.MicroServicioResenas.service.IResenaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ResenasController.class)
public class ResenasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IResenaService service;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ResenaResponseDTO resenaDto;

    @BeforeEach
    void setUp() {
        // Inicializamos el DTO de respuesta emulando una fila de la base de datos
        resenaDto = new ResenaResponseDTO();
        resenaDto.setIdResena(1L);
        resenaDto.setIdReserva(12L);
        resenaDto.setIdDueno(2L);
        resenaDto.setIdCuidador(3L);
        resenaDto.setEstrellas(5);
        resenaDto.setComentarios("Excelente cuidador, muy atento");

    }

    @Test
    @DisplayName("GET /api/resenas - Listar todas las reseñas")
    void testObtenerTodas() throws Exception {
        // Configura el comportamiento esperado basado en tu método real obtenerTodasLasResenas()
        when(service.obtenerTodasLasResenas()).thenReturn(List.of(resenaDto));

        mockMvc.perform(get("/api/resenas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].idResena").value(1L))
                .andExpect(jsonPath("[0].idReserva").value(12L))
                .andExpect(jsonPath("[0].estrellas").value(5))
                .andExpect(jsonPath("[0].comentarios").value("Excelente cuidador, muy atento"));

        verify(service, times(1)).obtenerTodasLasResenas();
    }

    @Test
    @DisplayName("POST /api/resenas - Crear una reseña de forma exitosa")
    void testCrearResena() throws Exception {
        // DTO de entrada (Request) limpio tal cual viaja desde la interfaz o Postman
        ResenaRequestDTO request = new ResenaRequestDTO(12L, 2L, 3L, 5, "Excelente cuidador, muy atento");

        when(service.guardar(any(ResenaRequestDTO.class))).thenReturn(resenaDto);

        mockMvc.perform(post("/api/resenas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated()) // Espera HTTP 201
                .andExpect(jsonPath(".idResena").value(1L))
                .andExpect(jsonPath(".comentarios").value("Excelente cuidador, muy atento"))
                .andExpect(jsonPath(".estrellas").value(5));

        verify(service, times(1)).guardar(any(ResenaRequestDTO.class));
    }

    @Test
    @DisplayName("GET /api/resenas/cuidador/{id} - Listar reseñas específicas de un cuidador")
    void testListarPorCuidador() throws Exception {
        // Configura el comportamiento basado en tu método real listarPorCuidador(Long)
        when(service.listarPorCuidador(3L)).thenReturn(List.of(resenaDto));

        mockMvc.perform(get("/api/resenas/cuidador/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].idCuidador").value(3L))
                .andExpect(jsonPath("[0].estrellas").value(5))
                .andExpect(jsonPath("[0].idResena").value(1L));

        verify(service, times(1)).listarPorCuidador(3L);
    }
}