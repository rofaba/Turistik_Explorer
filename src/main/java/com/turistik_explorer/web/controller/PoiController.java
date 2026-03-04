package com.turistik_explorer.web.controller;


import com.turistik_explorer.model.Hotel;
import com.turistik_explorer.model.Poi;
import com.turistik_explorer.model.Restaurant;
import com.turistik_explorer.repository.HotelRepository;
import com.turistik_explorer.repository.PoiRepository;
import com.turistik_explorer.repository.RestaurantRepository;
import com.turistik_explorer.web.dto.NearbyPlaceDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PoiController {

    private final PoiRepository poiRepository;
    private final HotelRepository hotelRepository;
    private final RestaurantRepository restaurantRepository;

    public PoiController(PoiRepository poiRepository, HotelRepository hotelRepository, RestaurantRepository restaurantRepository) {
        this.poiRepository = poiRepository;
        this.hotelRepository = hotelRepository;
        this.restaurantRepository = restaurantRepository;
    }

    private static double distanceKm(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371.0;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        return 2 * R * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    @GetMapping("/poi/{id}")
    public String poiDetail(@PathVariable Long id, Model model) {

        Poi poi = poiRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("POI no encontrado: " + id));

        String city = poi.getCiudad();

        // Trae de la misma ciudad (rápido). Si tu repo de Restaurant es findByCityIgnoreCase ya lo tienes.
        List<Hotel> hotels = hotelRepository.findByCiudadIgnoreCase(city);
        List<Restaurant> restaurants = restaurantRepository.findByCityIgnoreCase(city);

        // Top 5 hoteles cercanos
        List<NearbyPlaceDto> nearbyHotels = hotels.stream()
                .filter(h -> h.getLatitud() != null && h.getLongitud() != null)
                .map(h -> new NearbyPlaceDto(
                        h.getId(),
                        h.getNombre(),
                        h.getLatitud(),
                        h.getLongitud(),
                        distanceKm(poi.getLatitud(), poi.getLongitud(), h.getLatitud(), h.getLongitud()),
                        "HOTEL"
                ))
                .sorted((a, b) -> Double.compare(a.getDistanceKm(), b.getDistanceKm()))
                .limit(5)
                .toList();

        // Top 5 restaurantes cercanos
        List<NearbyPlaceDto> nearbyRestaurants = restaurants.stream()
                .filter(r -> r.getLatitud() != null && r.getLongitud() != null)
                .map(r -> new NearbyPlaceDto(
                        r.getId(),
                        r.getName(),
                        r.getLatitud(),
                        r.getLongitud(),
                        distanceKm(poi.getLatitud(), poi.getLongitud(), r.getLatitud(), r.getLongitud()),
                        "RESTAURANT"
                ))
                .sorted((a, b) -> Double.compare(a.getDistanceKm(), b.getDistanceKm()))
                .limit(5)
                .toList();

        model.addAttribute("poi", poi);
        model.addAttribute("nearbyHotels", nearbyHotels);
        model.addAttribute("nearbyRestaurants", nearbyRestaurants);

        return "pages/poi-detail";
    }
}