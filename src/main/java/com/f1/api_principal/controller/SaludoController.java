package com.f1.api_principal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaludoController {

    @GetMapping("/hola")
    public String saludar() {
        return "¡Hola Gonzalo! Tu entorno de Java 21 y Spring Boot está funcionando perfectamente.";
    }
}
