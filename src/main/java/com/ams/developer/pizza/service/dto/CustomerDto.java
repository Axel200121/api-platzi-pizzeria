package com.ams.developer.pizza.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CustomerDto {

    private String idCustomer;

    @NotNull(message = "El nombre no puede ser nulo")
    @NotBlank(message = "El nombe no puede estar vacio")
    private String name;

    @NotNull(message = "La dirección no puede ser nulo")
    @NotBlank(message = "La dirección no puede estar vacio")
    private String address;

    @NotNull(message = "El correo electronico no puede ser nulo")
    @NotBlank(message = "El correo electronico  no puede estar vacio")
    private String email;

    @NotNull(message = "El numero telefonico no puede ser nulo")
    @NotBlank(message = "El numero telefonico no puede estar vacio")
    private String phoneNumber;

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "idCustomer='" + idCustomer + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
