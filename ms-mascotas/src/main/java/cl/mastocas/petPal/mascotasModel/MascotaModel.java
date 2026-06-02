package cl.mastocas.petPal.mascotasModel;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data //Genera instruccion de lombok
@AllArgsConstructor  //Novacio
@NoArgsConstructor //vacio
@Entity // Intruccion para crear tablas
@Table(name = "mascotas") // Crea tablas
public class MascotaModel {
    @Id //PK le dice a la bbdd que es identificador unico.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto incrementa el ultimo id.
    private Long id_mascota;

    //Atributos.
    @Column(nullable = false, length = 30)
    private String nombre;

    @Column(nullable = false, length = 20)
    private String raza;

    @Column(nullable = false)
    private Integer edad;

    @Column(length = 40)
    private String alergias;

    @Column(length = 100)
    private String tipoMascota;

    // Aqui hacemos un llamado al micro servicio dueño para haceruna relacion entre dos tablas.
    @Column(name = "id_dueño", nullable = false)
    private Long idDueno;
}