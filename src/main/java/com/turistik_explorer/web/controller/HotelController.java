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
public class HotelController {

    private final HotelRepository hotelRepository;
    private final PoiRepository poiRepository;
    private final RestaurantRepository restaurantRepository;

    public HotelController(HotelRepository hotelRepository,
                           PoiRepository poiRepository,
                           RestaurantRepository restaurantRepository) {
        this.hotelRepository = hotelRepository;
        this.poiRepository = poiRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/hotel/{id}")
    public String hotelDetail(@PathVariable Long id, Model model) {

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Hotel no encontrado: " + id));

        String city = hotel.getCiudad();

        List<Poi> pois = poiRepository.findByCiudadIgnoreCase(city);
        List<Restaurant> restaurants = restaurantRepository.findByCityIgnoreCase(city);

        List<NearbyPlaceDto> nearbyPois = pois.stream()
                .filter(p -> p.getLatitud() != null && p.getLongitud() != null)
                .map(p -> new NearbyPlaceDto(
                        p.getId(),
                        p.getNombre(),
                        p.getLatitud(),
                        p.getLongitud(),
                        distanceKm(hotel.getLatitud(), hotel.getLongitud(), p.getLatitud(), p.getLongitud()),
                        "POI"
                ))
                .sorted((a,b) -> Double.compare(a.getDistanceKm(), b.getDistanceKm()))
                .limit(5)
                .toList();

        List<NearbyPlaceDto> nearbyRestaurants = restaurants.stream()
                .filter(r -> r.getLatitud() != null && r.getLongitud() != null)
                .map(r -> new NearbyPlaceDto(
                        r.getId(),
                        r.getName(),
                        r.getLatitud(),
                        r.getLongitud(),
                        distanceKm(hotel.getLatitud(), hotel.getLongitud(), r.getLatitud(), r.getLongitud()),
                        "RESTAURANT"
                ))
                .sorted((a,b) -> Double.compare(a.getDistanceKm(), b.getDistanceKm()))
                .limit(5)
                .toList();

        model.addAttribute("hotel", hotel);
        model.addAttribute("nearbyPois", nearbyPois);
        model.addAttribute("nearbyRestaurants", nearbyRestaurants);

        return "pages/hotel-detail";
    }
}