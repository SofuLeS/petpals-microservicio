package cl.petpals.mscuidadores.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import cl.petpals.mscuidadores.dto.CuidadorRequestDto;
import cl.petpals.mscuidadores.dto.CuidadorResponseDto;
import cl.petpals.mscuidadores.service.CuidadorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CuidadorController.class)
public class CuidadorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CuidadorService service;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private CuidadorResponseDto cuidadorDto;

    @BeforeEach
    void setUp() {
        cuidadorDto = new CuidadorResponseDto(
                1L,
                "12345678-9",
                "Carlos",
                "González",
                912345678,
                "carlos@gmail.com",
                28,
                "Perros",
                3,
                20,
                true,
                4.5
        );
    }

    @Test
    @DisplayName("GET /api/cuidadores - Listar todos")
    void testObtenerTodos() throws Exception {
        when(service.obtenerTodos()).thenReturn(List.of(cuidadorDto));

        mockMvc.perform(get("/api/cuidadores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("Carlos"))
                .andExpect(jsonPath("$[0].rut").value("12345678-9"))
                .andExpect(jsonPath("$[0].nombreCategoria").value("Perros"));
    }

    @Test
    @DisplayName("GET /api/cuidadores/{id} - Obtener por ID")
    void testObtenerPorId() throws Exception {
        when(service.obtenerPorId(1L)).thenReturn(Optional.of(cuidadorDto));

        mockMvc.perform(get("/api/cuidadores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Carlos"))
                .andExpect(jsonPath("$.apellidos").value("González"))
                .andExpect(jsonPath("$.email").value("carlos@gmail.com"));
    }

    @Test
    @DisplayName("GET /api/cuidadores/{id} - No encontrado devuelve 404")
    void testObtenerPorIdNoEncontrado() throws Exception {
        when(service.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/cuidadores/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/cuidadores - Crear cuidador")
    void testCrear() throws Exception {
        CuidadorRequestDto request = new CuidadorRequestDto(
                "12345678-9", "Carlos", "González",
                912345678, "carlos@gmail.com", 28,
                1L, 3, 20, true
        );

        when(service.guardar(any(CuidadorRequestDto.class))).thenReturn(cuidadorDto);

        mockMvc.perform(post("/api/cuidadores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Carlos"))
                .andExpect(jsonPath("$.rut").value("12345678-9"))
                .andExpect(jsonPath("$.nombreCategoria").value("Perros"));
    }

    @Test
    @DisplayName("PUT /api/cuidadores/{id} - Actualizar cuidador")
    void testActualizar() throws Exception {
        CuidadorRequestDto request = new CuidadorRequestDto(
                "12345678-9", "Carlos", "González",
                912345678, "carlos@gmail.com", 28,
                1L, 3, 20, true
        );

        when(service.actualizar(eq(1L), any(CuidadorRequestDto.class)))
                .thenReturn(Optional.of(cuidadorDto));

        mockMvc.perform(put("/api/cuidadores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Carlos"));
    }

    @Test
    @DisplayName("DELETE /api/cuidadores/{id} - Eliminar cuidador")
    void testEliminar() throws Exception {
        when(service.obtenerPorId(1L)).thenReturn(Optional.of(cuidadorDto));

        mockMvc.perform(delete("/api/cuidadores/1"))
                .andExpect(status().isNoContent());

        verify(service, times(1)).eliminar(1L);
    }

    @Test
    @DisplayName("DELETE /api/cuidadores/{id} - No existe devuelve 404")
    void testEliminarNoExiste() throws Exception {
        when(service.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/cuidadores/99"))
                .andExpect(status().isNotFound());

        verify(service, never()).eliminar(any());
    }

    @Test
    @DisplayName("GET /api/cuidadores/buscar?nombre= - Buscar por nombre")
    void testBuscarPorNombre() throws Exception {
        when(service.buscarPorNombre("Carlos")).thenReturn(List.of(cuidadorDto));

        mockMvc.perform(get("/api/cuidadores/buscar").param("nombre", "Carlos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Carlos"));
    }

    @Test
    @DisplayName("GET /api/cuidadores/buscar-apellido?apellido= - Buscar por apellido")
    void testBuscarPorApellido() throws Exception {
        when(service.buscarPorApellido("González")).thenReturn(List.of(cuidadorDto));

        mockMvc.perform(get("/api/cuidadores/buscar-apellido").param("apellido", "González"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].apellidos").value("González"));
    }

    @Test
    @DisplayName("GET /api/cuidadores/especialidad?animal= - Buscar por especialidad")
    void testBuscarPorEspecialidad() throws Exception {
        when(service.buscarPorEspecialidad("Perros")).thenReturn(List.of(cuidadorDto));

        mockMvc.perform(get("/api/cuidadores/especialidad").param("animal", "Perros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreCategoria").value("Perros"));
    }

    @Test
    @DisplayName("GET /api/cuidadores/disponibilidad - Listar disponibles")
    void testListarDisponibles() throws Exception {
        when(service.listarDisponibles()).thenReturn(List.of(cuidadorDto));

        mockMvc.perform(get("/api/cuidadores/disponibilidad"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].disponibilidad").value(true));
    }

    @Test
    @DisplayName("GET /api/cuidadores/mascotas-cuidadas?cantidad= - Buscar por mascotas cuidadas")
    void testBuscarPorMascotasCuidadas() throws Exception {
        when(service.buscarPorMascotasCuidadas(10)).thenReturn(List.of(cuidadorDto));

        mockMvc.perform(get("/api/cuidadores/mascotas-cuidadas").param("cantidad", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].mascotasCuidadas").value(20));
    }

    @Test
    @DisplayName("GET /api/cuidadores/experiencia?anos= - Buscar por años de experiencia")
    void testBuscarPorExperiencia() throws Exception {
        when(service.buscarPorAniosExperincia(2)).thenReturn(List.of(cuidadorDto));

        mockMvc.perform(get("/api/cuidadores/experiencia").param("anos", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].anosExperincia").value(3));
    }

    @Test
    @DisplayName("GET /api/cuidadores/top-calificacion - Top por calificación")
    void testTopPorCalificacion() throws Exception {
        when(service.listarPorCalificacion()).thenReturn(List.of(cuidadorDto));

        mockMvc.perform(get("/api/cuidadores/top-calificacion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].calificacion").value(4.5));
    }

    @Test
    @DisplayName("GET /api/cuidadores/top-mascotas - Top por mascotas cuidadas")
    void testTopPorMascotas() throws Exception {
        when(service.listarPorMascotas()).thenReturn(List.of(cuidadorDto));

        mockMvc.perform(get("/api/cuidadores/top-mascotas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].mascotasCuidadas").value(20));
    }
}

