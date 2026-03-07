package com.turistik_explorer.web.controller;

import com.turistik_explorer.service.explore.ExploreService;
import com.turistik_explorer.service.weather.OpenWeatherService;
import com.turistik_explorer.service.weather.WeatherInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExploreController {

    private final ExploreService exploreService;
    private final OpenWeatherService weatherService;

    public ExploreController(ExploreService exploreService, OpenWeatherService weatherService) {
        this.exploreService = exploreService;
        this.weatherService = weatherService;
    }

    @GetMapping("/explore")
    public String explore(@RequestParam("city") String city, Model model) {

        // Agregar datos de la base de datos a través del servicio
        model.addAttribute("city", city);
        model.addAttribute("pois", exploreService.getPoisByCity(city));
        model.addAttribute("hotels", exploreService.getHotelsByCity(city));
        model.addAttribute("restaurants", exploreService.getRestaurantsByCity(city));

        // Agregar datos del clima
        WeatherInfo weather = weatherService.getCurrentWeather(city);
        model.addAttribute("weather", weather);

        return "pages/explore";
    }
}