package com.servicios_mascotas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Servicios")
public class ServicioMascotas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 300)
    private String nombreServicio;

    @Column(nullable = false,length = 300)
    private String descripcion;

    @Column(nullable = false,length = 30)
    private double precio;





}
