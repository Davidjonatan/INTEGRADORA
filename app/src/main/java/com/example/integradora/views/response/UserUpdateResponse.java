package com.example.integradora.views.response;

import com.google.gson.JsonElement;

import java.util.List;
import java.util.Map;

public class UserUpdateResponse {
    private Validator validator;
    private String message;

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Validator {
        private List<String> name;
        private List<String> old_password;
        private List<String> new_password;
        private List<String> new_password_confirmation;

        public List<String> getName() {
            return name;
        }

        public void setName(List<String> name) {
            this.name = name;
        }

        public List<String> getOld_password() {
            return old_password;
        }

        public void setOld_password(List<String> old_password) {
            this.old_password = old_password;
        }

        public List<String> getNew_password() {
            return new_password;
        }

        public void setNew_password(List<String> new_password) {
            this.new_password = new_password;
        }

        public List<String> getNew_password_confirmation() {
            return new_password_confirmation;
        }

        public void setNew_password_confirmation(List<String> new_password_confirmation) {
            this.new_password_confirmation = new_password_confirmation;
        }
    }
}


