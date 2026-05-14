package cl.mastocas.petPal.mascotasConfig;


import cl.mastocas.petPal.mascotasModel.MascotaModel;
import cl.mastocas.petPal.mascotasRepository.MascotasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
@Slf4j

public class Datainizt implements  CommandLineRunner {
    //CommandLinerRunner lo obliga a implementar run .
    // sprint lo ejecutara autom
    private final MascotasRepository mascotasRepository;

    @Override
    public void run(String... args) throws Exception {

        if (mascotasRepository.count() > 0) {
            log.info(" La base de dato ya tiene mascotas");
            return;
        }
        mascotasRepository.save(new MascotaModel(null, "Fido", "Golden Retriever", 3, "Ninguna", "Perro", 101L));
        mascotasRepository.save(new MascotaModel(null, "Luna", "max", 2, "Pelos", "loro", 102L));
        mascotasRepository.save(new MascotaModel(null, "Rex", "Pastor Aleman", 5, "Polen", "Perro", 101L));
        mascotasRepository.save(new MascotaModel(null, "Michi", "Collie", 1, "Pescado", "Perro", 103L));
        log.info("Completado! {} mascotas insertadas.", mascotasRepository.count());
    }
}
