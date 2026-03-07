package com.turistik_explorer.service.weather;

/**
 * Interface for fetching weather information.
 */

public interface WeatherService {
    WeatherInfo getCurrentWeather(String city);
}