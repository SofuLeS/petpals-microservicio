package com.historial_petpals.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Historial")
public class Historial {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idReservas;

    @Column(nullable = false)
    private Integer idDueno;

    @Column(nullable = false)
    private Long idCuidador;

    @Column(nullable = false)
    private Long idMascota;

    @Column(nullable = false)
    private Long idServicio;













}
