package com.ams.developer.pizza.service;

import com.ams.developer.pizza.persitence.entity.PizzaOrderEntity;
import com.ams.developer.pizza.service.dto.ApiResponseDto;
import com.ams.developer.pizza.service.dto.PizzaOrderDto;
import org.springframework.validation.BindingResult;

public interface OrderService {

    ApiResponseDto getAllOrders(int page, int size, String sortBy);

    ApiResponseDto getOrder(int idOrder);

    PizzaOrderEntity getOrderById(int idOrder);

    ApiResponseDto saveOrder(PizzaOrderDto pizzaOrderDto, BindingResult bindingResult);

    ApiResponseDto updateOrder(int idOrder, PizzaOrderDto pizzaOrderDto, BindingResult bindingResult);

    ApiResponseDto deleteOrder(int idOrder);
}
