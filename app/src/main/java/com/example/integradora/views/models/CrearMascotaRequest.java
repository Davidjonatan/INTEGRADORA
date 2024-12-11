package com.example.integradora.views.models;

public class CrearMascotaRequest {
    private String nombre;
    private String animal;

    public CrearMascotaRequest(String nombre, String animal) {
        this.nombre = nombre;
        this.animal = animal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }
}
