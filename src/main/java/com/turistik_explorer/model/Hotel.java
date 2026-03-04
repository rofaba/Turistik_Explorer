package com.turistik_explorer.model;

import jakarta.persistence.*;
import lombok.Data;

/** * Representa un hotel turístico que los usuarios pueden reservar. */

@Entity
@Table(name = "hoteles")
@Data
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private int estrellas; // calificaciones de 1 a 5

    @Column(name = "precio_noche")
    private double precioNoche;

    private String direccion;

    private boolean tienePiscina;

    private String ciudad;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;

}