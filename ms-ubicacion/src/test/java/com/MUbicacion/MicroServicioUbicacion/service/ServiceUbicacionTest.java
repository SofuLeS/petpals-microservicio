package com.MUbicacion.MicroServicioUbicacion.service;

import com.MUbicacion.MicroServicioUbicacion.dto.RequestDTO;
import com.MUbicacion.MicroServicioUbicacion.dto.ResponseDTO;
import com.MUbicacion.MicroServicioUbicacion.model.ModelUbicacion;
import com.MUbicacion.MicroServicioUbicacion.repository.RepositoryUbicacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para ServiceUbicacion")
public class ServiceUbicacionTest {

    @Mock
    private RepositoryUbicacion repositoryUbicacion;

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
    private ServiceUbicacion serviceUbicacion;

    private ModelUbicacion ubicacionMock;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() {
        ubicacionMock = new ModelUbicacion();
        ubicacionMock.setId(1L);
        ubicacionMock.setIdCuidador(5L);
        ubicacionMock.setComuna("Viña del Mar");
        ubicacionMock.setCiudad("Valparaíso");
        ubicacionMock.setRegion("Valparaíso");
    }

    /**
     * Helper para mockear el comportamiento encadenado de WebClient.build().get().uri().retrieve().bodyToMono()
     */
    @SuppressWarnings("unchecked")
    private void mockWebClientChain(boolean debeFuncionar) {
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        if (debeFuncionar) {
            when(responseSpec.bodyToMono(Object.class)).thenReturn(Mono.just(new Object()));
        } else {
            when(responseSpec.bodyToMono(Object.class)).thenReturn(Mono.error(new RuntimeException("Error de conexión")));
        }
    }

    @Test
    @DisplayName("GIVEN un cuidador válido, WHEN se crea la ubicación, THEN guarda y retorna el DTO")
    void testCrearUbicacionExitosa() {
        // GIVEN
        RequestDTO request = new RequestDTO(5L, "Viña del Mar", "Valparaíso", "Valparaíso");
        mockWebClientChain(true);
        when(repositoryUbicacion.save(any(ModelUbicacion.class))).thenReturn(ubicacionMock);

        // WHEN
        ResponseDTO resultado = serviceUbicacion.crearUbicacion(request);

        // THEN
        assertNotNull(resultado);
        assertEquals("Viña del Mar", resultado.getComuna()); // Nota: usa el getter correspondiente de tu ResponseDTO
        assertEquals(5L, resultado.getIdCuidador());
        verify(repositoryUbicacion, times(1)).save(any(ModelUbicacion.class));
    }

    @Test
    @DisplayName("GIVEN un cuidador inexistente, WHEN se intenta crear, THEN lanza ResponseStatusException 404")
    void testCrearUbicacionErronea() {
        // GIVEN
        RequestDTO request = new RequestDTO(99L, "Viña del Mar", "Valparaíso", "Valparaíso");
        mockWebClientChain(false);

        // WHEN & THEN
        ResponseStatusException excepcion = assertThrows(ResponseStatusException.class, () -> {
            serviceUbicacion.crearUbicacion(request);
        });

        assertEquals(404, excepcion.getStatusCode().value());
        assertTrue(excepcion.getReason().contains("El Cuidador con ID 99 no existe."));
        verify(repositoryUbicacion, never()).save(any(ModelUbicacion.class));
    }

    @Test
    @DisplayName("GIVEN una comuna, WHEN se busca, THEN retorna las ubicaciones asociadas")
    void testBuscarPorComuna() {
        // GIVEN
        when(repositoryUbicacion.findByComunaIgnoreCase("Viña del Mar")).thenReturn(List.of(ubicacionMock));

        // WHEN
        List<ResponseDTO> resultado = serviceUbicacion.buscarPorComuna("Viña del Mar");

        // THEN
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(repositoryUbicacion, times(1)).findByComunaIgnoreCase("Viña del Mar");
    }
}