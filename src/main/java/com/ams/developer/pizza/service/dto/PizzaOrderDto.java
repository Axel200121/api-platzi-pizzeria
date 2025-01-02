package com.ams.developer.pizza.service.dto;

import com.ams.developer.pizza.persitence.entity.OrderItemEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public class PizzaOrderDto {

    private Integer idOrder;

    @NotNull(message = "El identificador del cliente no puede ser nulo")
    @NotBlank(message = "El identificador del cliente no puede estar vacio")
    private String idCustomer;

    private LocalDateTime date;

    private Double total;

    @NotNull(message = "El tipo de orden no puede ser nulo")
    @NotBlank(message = "El tipo de orden no puede estar vacio")
    private String method;

    private String addtionalNotes;

    private List<OrderItemDto> items;

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAddtionalNotes() {
        return addtionalNotes;
    }

    public void setAddtionalNotes(String addtionalNotes) {
        this.addtionalNotes = addtionalNotes;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }
}
