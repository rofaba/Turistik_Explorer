package com.turistik_explorer.service.poi;

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

@Service
public class PoiService {

    private final PoiRepository poiRepository;
    private final HotelRepository hotelRepository;
    private final RestaurantRepository restaurantRepository;

    public PoiService(PoiRepository poiRepository,
                      HotelRepository hotelRepository,
                      RestaurantRepository restaurantRepository) {
        this.poiRepository = poiRepository;
        this.hotelRepository = hotelRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Poi findById(Long id) {
        return poiRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("POI no encontrado: " + id));
    }

    public List<NearbyPlaceDto> getNearbyHotels(Poi poi, int limit) {
        List<Hotel> hotels = hotelRepository.findByCiudadIgnoreCase(poi.getCiudad());
        return hotels.stream()
                .filter(h -> h.getLatitud() != null && h.getLongitud() != null)
                .map(h -> new NearbyPlaceDto(
                        h.getId(),
                        h.getNombre(),
                        h.getLatitud(),
                        h.getLongitud(),
                        GeoUtils.distanceKm(poi.getLatitud(), poi.getLongitud(), h.getLatitud(), h.getLongitud()),
                        "HOTEL"
                ))
                .sorted((a, b) -> Double.compare(a.getDistanceKm(), b.getDistanceKm()))
                .limit(limit)
                .toList();
    }

    public List<NearbyPlaceDto> getNearbyRestaurants(Poi poi, int limit) {
        List<Restaurant> restaurants = restaurantRepository.findByCityIgnoreCase(poi.getCiudad());
        return restaurants.stream()
                .filter(r -> r.getLatitud() != null && r.getLongitud() != null)
                .map(r -> new NearbyPlaceDto(
                        r.getId(),
                        r.getName(),
                        r.getLatitud(),
                        r.getLongitud(),
                        GeoUtils.distanceKm(poi.getLatitud(), poi.getLongitud(), r.getLatitud(), r.getLongitud()),
                        "RESTAURANT"
                ))
                .sorted((a, b) -> Double.compare(a.getDistanceKm(), b.getDistanceKm()))
                .limit(limit)
                .toList();
    }

    public void save(Poi poi) {
        poiRepository.save(poi);
    }
}