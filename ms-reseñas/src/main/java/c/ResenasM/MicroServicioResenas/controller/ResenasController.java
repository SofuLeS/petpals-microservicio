package c.ResenasM.MicroServicioResenas.controller;


import c.ResenasM.MicroServicioResenas.dto.ResenaRequestDTO;
import c.ResenasM.MicroServicioResenas.dto.ResenaResponseDTO;
import c.ResenasM.MicroServicioResenas.service.IResenaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resenas")
public class ResenasController {

    @Autowired
    private IResenaService resenaService;



    //Endpoint para crear una reseña
    @PostMapping
    public ResponseEntity<ResenaResponseDTO> crearResena(@Valid @RequestBody ResenaRequestDTO dto) {
        ResenaResponseDTO respuesta = resenaService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    // listar reseñas de un cuidador
    @GetMapping("/cuidador/{id}")
    public ResponseEntity<List<ResenaResponseDTO>> listarPorCuidador(@PathVariable("id") Long idCuidador) {
        List<ResenaResponseDTO> lista = resenaService.listarPorCuidador(idCuidador);
        return ResponseEntity.ok(lista);
    }

    @GetMapping
    public ResponseEntity<List<ResenaResponseDTO>> mostrarTodas() {
        List<ResenaResponseDTO> resenas = resenaService.obtenerTodasLasResenas();
        return ResponseEntity.ok(resenas);
    }
}