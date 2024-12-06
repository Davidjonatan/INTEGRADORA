package com.example.integradora.views.models;

public class ReactivateRequest {
    String email;

    public ReactivateRequest(String email) { this.email = email; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email;}
}
