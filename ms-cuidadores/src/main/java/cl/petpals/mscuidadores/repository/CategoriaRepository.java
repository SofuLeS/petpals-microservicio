package cl.petpals.mscuidadores.repository;

import cl.petpals.mscuidadores.modelo.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
