package com.turistik_explorer.service.explore;

import com.turistik_explorer.model.Hotel;
import com.turistik_explorer.model.Poi;
import com.turistik_explorer.model.Restaurant;
import com.turistik_explorer.repository.HotelRepository;
import com.turistik_explorer.repository.PoiRepository;
import com.turistik_explorer.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;

/**
 * Servicio para obtener POIs, hoteles y restaurantes por ciudad, con normalización de texto.
 */

@Service
public class ExploreService {

    private final PoiRepository poiRepository;
    private final HotelRepository hotelRepository;
    private final RestaurantRepository restaurantRepository;

    public ExploreService(PoiRepository poiRepository,
                          HotelRepository hotelRepository,
                          RestaurantRepository restaurantRepository) {
        this.poiRepository = poiRepository;
        this.hotelRepository = hotelRepository;
        this.restaurantRepository = restaurantRepository;
    }

    /**
     * Normaliza un texto quitando acentos y pasándolo a minúsculas.
     */
    private String norm(String s) {
        if (s == null) return "";
        String n = Normalizer.normalize(s.trim(), Normalizer.Form.NFD);
        n = n.replaceAll("\\p{M}", "");
        return n.toLowerCase(Locale.ROOT);
    }

    public List<Poi> getPoisByCity(String city) {
        List<Poi> pois = poiRepository.findByCiudadIgnoreCase(city);
        if (pois.isEmpty()) {
            String cityNorm = norm(city);
            return poiRepository.findAll().stream()
                    .filter(p -> norm(p.getCiudad()).equals(cityNorm))
                    .toList();
        }
        return pois;
    }

    public List<Hotel> getHotelsByCity(String city) {
        List<Hotel> hotels = hotelRepository.findByCiudadIgnoreCase(city);
        if (hotels.isEmpty()) {
            String cityNorm = norm(city);
            return hotelRepository.findAll().stream()
                    .filter(h -> norm(h.getCiudad()).equals(cityNorm))
                    .toList();
        }
        return hotels;
    }

    public List<Restaurant> getRestaurantsByCity(String city) {
        List<Restaurant> restaurants = restaurantRepository.findByCityIgnoreCase(city);
        if (restaurants.isEmpty()) {
            String cityNorm = norm(city);
            return restaurantRepository.findAll().stream()
                    .filter(r -> norm(r.getCity()).equals(cityNorm))
                    .toList();
        }
        return restaurants;
    }
}