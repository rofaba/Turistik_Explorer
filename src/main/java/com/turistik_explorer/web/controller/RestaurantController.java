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

import static com.turistik_explorer.util.GeoUtils.distanceKm;

@Controller
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;
    private final PoiRepository poiRepository;
    private final HotelRepository hotelRepository;

    public RestaurantController(RestaurantRepository restaurantRepository,
                                PoiRepository poiRepository,
                                HotelRepository hotelRepository) {
        this.restaurantRepository = restaurantRepository;
        this.poiRepository = poiRepository;
        this.hotelRepository = hotelRepository;
    }

    @GetMapping("/restaurant/{id}")
    public String restaurantDetail(@PathVariable Long id, Model model) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant no encontrado: " + id));

        String city = restaurant.getCity();

        List<Poi> pois = poiRepository.findByCiudadIgnoreCase(city);
        List<Hotel> hotels = hotelRepository.findByCiudadIgnoreCase(city);

        List<NearbyPlaceDto> nearbyPois = pois.stream()
                .filter(p -> p.getLatitud() != null && p.getLongitud() != null)
                .map(p -> new NearbyPlaceDto(
                        p.getId(),
                        p.getNombre(),
                        p.getLatitud(),
                        p.getLongitud(),
                        distanceKm(restaurant.getLatitud(), restaurant.getLongitud(), p.getLatitud(), p.getLongitud()),
                        "POI"
                ))
                .sorted((a,b) -> Double.compare(a.getDistanceKm(), b.getDistanceKm()))
                .limit(5)
                .toList();

        List<NearbyPlaceDto> nearbyHotels = hotels.stream()
                .filter(h -> h.getLatitud() != null && h.getLongitud() != null)
                .map(h -> new NearbyPlaceDto(
                        h.getId(),
                        h.getNombre(),
                        h.getLatitud(),
                        h.getLongitud(),
                        distanceKm(restaurant.getLatitud(), restaurant.getLongitud(), h.getLatitud(), h.getLongitud()),
                        "HOTEL"
                ))
                .sorted((a,b) -> Double.compare(a.getDistanceKm(), b.getDistanceKm()))
                .limit(5)
                .toList();

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("nearbyPois", nearbyPois);
        model.addAttribute("nearbyHotels", nearbyHotels);

        return "pages/restaurant-detail";
    }
}