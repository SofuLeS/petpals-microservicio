package com.ResenasM.MicroServicioResenas.servicio;

import c.ResenasM.MicroServicioResenas.dto.ResenaRequestDTO;
import c.ResenasM.MicroServicioResenas.dto.ResenaResponseDTO;
import c.ResenasM.MicroServicioResenas.exception.ResourceNotFoundException;
import c.ResenasM.MicroServicioResenas.model.ResenasModel;
import c.ResenasM.MicroServicioResenas.repository.ResenasRepository;
import c.ResenasM.MicroServicioResenas.service.ResenaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests del Servicio de Reseñas")
public class ResenaServiceTest {

    @Mock
    private ResenasRepository resenasRepository;

    @Mock
    private c.ResenasM.MicroServicioResenas.config.Data webClientData;

    @InjectMocks
    private ResenaService resenaService;

    private ResenasModel resena1;
    private ResenasModel resena2;

    @BeforeEach
    void setUp() {
        resena1 = new ResenasModel();
        resena1.setIdResena(1L);
        resena1.setIdReserva(101L);
        resena1.setIdDueno(1L);
        resena1.setIdCuidador(10L);
        resena1.setEstrellas(5);
        resena1.setComentarios("Excelente servicio");


        resena2 = new ResenasModel();
        resena2.setIdResena(2L);
        resena2.setIdReserva(102L);
        resena2.setIdDueno(2L);
        resena2.setIdCuidador(10L);
        resena2.setEstrellas(4);
        resena2.setComentarios("Muy puntual");
    }

    @Test
    @DisplayName("Debe guardar una reseña exitosamente cuando el dueño y el cuidador existen")
    void testGuardarResenaExitoso() {
        // GIVEN
        ResenaRequestDTO request = new ResenaRequestDTO(101L, 1L, 10L, 5, "Excelente servicio ");

        // Simulamos que el WebClient valida con éxito al dueño y al cuidador (no hacen nada / no tiran excepción)
        doNothing().when(webClientData).obtenerDuenoId(1L);
        doNothing().when(webClientData).obtenerCuidadorId(10L);

        // Simulamos el guardado en la base de datos
        when(resenasRepository.save(any(ResenasModel.class))).thenReturn(resena1);

        // WHEN
        ResenaResponseDTO resultado = resenaService.guardar(request);

        // THEN
        assertNotNull(resultado);
        assertEquals("Excelente servicio", resultado.getComentarios()); // Verifica que aplicó el .trim()
        assertEquals(5, resultado.getEstrellas());
        verify(resenasRepository, times(1)).save(any(ResenasModel.class));
    }

    @Test
    @DisplayName("Debe lanzar una excepción si el dueño no existe en el sistema remoto")
    void testGuardarResenaErrorDuenoNoExiste() {
        // GIVEN
        ResenaRequestDTO request = new ResenaRequestDTO(101L, 99L, 10L, 5, "Buen servicio");

        // Simulamos que el WebClient falla al buscar el dueño lanzando una excepción cualquiera
        doThrow(new RuntimeException("Error")).when(webClientData).obtenerDuenoId(99L);

        // WHEN & THEN
        ResourceNotFoundException excepcion = assertThrows(ResourceNotFoundException.class, () -> {
            resenaService.guardar(request);
        });

        assertTrue(excepcion.getMessage().contains("El dueño con ID 99 no existe."));
        verify(resenasRepository, never()).save(any(ResenasModel.class));
    }

    @Test
    @DisplayName("Debe listar todas las reseñas de un cuidador específico")
    void testListarPorCuidador() {
        // GIVEN
        Long idCuidadorBusqueda = 10L;
        when(resenasRepository.findByIdCuidador(idCuidadorBusqueda)).thenReturn(List.of(resena1, resena2));

        // WHEN
        List<ResenaResponseDTO> resultado = resenaService.listarPorCuidador(idCuidadorBusqueda);

        // THEN
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(1L, resultado.get(0).getIdResena());
        assertEquals(2L, resultado.get(1).getIdResena());
        verify(resenasRepository, times(1)).findByIdCuidador(idCuidadorBusqueda);
    }
}