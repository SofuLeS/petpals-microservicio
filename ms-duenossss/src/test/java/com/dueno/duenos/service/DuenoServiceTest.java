package com.dueno.duenos.service;
import com.dueno.duenos.dto.DuenoRequest;
import com.dueno.duenos.dto.DuenoResponse;
import com.dueno.duenos.model.Dueno;
import com.dueno.duenos.repository.DuenoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests del DuenoService")
public class DuenoServiceTest {

    @Mock
    private DuenoRepository repo;

    @InjectMocks
    private DuenoService service;

    @BeforeEach
    void setUp(){

    }

    @Test //Sin esta anotación, JUnit no ejecutará el método
    @DisplayName("Debe retornar dueno por ID")
    void testObtenerXId(){

        //GIVEN
        Dueno dueno = new Dueno( //se crea dueño de prueba, el cual no esta en la base de datos solo
                                //esta aqui y solo se usara aqui
                1L,
                "12345678-9",
                "Juan",
                "Perez",
                987654321,
                "juan@gmail.com",
                "Santiago"
        );
        when(repo.findById(1L)).thenReturn(Optional.of(dueno)); //simula comportamiento del repository

        //WHEN
        Optional<DuenoResponse> resultado = service.obtenerXId(1L);//Se ejecuta el método que estamos probando
                                                                    //devuelve el dueño simulado

        //THEN //aqui se verifica que el resultado sea correcto
        assertTrue(resultado.isPresent()); //Comprueba que el Optional contiene un valor es decir que se encontro el dueño
        assertEquals("Juan", resultado.get().getNombres());//Comprueba que el nombre obtenido sea exactamente juan
        verify(repo, times(1)).findById(1L);//Comprueba que el repositorio fue llamado una sola vez.
    }

    @Test
    @DisplayName("Debe retornar todos los dueños")
    void TestbuscarTodos() {

        //GIVEN
        Dueno dueno1 = new Dueno(
                1L,
                "12345678-9",
                "Juan",
                "Perez",
                987654321,
                "juan@gmail.com",
                "Santiago"
        );

        Dueno dueno2 = new Dueno(
                2L,
                "98765432-1",
                "Maria",
                "Gonzalez",
                912345678,
                "maria@gmail.com",
                "Valparaiso"
        );

        when(repo.findAll()).thenReturn(List.of(dueno1, dueno2));//Simula que la base de datos tiene dos dueños.

        //WHEN
        List<DuenoResponse> resultado = service.mostrarTodos();//Ejecuta el método del servicio

        //THEN
        assertNotNull(resultado);
        assertEquals(2, resultado.size());//Verifica que se devolvieron dos registros.
        assertEquals("Juan", resultado.get(0).getNombres());
        assertEquals("Maria", resultado.get(1).getNombres());

        verify(repo, times(1)).findAll();//Verifica que se llamó al método findAll()
    }
    @Test
    @DisplayName("Debe guardar un dueño")
    void TestGuardarDueno(){
        // GIVEN
        DuenoRequest request = new DuenoRequest( //creacion del dto
                "12345678-9",
                "Juan",
                "Perez",
                987654321,
                "juan@gmail.com",
                "Santiago"
        );

        Dueno duenoGuardado = new Dueno(
                1L,
                "12345678-9",
                "Juan",
                "Perez",
                987654321,
                "juan@gmail.com",
                "Santiago"
        );
        when(repo.save(any(Dueno.class))).thenReturn(duenoGuardado);//Simula que el repositorio guardó un dueño
        // WHEN
        DuenoResponse resultado = service.guardar(request);//Ejecuta el método guardar

        //THEN
        assertNotNull(resultado);//Comprueba que se devolvió un resultado
        assertEquals("Juan", resultado.getNombres());//Comprueba que el dueño guardado tiene el nombre correcto
        assertEquals("12345678-9", resultado.getRut());

        verify(repo, times(1)).save(any(Dueno.class));//Verifica que se llamó al método save.
    }
    @Test
    @DisplayName("Debe eliminar un dueño por ID")
    void TestEliminarDueno(){

        // GIVEN
        Long id = 1L;//se pone ya que el metodo recibe por parametros el id tipo Long

        // WHEN
        service.eliminar(id);//Ejecuta el método eliminar

        // THEN
        verify(repo, times(1)).deleteById(id);//Comprueba que el repositorio intentó eliminar el dueño una vez.
    }
    @Test
    @DisplayName("Debe actualizar un dueño existente")
    void TestActualizarDueno(){
        // GIVEN
        Long id = 1L;//se pone ya que el metodo recibe por parametros el id tipo Long

        Dueno existente = new Dueno(
                1L,
                "12345678-9",
                "Juan",
                "Perez",
                987654321,
                "juan@gmail.com",
                "Santiago"
        );

        DuenoRequest request = new DuenoRequest(
                "11111111-1",
                "Pedro",
                "Gonzalez",
                912345678,
                "pedro@gmail.com",
                "Valparaiso"
        );

        Dueno actualizado = new Dueno(
                1L,
                "11111111-1",
                "Pedro",
                "Gonzalez",
                912345678,
                "pedro@gmail.com",
                "Valparaiso"
        );
        when(repo.findById(id)).thenReturn(Optional.of(existente));//Simula que el dueño existe

        when(repo.save(any(Dueno.class))).thenReturn(actualizado);//Simula el guardado de los cambios.

        // WHEN
        Optional<DuenoResponse> resultado = service.actualizar(id, request);//Ejecuta la actualización
        // THEN
        assertTrue(resultado.isPresent());
        assertEquals("Pedro", resultado.get().getNombres());//Comprueba que el nombre cambió correctamente
        assertEquals("Valparaiso", resultado.get().getDireccion());

        verify(repo, times(1)).findById(id);//Comprueba que el método fue ejecutado exactamente una vez
        verify(repo, times(1)).save(any(Dueno.class));

    }



}
