package cl.petpals.mscuidadores.repository;

import cl.petpals.mscuidadores.modelo.Cuidador;
import org.springframework.data.jpa.repository.JpaRepository;

//<> se llamanf genericos, JPA es una maquina generica que springbot creo, y esta nio sabe que es lo qye tu
//tienes que guardar, por eso hay que pasarle dos parametros para saber que es lo que tu quieres guardar
//El primer parametro le esta dicendo que es lo que va a manejar
//
public interface CuidadorRepository extends JpaRepository<Cuidador, Long> {


}
