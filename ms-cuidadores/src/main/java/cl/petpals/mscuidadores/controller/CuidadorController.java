package cl.petpals.mscuidadores.controller;

import cl.petpals.mscuidadores.dto.CuidadorRequestDto;
import cl.petpals.mscuidadores.dto.CuidadorResponseDto;
import cl.petpals.mscuidadores.service.CuidadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id){
        if (cuidadorService.obtenerPorId(id).isEmpty()){
            Map<String, String> error = new LinkedHashMap<>();
            error.put("error", " Cuidador con id " +id+ " no existe");
            error.put("motivo", "No se encontro cuidaro con esa id en la base de datos");
            return ResponseEntity.status(404).body(error);
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

    //Buscar por mascotas cuidadas
    @GetMapping("/mascotas-cuidadas")
    public ResponseEntity<List<CuidadorResponseDto>> buscarPorMascotasCuidadas(@RequestParam Integer cantidad){
        return ResponseEntity.ok(cuidadorService.buscarPorMascotasCuidadas(cantidad));
    }

    //Buscar por experiencia
    @GetMapping("/experiencia")
    public ResponseEntity<List<CuidadorResponseDto>> buscarPorExperiencia(@RequestParam Integer anos){
        return ResponseEntity.ok(cuidadorService.buscarPorAniosExperincia(anos));
    }

}
