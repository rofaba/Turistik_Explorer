package com.turistik_explorer.repository;


import com.turistik_explorer.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/*
 * Repositorio para la entidad Restaurant, con métodos personalizados para búsquedas por ciudad, tipo de cocina, rating y proximidad geográfica.
 */

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByCityIgnoreCase(String city);

    List<Restaurant> findByCityIgnoreCaseAndCuisineTypeIgnoreCase(String city, String cuisine);
    List<Restaurant> findByCityIgnoreCaseAndRatingGreaterThanEqual(String city, Double rating);

    @Query(value = "SELECT * FROM restaurant r WHERE " +
            "(6371 * acos(cos(radians(:lat)) * cos(radians(r.latitud)) * " +
            "cos(radians(r.longitud) - radians(:lng)) + sin(radians(:lat)) * " +
            "sin(radians(r.latitud)))) <= :distancia",
            nativeQuery = true)
    List<Restaurant> buscarCercanos(@Param("lat") double lat, @Param("lng") double lng, @Param("distancia") double distancia);}