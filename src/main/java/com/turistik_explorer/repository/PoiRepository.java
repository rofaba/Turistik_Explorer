package com.turistik_explorer.repository;


import com.turistik_explorer.model.PlaceType;
import com.turistik_explorer.model.Poi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interfaz para el acceso a datos de los Puntos de Interés.
 */
@Repository
public interface PoiRepository extends JpaRepository<Poi, Long> {

    /**
     * Realiza una búsqueda geoespacial de puntos de interés utilizando la fórmula de Haversine.
     * * Esta consulta nativa calcula la distancia ortodrómica entre un punto dado (lat, lng)
     * y cada registro en la base de datos basándose en la curvatura terrestre (radio medio de 6371 km).
     *
     * @param lat       Latitud del punto de origen en grados decimales.
     * @param lng       Longitud del punto de origen en grados decimales.
     * @param distancia Radio máximo de búsqueda expresado en kilómetros (km).
     * @return          Una lista de entidades {@link Poi} que se encuentran dentro del radio especificado.
     */

    @Query(value = "SELECT * FROM pois p WHERE " +
            "(6371 * acos(cos(radians(:lat)) * cos(radians(p.latitud)) * " +
            "cos(radians(p.longitud) - radians(:lng)) + sin(radians(:lat)) * " +
            "sin(radians(p.latitud)))) <= :distancia",
            nativeQuery = true)

    /**
     * Recupera una lista de puntos de interés cercanos a una ubicación dada.
     * @param lat Latitud del punto de referencia.
     * @param lng Longitud del punto de referencia.
     * @param distancia Distancia máxima en kilómetros para considerar un POI como cercano.
     * @return Lista de POIs encontrados dentro de la distancia especificada.
     */
    List<Poi> buscarCercanos(@Param("lat") double lat, @Param("lng") double lng, @Param("distancia") double distancia);

    /**
     * Recupera una lista de puntos de interés filtrados por el nombre de la ciudad.
     * @param ciudad Nombre de la ciudad (ej: Málaga, Sevilla).
     * @return Lista de POIs encontrados.
     */
    List<Poi> findByCiudadIgnoreCase(String ciudad);
    List<Poi> findByCiudadIgnoreCaseAndTipo(String ciudad, PlaceType tipo);
}