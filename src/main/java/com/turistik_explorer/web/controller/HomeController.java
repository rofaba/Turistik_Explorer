package com.turistik_explorer.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
/**
 * Controller for handling home page requests.
 */

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("cities", List.of("Málaga", "Granada", "Sevilla", "Cádiz"));
        return "pages/home";
    }
}