package cl.mastocas.petPal.mascotasService;

import cl.mastocas.petPal.mascotasDTO.MascotaRequestDTO;
import cl.mastocas.petPal.mascotasDTO.MascotaResponseDTO;
import java.util.List;
import java.util.Optional;

public interface IMascotaService {
    /* Definimos los metodos que vamo a usar en una interface distinta dentro
     de service para una mejor ejecucion y que la clase controller dependa unicamente
     de una abstraccion y no implementacion
     */

    MascotaResponseDTO guardar(MascotaRequestDTO dto);

    List<MascotaResponseDTO> obtenerTodos();

    List<MascotaResponseDTO> obtenerTodas();

    Optional<MascotaResponseDTO> obtenerPorId(Long id);
    // obliga a la validacion

    MascotaResponseDTO actualizar(Long id, MascotaRequestDTO dto);

    // void como no hay datos que devuelva es una accion finalizada.

    void eliminar(Long id);
}