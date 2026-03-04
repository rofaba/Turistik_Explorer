package com.turistik_explorer.service.weather;

public record WeatherInfo(
        String city,
        Double tempC,
        String description,
        Integer humidity,
        Double windSpeed
) {}