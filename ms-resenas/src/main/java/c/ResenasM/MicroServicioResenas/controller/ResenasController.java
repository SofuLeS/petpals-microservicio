package c.ResenasM.MicroServicioResenas.controller;


import c.ResenasM.MicroServicioResenas.dto.ResenaRequestDTO;
import c.ResenasM.MicroServicioResenas.dto.ResenaResponseDTO;
import c.ResenasM.MicroServicioResenas.service.IResenaService;
import c.ResenasM.MicroServicioResenas.service.ResenaService;
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
    // Obtener todas las reseñas
    @GetMapping
    public ResponseEntity<List<ResenaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(resenaService.obtenerTodasLasResenas());
    }


    // Obtener reseñas de un cuidador específico
    @GetMapping("/cuidador/{idCuidador}")
    public ResponseEntity<List<ResenaResponseDTO>> listarPorCuidador(@PathVariable Long idCuidador) {
        return ResponseEntity.ok(resenaService.listarPorCuidador(idCuidador));

    }

}