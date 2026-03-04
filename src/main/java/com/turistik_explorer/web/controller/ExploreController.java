package com.turistik_explorer.web.controller;

import com.turistik_explorer.model.Hotel;
import com.turistik_explorer.model.Poi;
import com.turistik_explorer.model.Restaurant;
import com.turistik_explorer.repository.HotelRepository;
import com.turistik_explorer.repository.PoiRepository;
import com.turistik_explorer.repository.RestaurantRepository;
import com.turistik_explorer.service.weather.OpenWeatherService;
import com.turistik_explorer.service.weather.WeatherInfo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.text.Normalizer;

import java.util.Locale;


import java.util.List;

@Controller
public class ExploreController {

    private final PoiRepository poiRepository;
    private final HotelRepository hotelRepository;
    private final RestaurantRepository restaurantRepository;
    private final OpenWeatherService weatherService;

    public ExploreController(PoiRepository poiRepository,
                             HotelRepository hotelRepository,
                             RestaurantRepository restaurantRepository,
                             OpenWeatherService weatherService) {
        this.poiRepository = poiRepository;
        this.hotelRepository = hotelRepository;
        this.restaurantRepository = restaurantRepository;
        this.weatherService = weatherService;
    }

    private static String norm(String s) {
        if (s == null) return "";
        String n = Normalizer.normalize(s.trim(), Normalizer.Form.NFD);
        n = n.replaceAll("\\p{M}", ""); // quita diacríticos
        return n.toLowerCase(Locale.ROOT);
    }

    @GetMapping("/explore")
    public String explore(@RequestParam("city") String city, Model model) {

        List<Poi> pois = poiRepository.findByCiudadIgnoreCase(city);
        List<Hotel> hotels = hotelRepository.findByCiudadIgnoreCase(city);
        List<Restaurant> restaurants = restaurantRepository.findByCityIgnoreCase(city);

        // Debug rápido (puedes borrarlo luego)
        System.out.println("CITY=" + city
                + " | POIS=" + pois.size()
                + " | HOTELS=" + hotels.size()
                + " | RESTAURANTS=" + restaurants.size());

        // Fallback por acentos / espacios (solo si salen vacíos)
        String cityNorm = norm(city);

        if (hotels.isEmpty()) {
            hotels = hotelRepository.findAll().stream()
                    .filter(h -> norm(h.getCiudad()).equals(cityNorm))
                    .toList();
        }

        if (restaurants.isEmpty()) {
            restaurants = restaurantRepository.findAll().stream()
                    .filter(r -> norm(r.getCity()).equals(cityNorm))
                    .toList();
        }

        model.addAttribute("city", city);
        model.addAttribute("pois", pois);
        model.addAttribute("hotels", hotels);
        model.addAttribute("restaurants", restaurants);

        WeatherInfo weather = weatherService.getCurrentWeather(city);
        model.addAttribute("weather", weather);

        return "pages/explore";
    }
}