package com.turistik_explorer.web.controller;


import com.turistik_explorer.model.PlaceType;
import com.turistik_explorer.model.Poi;
import com.turistik_explorer.service.poi.PoiService;
import com.turistik_explorer.web.dto.NearbyPlaceDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PoiController {

    private final PoiService poiService;

    public PoiController(PoiService poiService) {
        this.poiService = poiService;
    }

    @GetMapping("/poi/{id}")
    public String poiDetail(@PathVariable Long id, Model model) {
        Poi poi = poiService.findById(id);

        // Delegamos el cálculo de cercanía al servicio
        List<NearbyPlaceDto> nearbyHotels = poiService.getNearbyHotels(poi, 5);
        List<NearbyPlaceDto> nearbyRestaurants = poiService.getNearbyRestaurants(poi, 5);

        model.addAttribute("poi", poi);
        model.addAttribute("nearbyHotels", nearbyHotels);
        model.addAttribute("nearbyRestaurants", nearbyRestaurants);

        return "pages/poi-detail";
    }

    @GetMapping("/admin/add-poi")
    public String addPoiForm(Model model) {
        model.addAttribute("poi", new Poi());
        return "admin/add-poi";
    }

    @PostMapping("/admin/add-poi")
    public String savePoi(@Valid @ModelAttribute("poi") Poi poi, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/add-poi";
        }

        poi.setTipo(PlaceType.POI);
        poiService.save(poi);

        return "redirect:/explore?city=" + poi.getCiudad();
    }
}