package com.turistik_explorer.service.weather;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Locale;

@Service
public class OpenWeatherService implements WeatherService {

    private final WebClient webClient;

    @Value("${api.weather.key}")
    private String apiKey;

    public OpenWeatherService(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://api.openweathermap.org")
                .build();
    }

    @Override
    public WeatherInfo getCurrentWeather(String city) {
        // OpenWeather acepta "q=Malaga,ES" (mejor para evitar homónimos)
        String q = city.trim() + ",ES";

        OpenWeatherResponse res = webClient.get()
                .uri(uri -> uri.path("/data/2.5/weather")
                        .queryParam("q", q)
                        .queryParam("appid", apiKey)
                        .queryParam("units", "metric")
                        .queryParam("lang", "es")
                        .build())
                .retrieve()
                .bodyToMono(OpenWeatherResponse.class)
                .block();

        if (res == null || res.main == null) {
            return null;
        }

        String desc = (res.weather != null && !res.weather.isEmpty() && res.weather.get(0) != null)
                ? capitalize(res.weather.get(0).description)
                : null;

        return new WeatherInfo(
                city,
                res.main.temp,
                desc,
                res.main.humidity,
                res.wind != null ? res.wind.speed : null
        );
    }

    private String capitalize(String s) {
        if (s == null || s.isBlank()) return s;
        s = s.toLowerCase(Locale.ROOT);
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    // ===== DTO interno para mapear JSON =====
    static class OpenWeatherResponse {
        public java.util.List<WeatherItem> weather;
        public Main main;
        public Wind wind;
    }
    static class WeatherItem { public String description; }
    static class Main { public Double temp; public Integer humidity; }
    static class Wind { public Double speed; }
}