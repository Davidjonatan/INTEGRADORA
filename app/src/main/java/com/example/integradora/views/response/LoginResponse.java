package com.example.integradora.views.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    private String message;
    private String token;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }
}
