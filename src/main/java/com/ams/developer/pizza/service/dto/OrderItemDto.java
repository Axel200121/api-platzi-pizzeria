package com.ams.developer.pizza.service.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class OrderItemDto {

    private Integer idOrderItem;

    private Integer idOrder;

    private Integer idPizza;

    private Double quantity;

    private Double price;

    private PizzaDto pizza;

    private PizzaOrderDto order;

    public Integer getIdOrderItem() {
        return idOrderItem;
    }

    public void setIdOrderItem(Integer idOrderItem) {
        this.idOrderItem = idOrderItem;
    }

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public Integer getIdPizza() {
        return idPizza;
    }

    public void setIdPizza(Integer idPizza) {
        this.idPizza = idPizza;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public PizzaDto getPizza() {
        return pizza;
    }

    public void setPizza(PizzaDto pizza) {
        this.pizza = pizza;
    }

    public PizzaOrderDto getOrder() {
        return order;
    }

    public void setOrder(PizzaOrderDto order) {
        this.order = order;
    }
}
