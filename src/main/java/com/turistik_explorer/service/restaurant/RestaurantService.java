package com.turistik_explorer.service.restaurant;

import com.turistik_explorer.exception.NotFoundException;
import com.turistik_explorer.model.Hotel;
import com.turistik_explorer.model.Poi;
import com.turistik_explorer.model.Restaurant;
import com.turistik_explorer.repository.HotelRepository;
import com.turistik_explorer.repository.PoiRepository;
import com.turistik_explorer.repository.RestaurantRepository;
import com.turistik_explorer.util.GeoUtils;
import com.turistik_explorer.web.dto.NearbyPlaceDto;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * Servicio para manejar operaciones relacionadas con restaurantes, incluyendo búsqueda de POIs y hoteles cercanos.
 */

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final PoiRepository poiRepository;
    private final HotelRepository hotelRepository;

    public RestaurantService(RestaurantRepository restaurantRepository,
                             PoiRepository poiRepository,
                             HotelRepository hotelRepository) {
        this.restaurantRepository = restaurantRepository;
        this.poiRepository = poiRepository;
        this.hotelRepository = hotelRepository;
    }

    public Restaurant findById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El Restaurante con ID " + id + " no existe."));
    }

    public List<NearbyPlaceDto> getNearbyPois(Restaurant restaurant, int limit) {
        List<Poi> pois = poiRepository.findByCiudadIgnoreCase(restaurant.getCity());
        return pois.stream()
                .filter(p -> p.getLatitud() != null && p.getLongitud() != null)
                .map(p -> new NearbyPlaceDto(
                        p.getId(),
                        p.getNombre(),
                        p.getLatitud(),
                        p.getLongitud(),
                        GeoUtils.distanceKm(restaurant.getLatitud(), restaurant.getLongitud(), p.getLatitud(), p.getLongitud()),
                        "POI"
                ))
                .sorted(Comparator.comparingDouble(NearbyPlaceDto::getDistanceKm))
                .limit(limit)
                .toList();
    }

    public List<NearbyPlaceDto> getNearbyHotels(Restaurant restaurant, int limit) {
        List<Hotel> hotels = hotelRepository.findByCiudadIgnoreCase(restaurant.getCity());
        return hotels.stream()
                .filter(h -> h.getLatitud() != null && h.getLongitud() != null)
                .map(h -> new NearbyPlaceDto(
                        h.getId(),
                        h.getNombre(),
                        h.getLatitud(),
                        h.getLongitud(),
                        GeoUtils.distanceKm(restaurant.getLatitud(), restaurant.getLongitud(), h.getLatitud(), h.getLongitud()),
                        "HOTEL"
                ))
                .sorted(Comparator.comparingDouble(NearbyPlaceDto::getDistanceKm))
                .limit(limit)
                .toList();
    }
}