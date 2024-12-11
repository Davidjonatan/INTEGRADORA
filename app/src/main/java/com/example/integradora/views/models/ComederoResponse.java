package com.example.integradora.views.models;

import java.util.List;

public class ComederoResponse {
    List<Comedero> data;
    private String message;

    public List<Comedero> getData() {
        return data;
    }

    public void setData(List<Comedero> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
