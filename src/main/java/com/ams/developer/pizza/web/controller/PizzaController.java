package com.ams.developer.pizza.web.controller;

import com.ams.developer.pizza.persitence.entity.PizzaEntity;
import com.ams.developer.pizza.service.PizzaService;
import com.ams.developer.pizza.service.dto.ApiResponseDto;
import com.ams.developer.pizza.service.dto.PizzaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaController {

    @Autowired
    private  PizzaService pizzaService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponseDto> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy) {

        ApiResponseDto response = pizzaService.getAllPizzas(page, size, sortBy);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @GetMapping("/{idPizza}")
    public ResponseEntity<ApiResponseDto> getPizza(@PathVariable int idPizza){
        ApiResponseDto response = this.pizzaService.getPizza(idPizza);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PostMapping(value = "/save")
    public ResponseEntity<ApiResponseDto> savePizza(@ModelAttribute PizzaDto pizzaDto, BindingResult bindingResult){
        ApiResponseDto response = this.pizzaService.savePizza(pizzaDto,bindingResult);
        return new ResponseEntity<>(response,HttpStatus.valueOf(response.getStatusCode()));
    }

    @PutMapping("/update/{idPizza}")
    public ResponseEntity<ApiResponseDto> updatePizza(@PathVariable int idPizza, @ModelAttribute PizzaDto pizzaDto, BindingResult bindingResult){
        ApiResponseDto response = this.pizzaService.updatePizza(idPizza, pizzaDto, bindingResult);
        return new ResponseEntity<>(response,HttpStatus.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/delete/{idPizza}")
    public ResponseEntity<ApiResponseDto> deletePizza(@PathVariable int idPizza){
        ApiResponseDto response = this.pizzaService.deletePizza(idPizza);
        return new ResponseEntity<>(response,HttpStatus.valueOf(response.getStatusCode()));
    }
}
