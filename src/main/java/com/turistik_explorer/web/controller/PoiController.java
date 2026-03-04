package com.turistik_explorer.web.controller;

import com.turistik_explorer.exception.NotFoundException;
import com.turistik_explorer.model.Poi;
import com.turistik_explorer.repository.PoiRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PoiController {

    private final PoiRepository poiRepository;

    public PoiController(PoiRepository poiRepository) {
        this.poiRepository = poiRepository;
    }

    @GetMapping("/poi/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {

        Poi poi = poiRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("POI no encontrado: " + id));

        model.addAttribute("poi", poi);
        return "pages/poi-detail";
    }
}