package com.turistik_explorer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa un lugar turístico del sistema:
 * POI, HOTEL o RESTAURANTE.
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

    /* ==============================
       Tipo de lugar
       ============================== */

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlaceType tipo = PlaceType.POI;

    /* ==============================
       Campos específicos HOTEL
       ============================== */

    private Integer estrellas;

    private Double precioNoche;

    private Boolean tienePiscina;

    /* ==============================
       Campos específicos RESTAURANTE
       ============================== */

    private String cuisineType;

    private Double rating;

    private Double averagePrice;

    /* ==============================
       Dirección común
       ============================== */

    private String direccion;
}