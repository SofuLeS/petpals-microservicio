package cl.petpals.mscuidadores.service;


import cl.petpals.mscuidadores.dto.CuidadorRequestDto;
import cl.petpals.mscuidadores.dto.CuidadorResponseDto;
import cl.petpals.mscuidadores.modelo.Categoria;
import cl.petpals.mscuidadores.modelo.Cuidador;
import cl.petpals.mscuidadores.repository.CategoriaRepository;
import cl.petpals.mscuidadores.repository.CuidadorRepository;
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
@DisplayName("Tests del CuidadorService")
public class CuidadorServiceTest {

    @Mock
    private CuidadorRepository repo;

    @Mock
    private CategoriaRepository categoriaRepo;

    @InjectMocks
    private CuidadorService service;

    private Categoria categoriaPerro;
    private Cuidador cuidador1;
    private Cuidador cuidador2;

    @BeforeEach
    void setUp() {
        categoriaPerro = new Categoria(1L, "Perros", "Cuidado de caninos domésticos");

        // Orden : id, rut, nombre, apellidos, telefono, email, edad, disponibilidad
        //                        ,calificacion, anosExperincia, mascotasCuidadas, categoria
        cuidador1 = new Cuidador(
                1L,
                "12345678-9",
                "Carlos",
                "González",
                912345678,
                "carlos@gmail.com",
                28,
                true,
                4.5,
                3,
                20,
                categoriaPerro
        );

        cuidador2 = new Cuidador(
                2L,
                "98765432-1",
                "María",
                "Soto",
                987654321,
                "maria@gmail.com",
                32,
                true,
                4.8,
                7,
                45,
                categoriaPerro
        );
    }

    //Obtener por la id
    @Test
    @DisplayName("Debe retornar Cuidador por ID")
    void testObtenerPorId() {

        when(repo.findById(1L)).thenReturn(Optional.of(cuidador1));

        Optional<CuidadorResponseDto> resultado = service.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Carlos", resultado.get().getNombre());
        assertEquals("12345678-9", resultado.get().getRut());
        assertEquals("Perros", resultado.get().getNombreCategoria());
        verify(repo, times(1)).findById(1L);
    }

    //Obtener todos
    @Test
    @DisplayName("Debe retornar todos los cuidadores")
    void testObtenerTodos() {

        // GIVEN
        when(repo.findAll()).thenReturn(List.of(cuidador1, cuidador2));

        // WHEN
        List<CuidadorResponseDto> resultado = service.obtenerTodos();

        // THEN
        assertNotNull(resultado);
        assertEquals(2, resultado.size());                         // llegaron los dos
        assertEquals("Carlos", resultado.get(0).getNombre());
        assertEquals("María", resultado.get(1).getNombre());

        verify(repo, times(1)).findAll();
    }

    //Test: Guardar
    @Test
    @DisplayName("Debe guardar un cuidador correctamente")
    void testGuardar() {

        // GIVEN: construimos el DTO de entrada (lo que llega desde el cliente)
        CuidadorRequestDto request = new CuidadorRequestDto(
                "12345678-9",
                "Carlos",
                "González",
                912345678,
                "carlos@gmail.com",
                28,
                1L,       // categoriaId
                3,
                20,
                true
        );

        when(categoriaRepo.findById(1L)).thenReturn(Optional.of(categoriaPerro));

        when(repo.save(any(Cuidador.class))).thenReturn(cuidador1);

        CuidadorResponseDto resultado = service.guardar(request);

        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNombre());
        assertEquals("12345678-9", resultado.getRut());
        assertEquals("Perros", resultado.getNombreCategoria());

        verify(categoriaRepo, times(1)).findById(1L); // buscó la categoria
        verify(repo, times(1)).save(any(Cuidador.class)); // guardó el cuidador
    }

    //Test Guardar con categoria existente (deberia de tirara error)
    @Test
    @DisplayName("Debe lanzar excepción si la categoría no existe al guardar")
    void testGuardarCategoriaNoExiste() {

        CuidadorRequestDto request = new CuidadorRequestDto(
                "12345678-9", "Carlos", "González",
                912345678, "carlos@gmail.com", 28,
                99L, // id de categoria que no existe
                3, 20, true
        );

        when(categoriaRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.guardar(request));

        verify(categoriaRepo, times(1)).findById(99L);
        verify(repo, never()).save(any()); // nunca debería llegar al save
    }

    //Test Eliminar
    @Test
    @DisplayName("Debe eliminar un cuidador por ID")
    void testEliminar() {

        Long id = 1L;

        service.eliminar(id);

        verify(repo, times(1)).deleteById(id); // el repo intentó borrar exactamente 1 vez
    }

    //Test Actualizar
    @Test
    @DisplayName("Debe actualizar un cuidador existente")
    void testActualizar() {

        Long id = 1L;

        CuidadorRequestDto request = new CuidadorRequestDto(
                "11111111-1",
                "Pedro",
                "Ramírez",
                911111111,
                "pedro@gmail.com",
                30,
                1L,   // misma categoriaId
                5,
                35,
                false
        );

        Cuidador actualizado = new Cuidador(
                1L,
                "11111111-1",
                "Pedro",
                "Ramírez",
                911111111,
                "pedro@gmail.com",
                30,
                false,
                4.5,
                5,
                35,
                categoriaPerro
        );

        when(repo.findById(id)).thenReturn(Optional.of(cuidador1));       // el cuidador existe
        when(categoriaRepo.findById(1L)).thenReturn(Optional.of(categoriaPerro)); // la categoria existe
        when(repo.save(any(Cuidador.class))).thenReturn(actualizado);     // devuelve el actualizado

        Optional<CuidadorResponseDto> resultado = service.actualizar(id, request);

        assertTrue(resultado.isPresent());
        assertEquals("Pedro", resultado.get().getNombre());          // nombre cambió
        assertEquals("11111111-1", resultado.get().getRut());         // rut cambió
        assertEquals(false, resultado.get().getDisponibilidad());     // disponibilidad cambió

        verify(repo, times(1)).findById(id);
        verify(repo, times(1)).save(any(Cuidador.class));
    }

    //Listar disponibles
    @Test
    @DisplayName("Debe retornar solo los cuidadores disponibles")
    void testListarDisponibles() {

        // cuidador2 está disponible, simulamos que el repo devuelve solo ese
        when(repo.findByDisponibilidadTrue()).thenReturn(List.of(cuidador1, cuidador2));

        List<CuidadorResponseDto> resultado = service.listarDisponibles();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.get(0).getDisponibilidad()); // ambos deben ser true

        verify(repo, times(1)).findByDisponibilidadTrue();
    }
}

