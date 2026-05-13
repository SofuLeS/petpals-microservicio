package cl.petpals.mscuidadores.service;

import cl.petpals.mscuidadores.modelo.Categoria;
import cl.petpals.mscuidadores.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    //Obtener todas las catgorias
    public List<Categoria> obtenerTodas(){return categoriaRepository.findAll();}

    //Obtener Categoria Especifica
    public Optional<Categoria> obtenerPorId(Long id){return  categoriaRepository.findById(id);}

    //Guardar nieva categoria
    public Categoria guardar(Categoria categoria){return categoriaRepository.save(categoria);}

    //Eliminar
    public void eliminar(Long id){categoriaRepository.deleteById(id);}

}
