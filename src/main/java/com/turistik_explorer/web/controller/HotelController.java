package com.turistik_explorer.web.controller;

import com.turistik_explorer.model.Hotel;

import com.turistik_explorer.service.hotel.HotelService;
import com.turistik_explorer.web.dto.NearbyPlaceDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/hotel/{id}")
    public String hotelDetail(@PathVariable Long id, Model model) {
        Hotel hotel = hotelService.findById(id);

        // Obtenemos los lugares cercanos a través del servicio
        List<NearbyPlaceDto> nearbyPois = hotelService.getNearbyPois(hotel, 5);
        List<NearbyPlaceDto> nearbyRestaurants = hotelService.getNearbyRestaurants(hotel, 5);

        model.addAttribute("hotel", hotel);
        model.addAttribute("nearbyPois", nearbyPois);
        model.addAttribute("nearbyRestaurants", nearbyRestaurants);

        return "pages/hotel-detail";
    }
}