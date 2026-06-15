package com.servicios_mascotas.service;

import com.servicios_mascotas.dto.SMRequest;
import com.servicios_mascotas.dto.SMResponse;
import com.servicios_mascotas.model.ServicioMascotas;
import com.servicios_mascotas.repository.SRespository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests del ServiciosService")
public class ServicioServiceTest {

    @Mock
    private SRespository repo;

    @InjectMocks
    private SServices service;

    @BeforeEach
    void setUp(){

    }
    @Test
    @DisplayName("Debe retornar servicios por ID")
    void testObtenerXId(){
        //GIVEN
        ServicioMascotas sm = new ServicioMascotas(
                1L,
                "Paseo",
                "Paseo de una hora",
                1000.0
        );
        when(repo.findById(1L)).thenReturn(Optional.of(sm)); //simula comportamiento del repository

        //WHEN
        Optional<SMResponse> resultado = service.obtenerXId(1L);//Se ejecuta el método que estamos probando
        //devuelve el servicio simulado

        //THEN //aqui se verifica que el resultado sea correcto
        assertTrue(resultado.isPresent()); //Comprueba que el Optional contiene un valor es decir que se encontro el servicio
        assertEquals("Paseo", resultado.get().getNombreServicio());//Comprueba que el nombre obtenido sea exactamente paseo
        verify(repo, times(1)).findById(1L);//Comprueba que el repositorio fue llamado una sola vez.
    }

    @Test
    @DisplayName("Debe retornar todos los servicios")
    void TestbuscarTodos(){
        //GIVEN
        ServicioMascotas s1 = new ServicioMascotas(
                1L,
                "Paseo",
                "Paseo de una hora",
                1000.0
        );

        //GIVEN
        ServicioMascotas s2 = new ServicioMascotas(
                2L,
                "Ducha",
                "Ducha express a mascota",
                1000.0
        );
        when(repo.findAll()).thenReturn(List.of(s1, s2));//Simula que la base de datos tiene dos servicios.
        //WHEN
        List<SMResponse> resultado = service.listar();//Ejecuta el método del servicio
        //THEN
        assertNotNull(resultado);
        assertEquals(2, resultado.size());//Verifica que se devolvieron dos registros.
        assertEquals("Paseo", resultado.get(0).getNombreServicio());
        assertEquals("Ducha", resultado.get(1).getNombreServicio());
        verify(repo, times(1)).findAll();//Verifica que se llamó al método findAll()
    }

    @Test
    @DisplayName("Debe guardar un servicio")
    void TestGuardarServicio(){
        // GIVEN
        SMRequest request = new SMRequest( //creacion del dto
                "Paseo",
                "Paseo de una hora",
                1000.0
        );
        ServicioMascotas servicioGuardado = new ServicioMascotas(
                1L,
                "Paseo",
                "Paseo de una hora",
                1000.0
        );
        when(repo.save(any(ServicioMascotas.class))).thenReturn(servicioGuardado);//Simula que el repositorio guardó un servicio
        // WHEN
        SMResponse resultado = service.guardar(request);//Ejecuta el método guardar
        //THEN
        assertNotNull(resultado);//Comprueba que se devolvió un resultado
        assertEquals("Paseo", resultado.getNombreServicio());//Comprueba que el dueño guardado tiene el nombre correcto
        assertEquals(1000.0, resultado.getPrecio());
    }
    @Test
    @DisplayName("Debe eliminar un servicio por ID")
    void TestEliminarservicio(){
        // GIVEN
        Long id = 1L;//se pone ya que el metodo recibe por parametros el id tipo Long
        // WHEN
        service.eliminar(id);//Ejecuta el método eliminar
        // THEN
        verify(repo, times(1)).deleteById(id);//Comprueba que el repositorio intentó eliminar el servicio una vez.
    }
    @Test
    @DisplayName("Debe actualizar un servicio existente")
    void TestActualizarservicio(){
        // GIVEN
        Long id = 1L;//se pone ya que el metodo recibe por parametros el id tipo Long
        ServicioMascotas existente = new ServicioMascotas(
                1L,
                "Ducha",
                "Ducha express a mascota",
                1000.0
        );
        SMRequest request = new SMRequest(
                "Paseo",
                "Paseo de una hora",
                1000.0
        );
        ServicioMascotas actualizado = new ServicioMascotas(
                1L,
                "Paseo",
                "Paseo de media hora",
                5000.0
        );
        when(repo.findById(id)).thenReturn(Optional.of(existente));//Simula que el servicio existe

        when(repo.save(any(ServicioMascotas.class))).thenReturn(actualizado);//Simula el guardado de los cambios.
        //WHEN
        Optional<SMResponse> resultado = service.actualizar(id, request);//Ejecuta la actualización
        // THEN
        assertTrue(resultado.isPresent());
        assertEquals("Paseo", resultado.get().getNombreServicio());//Comprueba que el nombre cambió correctamente
        assertEquals(5000.0, resultado.get().getPrecio());

        verify(repo, times(1)).findById(id);//Comprueba que el método fue ejecutado exactamente una vez
        verify(repo, times(1)).save(any(ServicioMascotas.class));


    }


}
