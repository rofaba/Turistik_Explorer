package com.turistik_explorer.repository;


import com.turistik_explorer.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByCiudadIgnoreCase(String ciudad);

    @Query(value = "SELECT * FROM hoteles h WHERE " +
            "(6371 * acos(cos(radians(:lat)) * cos(radians(h.latitud)) * " +
            "cos(radians(h.longitud) - radians(:lng)) + sin(radians(:lat)) * " +
            "sin(radians(h.latitud)))) <= :distancia",
            nativeQuery = true)
    List<Hotel> buscarCercanos(@Param("lat") double lat, @Param("lng") double lng, @Param("distancia") double distancia);}