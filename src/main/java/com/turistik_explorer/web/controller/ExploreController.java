package com.turistik_explorer.web.controller;

import com.turistik_explorer.model.Poi;
import com.turistik_explorer.repository.PoiRepository;
import com.turistik_explorer.service.weather.WeatherInfo;
import com.turistik_explorer.service.weather.WeatherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ExploreController {

    private final PoiRepository poiRepository;
    private final WeatherService weatherService;

    public ExploreController(PoiRepository poiRepository, WeatherService weatherService) {
        this.poiRepository = poiRepository;
        this.weatherService = weatherService;
    }

    @GetMapping("/explore")
    public String explore(@RequestParam(name = "city", required = false) String city, Model model) {
        if (city == null || city.isBlank()) return "redirect:/";

        List<Poi> pois = poiRepository.findByCiudadIgnoreCase(city);

        WeatherInfo weather = null;
        try {
            weather = weatherService.getCurrentWeather(city);
        } catch (Exception ignored) {
            // si falla la API, la app sigue funcionando
        }

        model.addAttribute("city", city);
        model.addAttribute("pois", pois);
        model.addAttribute("weather", weather);

        return "pages/explore";
    }
}