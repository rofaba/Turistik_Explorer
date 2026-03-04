package com.turistik_explorer.model;

import jakarta.persistence.*;
import lombok.Data;

/** * Representa una actividad turística (tours, eventos, experiencias) que los usuarios pueden reservar. */

@Entity
@Data
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String duracion;
    private String ciudad;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;


    private boolean exterior; // Para validar con el clima
}