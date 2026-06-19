package com.MUbicacion.MicroServicioUbicacion.controller;

import com.MUbicacion.MicroServicioUbicacion.dto.RequestDTO;
import com.MUbicacion.MicroServicioUbicacion.dto.ResponseDTO;
import com.MUbicacion.MicroServicioUbicacion.service.ServiceUbicacion;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(ControllerUbicacion.class)
public class UbicacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ServiceUbicacion serviceUbicacion;

    @Autowired
    private ObjectMapper objectMapper;

    private RequestDTO ubicaRequest;
    private ResponseDTO ubicaResponse;

    @BeforeEach
    void setUp() {
        ubicaRequest = new RequestDTO();
        ubicaRequest.setIdCuidador(5L);
        ubicaRequest.setComuna("Santiago");
        ubicaRequest.setCiudad("Santiago");
        ubicaRequest.setRegion("Metropolitana");

        ubicaResponse = new ResponseDTO();
        ubicaResponse.setId(1L);
        ubicaResponse.setIdCuidador(5L);
        ubicaResponse.setComuna("Santiago");
        ubicaResponse.setCiudad("Santiago");
        ubicaResponse.setRegion("Metropolitana");
    }

    @Test
    void testCrearUbicacion() throws Exception {
        Mockito.when(serviceUbicacion.crearUbicacion(Mockito.any(RequestDTO.class))).thenReturn(ubicaResponse);

        mockMvc.perform(post("/api/ubicaciones") // <- Revisa la ruta exacta en tu Controller
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ubicaRequest)))
                .andExpect(status().isOk()) // Cámbialo a .isCreated() si tu controlador devuelve un 201 Created
                .andExpect(jsonPath("$.comuna", is("Santiago")))
                .andExpect(jsonPath("$.idCuidador", is(5)));
    }

    @Test
    void testObtenerTodas() throws Exception {
        List<ResponseDTO> lista = Arrays.asList(ubicaResponse);
        Mockito.when(serviceUbicacion.obtenerTodas()).thenReturn(lista);

        mockMvc.perform(get("/api/ubicaciones")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].comuna", is("Santiago")));
    }

    @Test
    void testBuscarPorComuna() throws Exception {
        List<ResponseDTO> lista = Arrays.asList(ubicaResponse);
        Mockito.when(serviceUbicacion.buscarPorComuna("Santiago")).thenReturn(lista);

        mockMvc.perform(get("/api/ubicaciones/comuna/Santiago") // <- Revisa cómo mapeaste esta ruta en tu controlador
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].comuna", is("Santiago")));
    }
}