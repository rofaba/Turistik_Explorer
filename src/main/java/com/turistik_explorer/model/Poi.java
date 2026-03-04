package com.turistik_explorer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa un Punto de Interés turístico (monumentos, museos, parques)
 * almacenados en los recursos del servidor.
 */

@Entity
@Table(name = "pois")
@Getter @Setter
public class Poi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @Size(max = 1000, message = "La descripción es demasiado larga")
    private String descripcion;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;

    private String categoria;

    @Column(name = "image_url")
    private String imageUrl;

    private String ciudad;
}
