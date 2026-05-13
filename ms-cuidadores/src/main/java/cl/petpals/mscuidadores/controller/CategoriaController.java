package cl.petpals.mscuidadores.controller;

import cl.petpals.mscuidadores.modelo.Categoria;
import cl.petpals.mscuidadores.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuidadores")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    //Obtener todas las categorias
    @GetMapping
    public ResponseEntity<List<Categoria>> obtenerTodas(){
        return  ResponseEntity.ok(categoriaService.obtenerTodas());
    }

    //Obtener categorias por id
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerPorId(@PathVariable Long id){
        return categoriaService.obtenerPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //Crear una nueva Categoria
    @PostMapping
    public ResponseEntity<Categoria> crear(@Valid @RequestBody Categoria categoria){
        return ResponseEntity.status(201).body(categoriaService.guardar(categoria));
    }

    //Actualizar categoria
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(@PathVariable Long id, @Valid @RequestBody Categoria datos){
        return  categoriaService.obtenerPorId(id).map(existente ->{
            datos.setId(id);
            return ResponseEntity.ok(categoriaService.guardar(datos));
        }).orElse(ResponseEntity.notFound().build());
    }

    //ELiminar una categoria
    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> eliminar(@PathVariable Long id){
        if (categoriaService.obtenerPorId(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
