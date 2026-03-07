package com.turistik_explorer.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 1. Atrapa nuestros NotFoundException y muestra la brújula (404)
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NotFoundException ex) {
        return "error/404";
    }

    // 2. Atrapa CUALQUIER OTRA excepción no controlada y muestra los engranajes (500)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(Exception ex, Model model) {
        // Pasamos el mensaje de error a la vista para saber qué falló
        model.addAttribute("message", ex.getMessage());
        return "error/500";
    }
}