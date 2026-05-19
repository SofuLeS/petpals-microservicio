package cl.petpals.ms_pago.service;

import cl.petpals.ms_pago.dto.PagoRequestDto;
import cl.petpals.ms_pago.dto.PagoResponseDto;
import cl.petpals.ms_pago.dto.ReservaResponseDto;
import cl.petpals.ms_pago.modelo.EstadoPago;
import cl.petpals.ms_pago.modelo.Pago;
import cl.petpals.ms_pago.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;
    private final WebClient.Builder webClientBuilder;

    @Value("${ms.reservas.url}")
    private String reservaUrl;

    private ReservaResponseDto obtenerReserva(Long idReserva){
        return webClientBuilder
                .baseUrl(reservaUrl)
                .build()
                .get()
                .uri("/api/reservas/" + idReserva)
                .retrieve()
                .bodyToMono(ReservaResponseDto.class)
                .block();
    }

    private PagoResponseDto mapToDto(Pago pago){
        return new PagoResponseDto(
                pago.getId(),
                pago.getIdReserva(),
                pago.getIdDueno(),
                pago.getMonto(),
                pago.getMetodoPago(),
                pago.getEstadoPago(),
                pago.getFechaPago(),
                pago.getDescripcion()

        );
    }

    public PagoResponseDto guardar(PagoRequestDto dto){
        ReservaResponseDto reserva = obtenerReserva(dto.getIdReserva());
        if (reserva == null){
            throw new RuntimeException("La reserva con id "+ dto.getIdReserva() + " no existe");
        }
        Pago pago = new Pago();
        pago.setIdReserva(dto.getIdReserva());
        pago.setIdDueno(dto.getIdDueno());
        pago.setMonto(dto.getMonto());
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setDescripcion(dto.getDescripcion());
        pago.setEstadoPago(EstadoPago.PENDIENTE);
        pago.setFechaPago(LocalDateTime.now());
        return mapToDto(pagoRepository.save(pago));
    }

    //Obtener por id
    public Optional<PagoResponseDto> obtenerPorId(Long id){
        return pagoRepository.findById(id).map(this::mapToDto);
    }

    //ObtenerTodos
    public List<PagoResponseDto> obtenerTodos(){
        return pagoRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    //Obtener por reserva
    public List<PagoResponseDto> listarPorReserva(Long idReserva){
        return pagoRepository.findByIdReserva(idReserva).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    //Obtener por dueño
    public List<PagoResponseDto> listarPorDueno(Long idDueno){
        return pagoRepository.findByIdDueno(idDueno).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    //Obtener por estado
    public List<PagoResponseDto> listarPorEstado(EstadoPago estadoPago){
        return pagoRepository.findByEstadoPago(estadoPago).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    //Actualizar estado
    public Optional<PagoResponseDto> actualizarEstado(Long id, EstadoPago nuvoEstado){
        return pagoRepository.findById(id).map(existente -> {existente.setEstadoPago(nuvoEstado);
        return mapToDto(pagoRepository.save(existente));
        });
    }

    //Eliminars
    public void eliminar(Long id){
        pagoRepository.deleteById(id);
    }

}