package cl.petpals.mscuidadores.config;

import cl.petpals.mscuidadores.modelo.Categoria;
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
        Categoria perroGrande = categoriaRepository.save(new Categoria(null, "Perro Grande", "Cuidado de razas grandes"));
        Categoria gatos = categoriaRepository.save(new Categoria(null,"Gatos","Cuidado de felinos domesticos" ));
        Categoria perros = categoriaRepository.save(new Categoria(null, "Perro","Cuidado de caninos domesticos"));
        Categoria exoticos = categoriaRepository.save(new Categoria(null,"Exoticos","Cuidado de animales exoticos"));

        log.info(">>> PetPals: Datos cargados con exito!! ({} categoria,{} cuidadores)",categoriaRepository.count(),cuidadorRepository.count());

    }
}
