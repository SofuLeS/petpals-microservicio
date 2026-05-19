package com.MUbicacion.MicroServicioUbicacion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ubicaciones") //plural
public class ModelUbicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idCuidador;
    @Column(nullable = false)
    private String comuna;
    @Column(nullable = false)
    private String ciudad;
    @Column(nullable = false)
    private String region;
}

