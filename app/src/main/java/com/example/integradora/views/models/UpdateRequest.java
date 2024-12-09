package com.example.integradora.views.models;

public class UpdateRequest {
    private String name;
    private String old_password;
    private String new_password;
    private String new_password_confirmation;

    public UpdateRequest(String name, String oldPassword, String newPassword, String newPasswordConfirmation) {
        this.name = name;
        this.old_password = oldPassword;
        this.new_password = newPassword;
        this.new_password_confirmation = newPasswordConfirmation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public String getNew_password_confirmation() {
        return new_password_confirmation;
    }

    public void setNew_password_confirmation(String new_password_confirmation) {
        this.new_password_confirmation = new_password_confirmation;
    }
}
