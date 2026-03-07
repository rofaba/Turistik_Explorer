package com.turistik_explorer.web.controller;


import com.turistik_explorer.model.Restaurant;

import com.turistik_explorer.service.restaurant.RestaurantService;
import com.turistik_explorer.web.dto.NearbyPlaceDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/restaurant/{id}")
    public String restaurantDetail(@PathVariable Long id, Model model) {
        Restaurant restaurant = restaurantService.findById(id);

        List<NearbyPlaceDto> nearbyPois = restaurantService.getNearbyPois(restaurant, 5);
        List<NearbyPlaceDto> nearbyHotels = restaurantService.getNearbyHotels(restaurant, 5);

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("nearbyPois", nearbyPois);
        model.addAttribute("nearbyHotels", nearbyHotels);

        return "pages/restaurant-detail";
    }
}