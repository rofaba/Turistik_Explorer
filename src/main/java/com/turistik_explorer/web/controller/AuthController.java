package com.turistik_explorer.web.controller;

/*
 * Controlador para manejar las rutas de autenticación, como el inicio de sesión.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "pages/login";
    }

}