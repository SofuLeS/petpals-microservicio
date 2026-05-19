package cl.petpals.ms_reservas.service;

import cl.petpals.ms_reservas.dto.CuidadorResponseDto;
import cl.petpals.ms_reservas.dto.ReservaRequestDto;
import cl.petpals.ms_reservas.dto.ReservaResponseDto;
import cl.petpals.ms_reservas.model.EstadoReserva;
import cl.petpals.ms_reservas.model.Reserva;
import cl.petpals.ms_reservas.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final WebClient.Builder webClientBuilder;
    private final HistorialAsyncService historialAsyncService;

    @Value("${ms.cuidadores.url}")
    private String cuidadoresUrl;



    private CuidadorResponseDto obtenerCuidador(Long idCuidador){
        return webClientBuilder
                .baseUrl(cuidadoresUrl)
                .build()
                .get()
                .uri("/api/cuidadores/" + idCuidador)
                .retrieve()
                .bodyToMono(CuidadorResponseDto.class)
                .block();
    }


    public ReservaResponseDto guardar(ReservaRequestDto dto){
        CuidadorResponseDto cuidador = obtenerCuidador(dto.getIdCuidador());
        if (cuidador == null){
            throw new RuntimeException("El cuidador con id" + dto.getIdCuidador() + " no existe");
        }
        Reserva reserva = new Reserva();
        reserva.setId(dto.getIdServicio());
        reserva.setIdDueno(dto.getIdDueno());
        reserva.setIdMascota(dto.getIdMascota());
        reserva.setIdCuidador(dto.getIdCuidador());
        reserva.setIdServicio(dto.getIdServicio());
        reserva.setFechaReserva(dto.getFechaReserva());
        reserva.setFechaFinReserva(dto.getFechaFinReserva());
        reserva.setEstadoReserva(EstadoReserva.PENDIENTE);


        //se mandan los datos que se guardo en reserva hacia historial
        historialAsyncService.enviarHistorial(reserva);

        return mapToDto(reservaRepository.save(reserva));
    }

    private ReservaResponseDto mapToDto(Reserva reserva){
        return new ReservaResponseDto(
                reserva.getId(),
                reserva.getIdDueno(),
                reserva.getIdMascota(),
                reserva.getIdCuidador(),
                reserva.getIdServicio(),
                reserva.getFechaReserva(),
                reserva.getFechaFinReserva(),
                reserva.getEstadoReserva()
        );
    }

    //Obtener por id
    public Optional<ReservaResponseDto> obtenerPorId(Long id){
        return reservaRepository.findById(id).map(this::mapToDto);
    }

    //Obtener todos los resultaods
    public List<ReservaResponseDto> obtenerTodas(){
        return reservaRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    //Listar por cuidador
    public List<ReservaResponseDto> listarPorCuidador(Long idCuidador){
        return reservaRepository.findByIdCuidador(idCuidador).stream().map(this::mapToDto).collect(Collectors.toList());

    }

    //Listar por dueño
    public List<ReservaResponseDto> listarPorDueno(Long idDueno){
        return reservaRepository.findByIdDueno(idDueno).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    //Actualizar estado
    public Optional<ReservaResponseDto> actualizarEstado(Long id, EstadoReserva nuevoEstado){
        return reservaRepository.findById(id).map(existente-> {existente.setEstadoReserva(nuevoEstado);
        return mapToDto(reservaRepository.save(existente));
        });
    }

    public void eliminar(Long id){
        reservaRepository.deleteById(id);
    }
}
