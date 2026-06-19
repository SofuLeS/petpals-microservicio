package cl.mastocas.petPal.controller;

import cl.mastocas.petPal.mascotaController.MascotaController;
import cl.mastocas.petPal.mascotasDTO.MascotaRequestDTO;
import cl.mastocas.petPal.mascotasDTO.MascotaResponseDTO;
import cl.mastocas.petPal.mascotasService.MascotaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(MascotaController.class)
public class MascotasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MascotaService mascotaService;

    @Autowired
    private ObjectMapper objectMapper;

    private MascotaRequestDTO petRequest;
    private MascotaResponseDTO petResponse;

    @BeforeEach
    void setUp() {
        petRequest = new MascotaRequestDTO();
        petRequest.setNombre("Firulais");
        petRequest.setRaza("Poodle");
        petRequest.setEdad(3);
        petRequest.setAlergias("Ninguna");
        petRequest.setTipoMascota("Perro");
        petRequest.setIdDueno(10L);

        petResponse = new MascotaResponseDTO(
                1L,
                "Firulais",
                "Poodle",
                3,
                "Ninguna",
                "Perro",
                10L,
                " Proceso realizado con exito! Mascota Feliz :)."
        );
    }

    @Test
    void testGuardar() throws Exception {
        Mockito.when(mascotaService.guardar(Mockito.any(MascotaRequestDTO.class))).thenReturn(petResponse);

        mockMvc.perform(post("/api/mascotas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(petRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre", is("Firulais")));
    }

    @Test
    void testObtenerTodas() throws Exception {
        List<MascotaResponseDTO> lista = Arrays.asList(petResponse);
        Mockito.when(mascotaService.obtenerTodas()).thenReturn(lista);

        mockMvc.perform(get("/api/mascotas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombre", is("Firulais")));
    }

    @Test
    void testObtenerPorId() throws Exception {
        Mockito.when(mascotaService.obtenerPorId(1L)).thenReturn(Optional.of(petResponse));

        mockMvc.perform(get("/api/mascotas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Firulais")));
    }
}