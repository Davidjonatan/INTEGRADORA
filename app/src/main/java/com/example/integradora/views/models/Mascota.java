package com.example.integradora.views.models;

import java.io.Serializable;

public class Mascota implements Serializable {
    private int id;
    private String nombre;
    private String animal;
    private int usuario_id;
    private Integer comidasDiarias;

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public Integer getComidasDiarias() {
        return comidasDiarias;
    }

    public void setComidasDiarias(Integer comidasDiarias) {
        this.comidasDiarias = comidasDiarias;
    }
}

