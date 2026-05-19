package cl.mastocas.petPal.mascotasConfig;


import cl.mastocas.petPal.mascotaController.MascotaController;
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
    private final MascotaController mascotaController;

    @Override
    public void run(String... args) throws Exception {

        if (mascotasRepository.count() > 0) {
            log.info(" La base de dato ya tiene mascotas");
            return;
        }
        mascotasRepository.save(new MascotaModel(null, "Fido", "Golden Retriever", 3, "Ninguna", "Perro", 1L));
        mascotasRepository.save(new MascotaModel(null, "Luna", "Siamés", 2, "Lactosa", "Gato", 2L));
        mascotasRepository.save(new MascotaModel(null, "Rex", "Pastor Alemán", 5, "Polen", "Perro", 3L));
        mascotasRepository.save(new MascotaModel(null, "Michi", "Persa", 1, "Pescado", "Gato", 4L));
        log.info("Completado! {} mascotas insertadas.", mascotasRepository.count());
    }
}
