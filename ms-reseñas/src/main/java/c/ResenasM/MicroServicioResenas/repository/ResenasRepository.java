package c.ResenasM.MicroServicioResenas.repository;


import c.ResenasM.MicroServicioResenas.model.ResenasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ResenasRepository extends JpaRepository<ResenasModel, Long> {

    // aquiu vamos a ver las reseñas relacionadas al cuidador
    List<ResenasModel> findByIdCuidador(Long idCuidador);

    // Por si acaso tambien vamos a agg reseñas de dueños
    List<ResenasModel> findByIdDueno(Long idDueno);
}