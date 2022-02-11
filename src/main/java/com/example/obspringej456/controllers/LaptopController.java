package com.example.obspringej456.controllers;

import com.example.obspringej456.entities.Laptop;
import com.example.obspringej456.repositories.LaptopRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
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
    public ResponseEntity<Laptop> findById(@PathVariable Long id){
        Optional<Laptop> laptop = repo.findById(id);
        if(laptop.isPresent())
            return ResponseEntity.ok(laptop.get());
        else
            return ResponseEntity.notFound().build();
    }

    // añadir un portatil
    @PostMapping("/api/laptops")
    public ResponseEntity<Laptop> create(@RequestBody Laptop laptop){
        if(laptop.getId() != null){
            return ResponseEntity.badRequest().build();
        }

        Laptop newLaptop = laptop;
        repo.save(newLaptop);
        return ResponseEntity.ok(newLaptop);
    }

    // actualizar un portatil

    @PutMapping("/api/laptops/{id}")
    public ResponseEntity<Laptop> update(@PathVariable Long id, @RequestBody Laptop toUpdateLaptop){
        // comprobamos que la petición se ha hecho con un id correcto, y que existe un portatil con ese id.
        if(id <= 0){
            return ResponseEntity.badRequest().build();
        }
        if(!repo.existsById(id)){
            return  ResponseEntity.notFound().build();
        }
        // comprobamos que no se ha especificado el id en el cuerpo de la peticion, y que si se ha especificado
        // este coincide con el id de la url.
        if(toUpdateLaptop.getId() == null){
            toUpdateLaptop.setId(id);
        }
        if(!Objects.equals(toUpdateLaptop.getId(), id)){
            return ResponseEntity.badRequest().build();
        } else {
            // Una vez comprobamos que ambos id coinciden, guardamos la nueva version del objeto y devolvemos el nuevo
            // objeto y estado 200.
            repo.save(toUpdateLaptop);
            return ResponseEntity.ok(toUpdateLaptop);
        }
    }

    // Implementación del método delete para borrar un portatil por id
    @DeleteMapping("/api/laptops/{id}")
    public ResponseEntity<Laptop> delete(@PathVariable Long id){
        if(id < 0){
            return ResponseEntity.badRequest().build();
        }
        if(repo.existsById(id)){
            repo.deleteById(id);
            return  ResponseEntity.noContent().build();
        }


        return ResponseEntity.notFound().build();
    }

    // Implementación del metodo deleteAll para borrar todos los portatiles
    @DeleteMapping("/api/laptops")
    public ResponseEntity<Laptop> deleteAll(){
        if(repo.count() == 0){
            return ResponseEntity.badRequest().build();
        } else {
            repo.deleteAll();
            return ResponseEntity.noContent().build();
        }


    }
}
