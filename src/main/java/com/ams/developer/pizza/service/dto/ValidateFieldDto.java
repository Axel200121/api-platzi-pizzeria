package com.ams.developer.pizza.service.dto;

public class ValidateFieldDto {
    private String fieldValidated;
    private String fieldValidatedMessage;

    public String getFieldValidated() {
        return fieldValidated;
    }

    public void setFieldValidated(String fieldValidated) {
        this.fieldValidated = fieldValidated;
    }

    public String getFieldValidatedMessage() {
        return fieldValidatedMessage;
    }

    public void setFieldValidatedMessage(String fieldValidatedMessage) {
        this.fieldValidatedMessage = fieldValidatedMessage;
    }
}
