package com.ams.developer.pizza.service.dto;

public class ApiResponseDto {
    private Integer statusCode;
    private String description;
    private Object information;

    public ApiResponseDto() {
    }

    public ApiResponseDto(Integer statusCode, String description, Object information) {
        this.statusCode = statusCode;
        this.description = description;
        this.information = information;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getInformation() {
        return information;
    }

    public void setInformation(Object information) {
        this.information = information;
    }
}
