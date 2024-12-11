package com.example.integradora.views.models;

import android.content.Intent;

public class CrearComederoRequest {
    private Integer mascota_id;

    public CrearComederoRequest(Integer mascota_id) {
        this.mascota_id = mascota_id;
    }

    public Integer getMascota_id() {
        return mascota_id;
    }

    public void setMascota_id(Integer mascota_id) {
        this.mascota_id = mascota_id;
    }
}
