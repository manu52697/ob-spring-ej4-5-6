package com.example.obspringej567.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Laptop {

    // atributos

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String model;
    private String manufacturer;
    private LocalDate releaseDate;
    private Integer ramSize;
    private String os;

    // constructores


    public Laptop() {
    }

    public Laptop(Long id, String model, String manufacturer, LocalDate releaseDate, Integer ramSize, String os) {
        this.id = id;
        this.model = model;
        this.manufacturer = manufacturer;
        this.releaseDate = releaseDate;
        this.ramSize = ramSize;
        this.os = os;
    }

    // getters y setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getRamSize() {
        return ramSize;
    }

    public void setRamSize(Integer ramSize) {
        this.ramSize = ramSize;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    // tostring


    @Override
    public String toString() {
        return "Laptop{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", releaseDate=" + releaseDate +
                ", ramSize=" + ramSize +
                ", os='" + os + '\'' +
                '}';
    }
}
