package com.turistik_explorer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/** * Representa una reseña que los usuarios pueden dejar para hoteles, restaurantes o puntos de interés. */

@Entity
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String usuario;
    private String comentario;
    private int puntuacion; // 1 a 5
    private Long targetId;  // ID del Hotel, Restaurant o POI
    private String targetType; // "HOTEL", "RESTAURANT" o "POI"
}