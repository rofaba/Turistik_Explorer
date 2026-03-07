package com.turistik_explorer.service.hotel;

import com.turistik_explorer.model.Hotel;
import com.turistik_explorer.model.Poi;
import com.turistik_explorer.model.Restaurant;
import com.turistik_explorer.repository.HotelRepository;
import com.turistik_explorer.repository.PoiRepository;
import com.turistik_explorer.repository.RestaurantRepository;
import com.turistik_explorer.web.dto.NearbyPlaceDto;
import com.turistik_explorer.util.GeoUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Comparator;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final PoiRepository poiRepository;
    private final RestaurantRepository restaurantRepository;

    public HotelService(HotelRepository hotelRepository,
                        PoiRepository poiRepository,
                        RestaurantRepository restaurantRepository) {
        this.hotelRepository = hotelRepository;
        this.poiRepository = poiRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Hotel findById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Hotel no encontrado: " + id));
    }

    public List<NearbyPlaceDto> getNearbyPois(Hotel hotel, int limit) {
        List<Poi> pois = poiRepository.findByCiudadIgnoreCase(hotel.getCiudad());
        return pois.stream()
                .filter(p -> p.getLatitud() != null && p.getLongitud() != null)
                .map(p -> new NearbyPlaceDto(
                        p.getId(),
                        p.getNombre(),
                        p.getLatitud(),
                        p.getLongitud(),
                        GeoUtils.distanceKm(hotel.getLatitud(), hotel.getLongitud(), p.getLatitud(), p.getLongitud()),
                        "POI"
                ))
                .sorted(Comparator.comparingDouble(NearbyPlaceDto::getDistanceKm))
                .limit(limit)
                .toList();
    }

    public List<NearbyPlaceDto> getNearbyRestaurants(Hotel hotel, int limit) {
        List<Restaurant> restaurants = restaurantRepository.findByCityIgnoreCase(hotel.getCiudad());
        return restaurants.stream()
                .filter(r -> r.getLatitud() != null && r.getLongitud() != null)
                .map(r -> new NearbyPlaceDto(
                        r.getId(),
                        r.getName(),
                        r.getLatitud(),
                        r.getLongitud(),
                        GeoUtils.distanceKm(hotel.getLatitud(), hotel.getLongitud(), r.getLatitud(), r.getLongitud()),
                        "RESTAURANT"
                ))
                .sorted(Comparator.comparingDouble(NearbyPlaceDto::getDistanceKm))
                .limit(limit)
                .toList();
    }
}