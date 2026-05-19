package cl.petpals.mscuidadores.repository;

import cl.petpals.mscuidadores.modelo.Cuidador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 *Al hacer el que la interfas hereded de JpaRepo, spring te regala automaticamente los metodos
 * basico
 * Cuidaror esta representa la entidad donde va a trabajar  y
 * Long esta referenciando a la id
 * */
public interface CuidadorRepository extends JpaRepository<Cuidador, Long> {
//En springDataJpa tu no escribes las consultas el nombre del metodo ES la consula

    //Buscar por nombres
    //Containig: Buscara el nombre sin importar donde esta o si esta incompleto
    //IgnoreCase: Buscara el nombre sin discrimanar por mayusculas o minusculas
    //Aqui no se usa overr.. pq es una consulta no es algo que se tiene que reescrivir
    List<Cuidador> findByNombreContainingIgnoreCase(String nombre);
    List<Cuidador> findByApellidosContainingIgnoreCase(String apellidos);

    //No ocupa variable pq el criterio dee busqueda ya esta explicito en en el nombre
    List<Cuidador> findByDisponibilidadTrue();
    List<Cuidador> findByCategoriaNombreContainingIgnoreCase(String categoriaPet);
    List<Cuidador> findByCategoriaId(Long categoriaId);

    List<Cuidador> findByMascotasCuidadasGreaterThanEqual(Integer cantidad);
    List<Cuidador> findByAnosExperinciaGreaterThanEqual(Integer anos);

    Optional<Cuidador> findByRut(String rut);
    Optional<Cuidador> findByEmail(String email);

    boolean existsByRut(String rut);
    boolean existsByEmail(String email);



}
