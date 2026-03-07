package com.turistik_explorer.service.weather;

/**
 * A record to hold weather information for a city.
 *
 * @param city        The name of the city.
 * @param tempC       The temperature in Celsius.
 * @param description A brief description of the weather (e.g., "clear sky").
 * @param humidity    The humidity percentage.
 * @param windSpeed   The wind speed in meters per second.
 */

public record WeatherInfo(
        String city,
        Double tempC,
        String description,
        Integer humidity,
        Double windSpeed
) {}