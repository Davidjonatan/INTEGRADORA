package com.example.integradora.views.response;

import java.util.List;
import java.util.Map;

public class CrearComederoResponse {
    private String message;
    private Map<String, List<String>> validator;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, List<String>> getValidator() {
        return validator;
    }

    public void setValidator(Map<String, List<String>> validator) {
        this.validator = validator;
    }
}
