package cl.petpals.ms_pago.modelo;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Pago")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idReserva;

    @Column(nullable = false)
    private Long idDueno;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 50)
    private MetodoPago metodoPago;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private EstadoPago estadoPago;

    @Column(nullable = false)
    private LocalDateTime fechaPago;

    @Column(length = 250)
    private String descripcion;
}
