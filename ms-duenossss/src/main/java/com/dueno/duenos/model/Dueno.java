package com.dueno.duenos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Duenos")
public class Dueno {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String rut;

    @Column(nullable = false , length = 200)
    private  String nombres;

    @Column(nullable = false, length = 200)
    private String apellidos;

    @Column(nullable = false,length = 15)
    private Integer telefono;

    @Column(nullable = false, length = 200)
    private String correo;

    @Column(nullable = false, length = 400)
    private String direccion;




}
