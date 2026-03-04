package com.turistik_explorer.repository;


import com.turistik_explorer.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findByCiudadIgnoreCase(String ciudad);

    @Query(value = "SELECT * FROM activity a WHERE " +
            "(6371 * acos(cos(radians(:lat)) * cos(radians(a.latitud)) * " +
            "cos(radians(a.longitud) - radians(:lng)) + sin(radians(:lat)) * " +
            "sin(radians(a.latitud)))) <= :distancia",
            nativeQuery = true)
    List<Activity> buscarCercanos(@Param("lat") double lat, @Param("lng") double lng, @Param("distancia") double distancia);
}