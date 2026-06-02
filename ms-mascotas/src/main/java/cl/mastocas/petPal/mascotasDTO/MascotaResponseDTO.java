package cl.mastocas.petPal.mascotasDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MascotaResponseDTO {

    //devolveremos id a consola.
    //Aqui no nesecitamos validaciones porque se supone que ya fue validado en la entrada

    private Long id_mascota;
    private String nombre;
    private String raza;
    private Integer edad;
    private String alergias;
    private String tipoMascota;

    // Aqui la relacion
    private Long idDueno;

    // mensajes de salida para saber si funciona bien
    private String mensaje;
}