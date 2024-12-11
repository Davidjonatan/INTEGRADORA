package com.example.integradora.views.response;

import com.example.integradora.views.models.Mascota;

import java.util.List;

public class MascotaResponse {
    private String message;
    private List<com.example.integradora.views.models.Mascota> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<com.example.integradora.views.models.Mascota> getData() {
        return data;
    }

    public void setData(List<com.example.integradora.views.models.Mascota> data) {
        this.data = data;
    }

    public static class Mascota {
        private int id;
        private String nombre;
        private String animal;

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
    }
}
