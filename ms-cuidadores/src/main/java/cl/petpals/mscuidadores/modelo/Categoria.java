package cl.petpals.mscuidadores.modelo;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    //Muchos cuidadores pertenecen a uno
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
}
