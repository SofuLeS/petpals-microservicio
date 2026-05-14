package cl.petpals.ms_reservas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idDueno;

    @Column(nullable = false)
    private Long idMascota;

    @Column(nullable = false)
    private Long idCuidador;

    @Column(nullable = false)
    private Long idServicio;

    @Column(nullable = false)
    private LocalDateTime fechaReserva;

    @Column(length = 250)
    private String estado;

}
