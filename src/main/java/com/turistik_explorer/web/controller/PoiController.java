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
/**
 * Controlador para manejar las vistas relacionadas con los Puntos de Interés (POIs).
 * Permite mostrar detalles de un POI, así como agregar nuevos POIs desde el panel de administración.
 */

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
    // ==========================================
    // 1. READ: Mostrar el panel de control (Lista)
    // ==========================================
    @GetMapping("/admin/pois")
    public String listPois(Model model) {
        model.addAttribute("pois", poiService.findAll());
        return "admin/list-pois";
    }

    // 2. CREATE: Cargar formulario vacío
    @GetMapping("/admin/add-poi")
    public String addPoiForm(Model model) {
        model.addAttribute("poi", new Poi());
        return "admin/poi-form"; // <--- CAMBIO AQUÍ
    }

    // 3. UPDATE: Cargar formulario con datos existentes
    @GetMapping("/admin/edit-poi/{id}")
    public String editPoiForm(@PathVariable Long id, Model model) {
        Poi poi = poiService.findById(id);
        model.addAttribute("poi", poi);
        return "admin/poi-form"; // <--- CAMBIO AQUÍ
    }

    // 4. SAVE: Guarda (Crea o Actualiza) y redirige al panel
    @PostMapping("/admin/add-poi")
    public String savePoi(@Valid @ModelAttribute("poi") Poi poi, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/poi-form"; // <--- CAMBIO AQUÍ
        }

        poi.setTipo(PlaceType.POI);
        poiService.save(poi);
        return "redirect:/admin/pois";
    }

    // ==========================================
    // 5. DELETE: Borrar un POI
    // ==========================================
    @PostMapping("/admin/delete-poi/{id}")
    public String deletePoi(@PathVariable Long id) {
        poiService.deleteById(id);
        return "redirect:/admin/pois";
    }
}