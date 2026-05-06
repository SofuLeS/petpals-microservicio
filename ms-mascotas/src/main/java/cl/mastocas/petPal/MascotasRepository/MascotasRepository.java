package cl.mastocas.petPal.MascotasRepository;


import cl.mastocas.petPal.MascotasModel.MascotaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MascotasRepository extends JpaRepository<MascotaModel, Long> {

    // Busca mascotas por nombre ignorando mayúsculas/minúsculas
    List<MascotaModel> findByNombreContainingIgnoreCase(String nombre);

    // Filtro para buscar todas las mascotas que pertenecen a un mismo dueño
    List<MascotaModel> findByIdDueno(Long idDueno);
    // para el tipo de mascotad
    List<MascotaModel> findByTipoIgnoreCase(String tipo);
}