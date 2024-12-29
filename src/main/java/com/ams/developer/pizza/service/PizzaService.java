package com.ams.developer.pizza.service;
import com.ams.developer.pizza.persitence.entity.PizzaEntity;
import com.ams.developer.pizza.service.dto.ApiResponseDto;
import com.ams.developer.pizza.service.dto.PizzaDto;
import org.springframework.validation.BindingResult;


public interface PizzaService {
    //List<PizzaDto> getAllPizzas(int page, int size, String sortBy);

    ApiResponseDto getAllPizzas(int page, int size, String sortBy);

    ApiResponseDto getPizza(int idPizza);

    PizzaEntity getPizzaById(int idPizza);

    ApiResponseDto savePizza(PizzaDto pizzaDto, BindingResult bindingResult);

    ApiResponseDto updatePizza(int idPizza, PizzaDto pizzaDto, BindingResult bindingResult);

    ApiResponseDto deletePizza(int idPizza);
}
