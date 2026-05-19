package c.ResenasM.MicroServicioResenas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "resenas")
@Data
public class ResenasModel {

    @Id //genera id autom.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResena;
    //El long a diferencia del interger guarda y almacena muchos mas datos.

    @Min(1) //para las estrellas
    @Max(5)
    @Column(nullable = false)

    private Integer estrellas;

    //Un mensaje para la bases de datos. (limitado para q no sea muy largo).
    @NotBlank(message = "El comentario no puede estar vacio")
    @Size(max = 100, message = "El comentario es muy largo (máximo 255 caracteres)")
    @Column(length = 100) //largo
    private String comentarios;

    // Id de las relaciones de la tabla.

    @Column(nullable = false)
    private Long idReserva;

    @Column(nullable = false)
    private Long idDueno;

    @Column(nullable = false)
    private Long idCuidador;

    private LocalDate fechaResena;

    // algo nuevo con esta anotacion creamos una fecha automatica al momento de crear la reserva.
    @PrePersist
    protected void onCreate() {
        this.fechaResena = LocalDate.now();
    }
}