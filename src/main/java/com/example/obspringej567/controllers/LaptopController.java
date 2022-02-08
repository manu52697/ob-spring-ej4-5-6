package com.example.obspringej567.controllers;

import com.example.obspringej567.entities.Laptop;
import com.example.obspringej567.repositories.LaptopRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class LaptopController {

    // Declaramos el repositorio
    private LaptopRepository repo;

    // Constructor

    public LaptopController(LaptopRepository repo) {
        this.repo = repo;
    }

    // CRUD

    // devolver todos los portatiles almacenados

    @GetMapping("/api/laptops")
    public ResponseEntity<List<Laptop>> findAll(){
        if(repo.count()>0)
            return ResponseEntity.ok(repo.findAll());
        else
            return ResponseEntity.notFound().build();
    }

    // recuperar un portatil segun el id
    @GetMapping("/api/laptops/{id}")
    public ResponseEntity<Laptop> findById(@RequestBody Long id){
        Optional<Laptop> laptop = repo.findById(id);
        if(laptop.isPresent())
            return ResponseEntity.ok(laptop.get());
        else
            return ResponseEntity.notFound().build();
    }

    // añadir un portatil
    @PostMapping("/api/laptops")
    public ResponseEntity<Laptop> create(@RequestBody Laptop laptop){
        Laptop newLaptop = laptop;
        repo.save(newLaptop);
        return ResponseEntity.ok(newLaptop);
    }

}
