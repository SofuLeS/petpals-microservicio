package com.dueno.duenos.controller;

import com.dueno.duenos.dto.DuenoRequest;
import com.dueno.duenos.dto.DuenoResponse;
import com.dueno.duenos.model.Dueno;
import com.dueno.duenos.service.DuenoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DuenoController.class)
public class DuenosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DuenoService service;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Dueno dueno;

    private DuenoResponse duenoDto;

    @BeforeEach
    void setUp(){
        duenoDto = new DuenoResponse();
        duenoDto.setId(1L);
        duenoDto.setRut("25751348-k");
        duenoDto.setNombres("Ana Antonella");
        duenoDto.setApellidos("Merino Zuñiga");
        duenoDto.setTelefono(569486274);
        duenoDto.setCorreo("ana@email.com");
        duenoDto.setDireccion("Valparaiso , calle prat");
    }

    @Test
    public void testObtenerTodosLosDuenos() throws Exception{
        // Define el comportamiento del mock: cuando se llame a findAll(), devuelve una lista con un dueno
        when(service.mostrarTodos()).thenReturn(List.of(duenoDto));
        // Realiza una petición GET a /api/duenos y verifica que la respuesta sea correcta
        mockMvc.perform(get("/api/duenos"))
                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$[0].id").value(1L)) // Verifica que el primer elemento tenga id 1L
                .andExpect(jsonPath("$[0].rut").value("25751348-k"))
                .andExpect(jsonPath("$[0].nombres").value("Ana Antonella"))
                .andExpect(jsonPath("$[0].apellidos").value("Merino Zuñiga"))
                .andExpect(jsonPath("$[0].telefono").value(569486274))
                .andExpect(jsonPath("$[0].correo").value("ana@email.com"))
                .andExpect(jsonPath("$[0].direccion").value("Valparaiso , calle prat"));
    }

    @Test
    public void testObtenerXID() throws  Exception{

        // Define el comportamiento del mock: cuando se llame a obtenerXId con 1, devuelve el objeto dueno
        when(service.obtenerXId(1L)).thenReturn(Optional.ofNullable(duenoDto));

        // Realiza una petición GET a /api/duenos/1 y verifica que la respuesta sea correcta

        mockMvc.perform(get("/api/duenos/1"))

                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$.id").value(1L)) // Verifica que el primer elemento tenga id 1L
                .andExpect(jsonPath("$.rut").value("25751348-k")) // Verifica que el primer elemento tenga el rut 25751348-k
                .andExpect(jsonPath("$.nombres").value("Ana Antonella"))
                .andExpect(jsonPath("$.apellidos").value("Merino Zuñiga"))
                .andExpect(jsonPath("$.telefono").value(569486274))
                .andExpect(jsonPath("$.correo").value("ana@email.com"))
                .andExpect(jsonPath("$.direccion").value("Valparaiso , calle prat"));
    }

    @Test
    public void testCrearServicio() throws Exception {
        // Define el comportamiento del mock: cuando se llame a save(), devuelve el objeto Estudiante
        when(service.guardar(any(DuenoRequest.class))).thenReturn(duenoDto);
        // Realiza una petición POST a /api/servicios con el objeto servicio en formato JSON y verifica que la respuesta sea correcta
        mockMvc.perform(post("/api/duenos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duenoDto))) // Convierte el objeto servicios a JSON
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L)) // Verifica que el primer elemento tenga id 1L
                .andExpect(jsonPath("$.rut").value("25751348-k")) // Verifica que el primer elemento tenga el rut 25751348-k
                .andExpect(jsonPath("$.nombres").value("Ana Antonella"))
                .andExpect(jsonPath("$.apellidos").value("Merino Zuñiga"))
                .andExpect(jsonPath("$.telefono").value(569486274))
                .andExpect(jsonPath("$.correo").value("ana@email.com"))
                .andExpect(jsonPath("$.direccion").value("Valparaiso , calle prat"));


    }

    @Test
    public void testActualizarServicio() throws Exception{
        // Define el comportamiento del mock: cuando se llame a save(), devuelve el objeto Estudiante
        when(service.actualizar(eq(1L), any(DuenoRequest.class)))
                .thenReturn(Optional.of(duenoDto));

        // Realiza una petición PUT a /api/servicios/1 con el objeto Estudiante en formato JSON y verifica que la respuesta sea correcta
        mockMvc.perform(put("/api/duenos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duenoDto))) // Convierte el objeto Estudiante a JSON
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L)) // Verifica que el primer elemento tenga id 1L
                .andExpect(jsonPath("$.rut").value("25751348-k")) // Verifica que el primer elemento tenga el rut 25751348-k
                .andExpect(jsonPath("$.nombres").value("Ana Antonella"))
                .andExpect(jsonPath("$.apellidos").value("Merino Zuñiga"))
                .andExpect(jsonPath("$.telefono").value(569486274))
                .andExpect(jsonPath("$.correo").value("ana@email.com"))
                .andExpect(jsonPath("$.direccion").value("Valparaiso , calle prat"));
    }

    @Test
    public void testEliminarServicio() throws Exception{
        when(service.obtenerXId(1L))
                .thenReturn(Optional.of(duenoDto));

        // Realiza una petición DELETE a /api/duenos/1 y verifica que la respuesta sea correcta
        mockMvc.perform(delete("/api/duenos/1"))
                .andExpect(status().isNoContent()); // Verifica que el estado de la respuesta sea 200 OK
        //Verifica que el método deleteById() del servicio se haya llamado exactamente una vez con el id 1
        verify(service, times(1)).eliminar(1L);
    }























































}
