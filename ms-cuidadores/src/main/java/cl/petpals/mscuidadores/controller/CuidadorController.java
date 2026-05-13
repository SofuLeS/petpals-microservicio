package cl.petpals.mscuidadores.controller;

import cl.petpals.mscuidadores.dto.CuidadorRequestDto;
import cl.petpals.mscuidadores.dto.CuidadorResponseDto;
import cl.petpals.mscuidadores.service.CuidadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
@RequestMapping("/api/cuidadores")
@RequiredArgsConstructor
public class CuidadorController {

    private final CuidadorService cuidadorService;

    //Get enlistartodo
    @GetMapping
    public ResponseEntity<List<CuidadorResponseDto>> obtenerTods(){
        return ResponseEntity.ok(cuidadorService.obtenerTodos());
    }

    //Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<CuidadorResponseDto> obtenerPorId(@PathVariable Long id){
        return cuidadorService.obtenerPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //Crear un nueo cliente
    @PostMapping
    public ResponseEntity<CuidadorResponseDto> crear(@Valid @RequestBody CuidadorRequestDto dto){
        return  ResponseEntity.status(201).body(cuidadorService.guardar(dto));
    }

    //Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<CuidadorResponseDto> actualozar(@PathVariable Long id, @Valid @RequestBody CuidadorRequestDto dto){
        return cuidadorService.actualizar(id,dto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{api}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        if (cuidadorService.obtenerPorId(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        cuidadorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    //Buscar por nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<CuidadorResponseDto>> buscarPorNombre(@RequestParam String nombre){
        return ResponseEntity.ok(cuidadorService.buscarPorNombre(nombre));
    }

    //Buscar especialidad
    @GetMapping("/especialidad")
    public ResponseEntity<List<CuidadorResponseDto>> buscarPorEspecialidad(@RequestParam String animal){
        return ResponseEntity.ok(cuidadorService.buscarPorEspecialidad(animal));
    }

    //BUscar ṕor disponibilidada
    @GetMapping("/disponibilidad")
    public ResponseEntity<List<CuidadorResponseDto>> listarDisponible(){
        return ResponseEntity.ok(cuidadorService.listarDisponibles());
    }

}
