package cl.petpals.mscuidadores.service;

import cl.petpals.mscuidadores.dto.CuidadorRequestDto;
import cl.petpals.mscuidadores.dto.CuidadorResponseDto;
import cl.petpals.mscuidadores.modelo.Categoria;
import cl.petpals.mscuidadores.modelo.Cuidador;
import cl.petpals.mscuidadores.repository.CategoriaRepository;
import cl.petpals.mscuidadores.repository.CuidadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CuidadorService {
    //Nesario para buscar la ca
    private final CategoriaRepository categoriaRepository;
    private final CuidadorRepository cuidadorRepository;

    //Mapeo: entiddada -> responseDto
    private CuidadorResponseDto mapToDto(Cuidador cuidador){
        return new CuidadorResponseDto(
                cuidador.getId(),
                cuidador.getRut(),
                cuidador.getNombre(),
                cuidador.getApellidos(),
                cuidador.getTelefono(),
                cuidador.getEmail(),
                cuidador.getEdad(),
                cuidador.getCategoria().getNombre(),
                cuidador.isDisponibilidad()
        );
    }

    // Obtener todos
    public List<CuidadorResponseDto> obtenerTodos(){
        return cuidadorRepository.findAll().stream().map(this::mapToDto)
                .collect(Collectors.toList());
    }

    //Obtrner por id
    public Optional<CuidadorResponseDto> obtenerPorId(Long id){
        return cuidadorRepository.findById(id).map(this::mapToDto);
    }

    //Buscar por Categoria
    public List<CuidadorResponseDto> buscarPorEspecialidad(String especialidad){
        return cuidadorRepository.findByCategoriaNombreContainingIgnoreCase(especialidad)
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    //Buscar por dispoinibilidad
    public List<CuidadorResponseDto> listarDisponibles(){
        return cuidadorRepository.findByDisponibilidadTrue().stream()
                .map(this::mapToDto).collect(Collectors.toList());
    }

    //Guardars
    public  CuidadorResponseDto guardar(CuidadorRequestDto dto){
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con id: "+ dto.getCategoriaId()));

        Cuidador cuidador = new Cuidador();
        cuidador.setRut(dto.getRut());
        cuidador.setNombre(dto.getNombre());
        cuidador.setApellidos(dto.getApellidos());
        cuidador.setTelefono(dto.getTelefono());
        cuidador.setEmail(dto.getEmail());
        cuidador.setDisponibilidad(dto.getDisponibilidad());
        cuidador.setCategoria(categoria);

        return mapToDto(cuidadorRepository.save(cuidador));
    }

    //ELiminar
    public void eliminar(Long id){
        cuidadorRepository.deleteById(id);
    }

    //Actualizar
    public Optional<CuidadorResponseDto> actualizar(Long id, CuidadorRequestDto dto){
        return cuidadorRepository.findById(id).map( existente ->{
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId()).orElseThrow(() -> new RuntimeException("Categoria no encontrada con id: " + dto.getCategoriaId()));

            existente.setRut(dto.getRut());
            existente.setNombre(dto.getNombre());
            existente.setApellidos(dto.getApellidos());
            existente.setTelefono(dto.getTelefono());
            existente.setEmail(dto.getEmail());
            existente.setEdad(dto.getEdad());
            existente.setDisponibilidad(dto.getDisponibilidad());
            existente.setCategoria(categoria);
            return mapToDto(cuidadorRepository.save(existente));
        });
    }
}
