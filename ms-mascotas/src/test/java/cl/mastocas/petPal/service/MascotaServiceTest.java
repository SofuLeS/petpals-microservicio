
package cl.mastocas.petPal.service;

import cl.mastocas.petPal.mascotasDTO.MascotaRequestDTO;
import cl.mastocas.petPal.mascotasDTO.MascotaResponseDTO;
import cl.mastocas.petPal.mascotasModel.MascotaModel;
import cl.mastocas.petPal.mascotasRepository.MascotasRepository;
import cl.mastocas.petPal.mascotasService.MascotaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

    @ExtendWith(MockitoExtension.class)
    @DisplayName("Tests Unitarios - MascotaService")
    public class MascotaServiceTest {

        @Mock
        private MascotasRepository mascotaRepository;

        @InjectMocks
        private MascotaService mascotaService;

        private MascotaModel mascotaSimulada;

        @BeforeEach
        void setUp() {
            // Inicializamos un modelo de prueba real basado en tus campos exactos de BBDD
            mascotaSimulada = new MascotaModel();
            mascotaSimulada.setId_mascota(1L);
            mascotaSimulada.setNombre("Toby");
            mascotaSimulada.setRaza("Golden");
            mascotaSimulada.setEdad(3);
            mascotaSimulada.setAlergias("Alergia al gluten");
            mascotaSimulada.setTipoMascota("Perro");
            mascotaSimulada.setIdDueno(10L);
        }

        @Test
        @DisplayName("GIVEN un DTO con alergias, WHEN se guarda, THEN el repositorio lo registra y retorna Mascota Feliz")
        void testGuardarMascotaExitoso() {
            // GIVEN
            MascotaRequestDTO request = new MascotaRequestDTO("Toby", "Perro", "Golden", 3, "Alergia al gluten", 10L);
            when(mascotaRepository.save(any(MascotaModel.class))).thenReturn(mascotaSimulada);

            // WHEN
            MascotaResponseDTO resultado = mascotaService.guardar(request);

            // THEN
            assertNotNull(resultado);
            assertEquals(1L, resultado.getId_mascota());
            assertEquals("Toby", resultado.getNombre());
            assertEquals("Alergia al gluten", resultado.getAlergias());
            assertTrue(resultado.getMensaje().contains("Mascota Feliz"));
            verify(mascotaRepository, times(1)).save(any(MascotaModel.class));
        }

        @Test
        @DisplayName("GIVEN un ID existente, WHEN se busca la mascota, THEN retorna los datos del DTO")
        void testObtenerPorIdExitoso() {
            // GIVEN
            Long idBusqueda = 1L;
            when(mascotaRepository.findById(idBusqueda)).thenReturn(Optional.of(mascotaSimulada));

            // WHEN
            Optional<MascotaResponseDTO> resultado = mascotaService.obtenerPorId(idBusqueda);

            // THEN
            assertTrue(resultado.isPresent());
            assertEquals("Toby", resultado.get().getNombre());
            assertEquals("Alergia al gluten", resultado.get().getAlergias());
            verify(mascotaRepository, times(1)).findById(idBusqueda);
        }

        @Test
        @DisplayName("GIVEN un ID de dueño, WHEN se buscan sus mascotas, THEN retorna la lista filtrada")
        void testBuscarPorDuenoExitoso() {
            // GIVEN
            Long idDuenoBusqueda = 10L;
            when(mascotaRepository.findByIdDueno(idDuenoBusqueda)).thenReturn(List.of(mascotaSimulada));

            // WHEN
            List<MascotaResponseDTO> resultado = mascotaService.buscarPorDueno(idDuenoBusqueda);

            // THEN
            assertNotNull(resultado);
            assertEquals(1, resultado.size());
            assertEquals(idDuenoBusqueda, resultado.get(0).getIdDueno());
            verify(mascotaRepository, times(1)).findByIdDueno(idDuenoBusqueda);
        }

        @Test
        @DisplayName("GIVEN un ID inexistente, WHEN se intenta eliminar, THEN lanza RuntimeException")
        void testEliminarMascotaError() {
            // GIVEN
            Long idInexistente = 99L;
            when(mascotaRepository.existsById(idInexistente)).thenReturn(false);

            // WHEN & THEN
            RuntimeException excepcion = assertThrows(RuntimeException.class, () -> {
                mascotaService.eliminar(idInexistente);
            });

            assertTrue(excepcion.getMessage().contains("No se puede eliminar"));
            verify(mascotaRepository, never()).deleteById(anyLong());
        }

}
