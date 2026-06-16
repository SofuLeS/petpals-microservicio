package com.Calendario.MicroservicioCalendario.service;

import com.Calendario.MicroservicioCalendario.dtos.RequestCalendarioDTO;
import com.Calendario.MicroservicioCalendario.dtos.ResponseCalendarioDTO;
import com.Calendario.MicroservicioCalendario.model.ModelCalendario;
import com.Calendario.MicroservicioCalendario.repository.RepositoryCalendario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para ServiceCalendario")
public class ServiceCalendarioTest {
    //laboratorios de pruebas posibles escenarios
    @Mock
    private RepositoryCalendario repositoryCalendario;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    @SuppressWarnings("rawtypes")
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    @SuppressWarnings("rawtypes")
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private ServiceCalendario serviceCalendario;

    private ModelCalendario agendaMock;
    private Map<String, String> cuidadorJsonMock;
    //Creamos datos falsos para cada validar que cada dato funcione (objetos d pruebas)
    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() {
        agendaMock = new ModelCalendario();
        agendaMock.setId(1L);
        agendaMock.setIdCuidador(5L);
        agendaMock.setFecha(LocalDate.of(2026, 6, 20));
        agendaMock.setHoraInicio(LocalTime.of(9, 0));
        agendaMock.setHoraFin(LocalTime.of(18, 0));

        cuidadorJsonMock = new HashMap<>();
        cuidadorJsonMock.put("nombre", "Juan");
        cuidadorJsonMock.put("apellido", "Pérez");
        cuidadorJsonMock.put("telefono", "+56912345678");
    }

    @SuppressWarnings("unchecked")
    private void mockWebClientChain(boolean exitoso) {
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        if (exitoso) {
            when(responseSpec.bodyToMono(Map.class)).thenReturn(Mono.just(cuidadorJsonMock));
        } else {
            when(responseSpec.bodyToMono(Map.class)).thenReturn(Mono.error(new RuntimeException("Error")));
        }
    }

    @Test
    @DisplayName("GIVEN datos válidos, WHEN se agenda, THEN guarda y combina los datos del cuidador")
    void testGuardarCalendarioExitoso() {
        // GIVEN
        RequestCalendarioDTO request = new RequestCalendarioDTO(5L, LocalDate.of(2026, 6, 20), LocalTime.of(9, 0), LocalTime.of(18, 0));
        mockWebClientChain(true);
        when(repositoryCalendario.save(any(ModelCalendario.class))).thenReturn(agendaMock);

        // WHEN
        ResponseCalendarioDTO resultado = serviceCalendario.guardarCalendario(request);

        // THEN
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdCalendario());
        assertEquals("Juan Pérez", resultado.getNombreCuidador());
        assertEquals("+56912345678", resultado.getTelefonoCuidador());
        verify(repositoryCalendario, times(1)).save(any(ModelCalendario.class));
    }

    @Test
    @DisplayName("GIVEN una fecha, WHEN se consulta, THEN retorna la lista de turnos válidos")
    void testBuscarPorFecha() {
        // GIVEN
        LocalDate fechaBusqueda = LocalDate.of(2026, 6, 20);
        mockWebClientChain(true);
        when(repositoryCalendario.findByFecha(fechaBusqueda)).thenReturn(List.of(agendaMock));

        // WHEN
        List<ResponseCalendarioDTO> resultado = serviceCalendario.buscarPorFecha(fechaBusqueda);

        // THEN
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombreCuidador());
        verify(repositoryCalendario, times(1)).findByFecha(fechaBusqueda);
    }
}