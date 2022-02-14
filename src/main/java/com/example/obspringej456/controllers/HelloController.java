package com.example.obspringej456.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Value("${app.status}")
    private String status;

    @GetMapping("/api/hola")
    public String saludo() {
        String s = """
                <h1>Hola Mundo!</h1>
                <p>
                La aplicación se encuentra en el siguiente estado:
                </p>
                <p>
                API status -> %s
                </p>
                """;
        return String.format(s, status);
    }
}
