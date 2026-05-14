package cl.mastocas.petPal.mascotasService;

import cl.mastocas.petPal.mascotasDTO.MascotaRequestDTO;
import cl.mastocas.petPal.mascotasDTO.MascotaResponseDTO;
import cl.mastocas.petPal.mascotasModel.MascotaModel;
import cl.mastocas.petPal.mascotasRepository.MascotasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //inyectar el Repository autom
public class MascotaService implements IMascotaService {

    private final MascotasRepository mascotaRepository;

    // MAPEO de model a response dto
    // los transforma en una reapuesta json
    private MascotaResponseDTO MapDTO(MascotaModel mascota) {
        return new MascotaResponseDTO(
                mascota.getId_mascota(),
                mascota.getNombre(),
                mascota.getRaza(),
                mascota.getEdad(),
                mascota.getAlergias(),
                mascota.getTipoMascota(),
                mascota.getIdDueno(),
                " Proceso realizado con exito! Mascota Feliz :)."
        );
    }

    // Aqui recibe los datos del dto para la bbdd y postman
    @Override
    public MascotaResponseDTO guardar(MascotaRequestDTO dto) {
        // Aqui me dio error en el post entonces agg limpiar y ignorar case
        String tipoLimpio = dto.getTipoMascota().trim();

        MascotaModel nuevaMascota = new MascotaModel();

        nuevaMascota.setNombre(dto.getNombre());
        nuevaMascota.setRaza(dto.getRaza());
        nuevaMascota.setEdad(dto.getEdad());
        nuevaMascota.setAlergias(dto.getAlergias());
        //le pasamos el dato limpio
        nuevaMascota.setTipoMascota(tipoLimpio);

        nuevaMascota.setIdDueno(dto.getIdDueno());

        return MapDTO(mascotaRepository.save(nuevaMascota));
    }


    // obtiene una lista y la transforma
    @Override
    public List<MascotaResponseDTO> obtenerTodos() {
        return mascotaRepository.findAll().stream()
                .map(this::MapDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<MascotaResponseDTO> obtenerTodas() {
        return obtenerTodos();
    }


    // Busca una mascota por id y si existe, la mapea hacia el DTO
    @Override
    public java.util.Optional<MascotaResponseDTO> obtenerPorId(Long id) {
        return mascotaRepository.findById(id).map(this::MapDTO);
    }
    // Busca la mascota, actualiza ,
    // Si no existe, lanza una excepción capturada por el packg exception
    @Override
    public MascotaResponseDTO actualizar(Long id, MascotaRequestDTO dto) {
        return mascotaRepository.findById(id).map(existente -> {
            existente.setNombre(dto.getNombre());
            existente.setRaza(dto.getRaza());
            existente.setEdad(dto.getEdad());
            existente.setAlergias(dto.getAlergias());
            existente.setTipoMascota(dto.getTipoMascota());
            existente.setIdDueno(dto.getIdDueno());
            return MapDTO(mascotaRepository.save(existente));
        }).orElseThrow(() -> new RuntimeException("No se encontro la mascota con ID: " + id));
    }


    // Verifica existencia antes de borrar para evitar errores de BBDD
    @Override
    public void eliminar(Long id) {
        if (!mascotaRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar, No existe la mascota con ID: " + id);
        }
        mascotaRepository.deleteById(id);
        //delett
    }


    // Busca mascotas por nombre
    public List<MascotaResponseDTO> buscarPorNombre(String nombre) {
        return mascotaRepository.findByNombreContainingIgnoreCase(nombre)
                .stream().map(this::MapDTO).collect(Collectors.toList());
    }

    // mascotas por el tipo
    public List<MascotaResponseDTO> buscarPorTipo(String tipo) {
        return mascotaRepository.findByTipoMascotaIgnoreCase(tipo)
                .stream().map(this::MapDTO).collect(Collectors.toList());
    }

    // Busca todas las mascotas que pertenecen a un mismo dueño
    public List<MascotaResponseDTO> buscarPorDueno(Long idDueno) {
        return mascotaRepository.findByIdDueno(idDueno)
                .stream().map(this::MapDTO).collect(Collectors.toList());
    }
}