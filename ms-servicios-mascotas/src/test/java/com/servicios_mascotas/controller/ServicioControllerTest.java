package com.servicios_mascotas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.servicios_mascotas.dto.SMRequest;
import com.servicios_mascotas.dto.SMResponse;
import com.servicios_mascotas.model.ServicioMascotas;
import com.servicios_mascotas.service.SServices;
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
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SController.class) // Indica que se está probando el controlador de servicio
public class ServicioControllerTest {

    @Autowired
    private MockMvc mockMvc;// Proporciona una manera de realizar peticiones HTTP en las pruebas

    @MockitoBean
    private SServices service; // Crea un mock del servicio de servicios

    private final ObjectMapper objectMapper = new ObjectMapper(); // Se usa para convertir objetos Java a JSON y viceversa

    private ServicioMascotas servicio;

    private SMResponse servicioMascotaDto;

    @BeforeEach
    void setUp(){
        /*servicio = new ServicioMascotas();
        servicio.setId(1L);
        servicio.setNombreServicio("Paseo");
        servicio.setDescripcion("Paseo de una hora");
        servicio.setPrecio(1000.0);*/

        servicioMascotaDto = new SMResponse();
        servicioMascotaDto.setId(1L);
        servicioMascotaDto.setNombreServicio("Paseo");
        servicioMascotaDto.setDescripcion("Paseo de una hora");
        servicioMascotaDto.setPrecio(1000.0);
    }
    @Test
    public void testObtenerTodosLosServicios() throws Exception{
        // Define el comportamiento del mock: cuando se llame a findAll(), devuelve una lista con un servicio
        when(service.listar()).thenReturn(List.of(servicioMascotaDto));
        // Realiza una petición GET a /api/servicios y verifica que la respuesta sea correcta
        mockMvc.perform(get("/api/servicios"))
            .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
            .andExpect(jsonPath("$[0].id").value(1L)) // Verifica que el primer elemento tenga id 1L
            .andExpect(jsonPath("$[0].nombreServicio").value("Paseo")) // Verifica que el primer elemento tenga el nombre "Paseo"
            .andExpect(jsonPath("$[0].descripcion").value("Paseo de una hora")) // Verifica que el primer elemento tenga la descripcion"Paseo de una hora"
            .andExpect(jsonPath("$[0].precio").value(1000.0)); // Verifica que el primer elemento tenga el precio de 1000.0

    }
    @Test
    public void testObtenerXID() throws  Exception{
        // Define el comportamiento del mock: cuando se llame a findById() con 1, devuelve el objeto servicio
        when(service.obtenerXId(1L)).thenReturn(Optional.ofNullable(servicioMascotaDto));
        // Realiza una petición GET a /api/servicios/1 y verifica que la respuesta sea correcta
        mockMvc.perform(get("/api/servicios/1"))
                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$.id").value(1L)) // Verifica que el id del objeto devuelto sea 1
                .andExpect(jsonPath("$.nombreServicio").value("Paseo")) // Verifica que el run del objeto devuelto sea "12345678-9"
                .andExpect(jsonPath("$.descripcion").value("Paseo de una hora")) // Verifica que el primer elemento tenga la descripcion"Paseo de una hora"
                .andExpect(jsonPath("$.precio").value(1000.0)); // Verifica que el primer elemento tenga el precio de 1000.0
    }
    @Test
    public void testCrearServicio() throws Exception {
        // Define el comportamiento del mock: cuando se llame a save(), devuelve el objeto servicio
        when(service.guardar(any(SMRequest.class))).thenReturn(servicioMascotaDto);
        // Realiza una petición POST a /api/servicios con el objeto servicio en formato JSON y verifica que la respuesta sea correcta
        mockMvc.perform(post("/api/servicios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(servicioMascotaDto))) // Convierte el objeto servicios a JSON
                .andExpect(status().isCreated()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$.id").value(1L)) // Verifica que el id del objeto devuelto sea 1
                .andExpect(jsonPath("$.nombreServicio").value("Paseo"))
                .andExpect(jsonPath("$.descripcion").value("Paseo de una hora")) // Verifica que el primer elemento tenga la descripcion"Paseo de una hora"
                .andExpect(jsonPath("$.precio").value(1000.0)); // Verifica que el primer elemento tenga el precio de 1000.0
    }

    @Test
    public void testActualizarServicio() throws Exception{
        // Define el comportamiento del mock: cuando se llame a save(), devuelve el objeto Estudiante
        when(service.actualizar(eq(1L), any(SMRequest.class)))
                .thenReturn(Optional.of(servicioMascotaDto));

        // Realiza una petición PUT a /api/servicios/1 con el objeto Estudiante en formato JSON y verifica que la respuesta sea correcta
        mockMvc.perform(put("/api/servicios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(servicioMascotaDto))) // Convierte el objeto Estudiante a JSON
                        .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                        .andExpect(jsonPath("$.id").value(1L)) // Verifica que el id del objeto devuelto sea 1
                        .andExpect(jsonPath("$.nombreServicio").value("Paseo")) // Verifica que el run del objeto devuelto sea "12345678-9"
                        .andExpect(jsonPath("$.descripcion").value("Paseo de una hora")) // Verifica que el primer elemento tenga la descripcion"Paseo de una hora"
                        .andExpect(jsonPath("$.precio").value(1000.0)); // Verifica que el primer elemento tenga el precio de 1000.0
    }
    @Test
    public void testEliminarServicio() throws Exception{
        // Define el comportamiento del mock: cuando se llame a deleteById(), no hace nada
        when(service.obtenerXId(1L))
                .thenReturn(Optional.of(servicioMascotaDto));

        // Realiza una petición DELETE a /api/servicios/1 y verifica que la respuesta sea correcta
        mockMvc.perform(delete("/api/servicios/1"))
                .andExpect(status().isNoContent()); // Verifica que el estado de la respuesta sea 200 OK
        //Verifica que el método deleteById() del servicio se haya llamado exactamente una vez con el id 1
        verify(service, times(1)).eliminar(1L);
    }


}
