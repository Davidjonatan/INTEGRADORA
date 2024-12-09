package com.example.integradora.views.models;

import java.text.DecimalFormat;

public class Comedero {
    private int id;
    private String estado;
    private Mascota mascota;
    private String numero_serie;
    private Double temperatua_agua;
    private Double humedad;
    private Double gases;
    private Double cantidad_comida;
    private Double cantidad_agua;
    private Double cantidad_comida_servida;
    private Double cantidad_agua_servida;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public static class Mascota {
        private String nombre;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
    }

    public String getNumero_serie() {
        return numero_serie;
    }

    public void setNumero_serie(String numero_serie) {
        this.numero_serie = numero_serie;
    }

    public Double getTemperatura_agua() {
        return temperatua_agua;
    }

    public void setTemperatura_agua(Double temperatura_agua) {
        this.temperatua_agua = temperatura_agua;
    }

    public Double getHumedad() {
        return humedad;
    }

    public void setHumedad(Double humedad) {
        this.humedad = humedad;
    }

    public Double getGases() {
        return gases;
    }

    public void setGases(Double gases) {
        this.gases = gases;
    }

    public Double getCantidad_comida() {
        return cantidad_comida;
    }

    public void setCantidad_comida(Double cantidad_comida) {
        this.cantidad_comida = cantidad_comida;
    }

    public Double getCantidad_agua() {
        return cantidad_agua;
    }

    public void setCantidad_agua(Double cantidad_agua) {
        this.cantidad_agua = cantidad_agua;
    }

    public Double getCantidad_comida_servida() {
        return cantidad_comida_servida;
    }

    public void setCantidad_comida_servida(Double cantidad_comida_servida) {
        this.cantidad_comida_servida = cantidad_comida_servida;
    }

    public Double getCantidad_agua_servida() {
        return cantidad_agua_servida;
    }

    public void setCantidad_agua_servida(Double cantidad_agua_servida) {
        this.cantidad_agua_servida = cantidad_agua_servida;
    }
}

