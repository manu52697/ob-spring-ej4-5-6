package com.example.obspringej456.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/api/hola")
    public String saludo() {
        return "Hola Mundo";
    }
}
