package cl.petpals.mscuidadores.modelo;

//El cliente nunca debe de tocar el jpa

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
//Permite dejar de maner explicita el nombre de la tabla
@Table(name = "cuidadores")
public class Cuidador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 12, unique = true)
    private String rut;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false, length = 150)
    private String apellidos;

    @Column(unique = true)
    private Integer telefono;

    @Column(nullable = false, length = 254, unique = true)
    private String email;

    @Column(nullable = false, length = 2)
    private int edad;

    @Column(nullable = false)
    private boolean disponibilidad = true;

    @Column(name = "calificacion")
    private Double calificacion;

    @Column(name = "anos_experiencia")
    private Integer anosExperincia;

    @Column(name = "mascotas_cuidadas")
    private Integer mascotasCuidadas;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
}
