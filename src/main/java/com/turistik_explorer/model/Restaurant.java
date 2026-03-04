package com.turistik_explorer.model;

import jakarta.persistence.*;
import lombok.Data;

/** * Representa un restaurante turístico que los usuarios pueden visitar. */

@Entity
@Data
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String city;

    private String cuisineType;
    private Double rating;
    private String address;
    private Double averagePrice;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;
}