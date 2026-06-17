package cl.petpals.mscuidadores.config;

import cl.petpals.mscuidadores.modelo.Categoria;
import cl.petpals.mscuidadores.modelo.Cuidador;
import cl.petpals.mscuidadores.repository.CategoriaRepository;
import cl.petpals.mscuidadores.repository.CuidadorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final CuidadorRepository cuidadorRepository;

    @Override
    public void run(String... arg){
        if (categoriaRepository.count() > 0){
            log.info(">>> PetPals: La base de datos ya tiene datos.");
            return;
        }
        log.info(">>> PetPals: Insertando datos de prueba iniciales...");

        //Crear Categorias
        Categoria perroGrande = categoriaRepository.save(new Categoria(null, "Perros Grande", "Cuidado de razas grandes"));
        Categoria gato = categoriaRepository.save(new Categoria(null,"Gatos","Cuidado de felinos domesticos" ));
        Categoria perro = categoriaRepository.save(new Categoria(null, "Perros","Cuidado de caninos domesticos"));
        Categoria exotico = categoriaRepository.save(new Categoria(null,"Exoticos","Cuidado de animales exoticos"));

        log.info(">>> PetPals: Datos cargados con exito!! ({} categoria,{} cuidadores)",categoriaRepository.count(),cuidadorRepository.count());

        cuidadorRepository.save(new Cuidador(null, "12345678-9", "Carlos", "González",
                912345678, "carlos@gmail.com", 28, true, 4.5, 3, 20, perro));
        cuidadorRepository.save(new Cuidador(null, "98765432-1", "María", "Soto",
                987654321, "maria@gmail.com", 32, true, 4.8, 7, 45, gato));
        cuidadorRepository.save(new Cuidador(null, "11111111-1", "Juan", "Pérez",
                911111111, "juan@gmail.com", 25, false, 3.9, 1, 8, perroGrande));
        cuidadorRepository.save(new Cuidador(null, "22222222-2", "Ana", "Ramírez",
                922222222, "ana@gmail.com", 30, true, 4.2, 5, 30, exotico));

        log.info(">>> PetPals: Datos cargados con exito!! ({} categorias, {} cuidadores)",
                categoriaRepository.count(), cuidadorRepository.count());
    }
}
