package cl.petpals.ms_reservas.service;

import cl.petpals.ms_reservas.dto.ReservaRequestDto;
import cl.petpals.ms_reservas.dto.ReservaResponseDto;
import cl.petpals.ms_reservas.model.EstadoReserva;
import cl.petpals.ms_reservas.model.Reserva;
import cl.petpals.ms_reservas.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;

    private ReservaResponseDto mapToDto(Reserva reserva){
        return new ReservaResponseDto(
                reserva.getId(),
                reserva.getIdDueno(),
                reserva.getIdMascota(),
                reserva.getIdCuidador(),
                reserva.getIdServicio(),
                reserva.getFechaReserva(),
                reserva.getEstadoReserva()
        );
    }

    public ReservaResponseDto guardar(ReservaRequestDto requestDto){
        Reserva reserva = new Reserva();
        reserva.setIdDueno(requestDto.getIdDueno());
        reserva.setIdMascota(requestDto.getIdMascota());
        reserva.setIdCuidador(requestDto.getIdCuidador());
        reserva.setIdServicio(requestDto.getIdServicio());
        reserva.setFechaReserva(requestDto.getFechaReserva());
        reserva.setEstadoReserva(EstadoReserva.PENDIENTE);
        return mapToDto(reservaRepository.save(reserva));
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
