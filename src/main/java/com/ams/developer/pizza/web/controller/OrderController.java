package com.ams.developer.pizza.web.controller;

import com.ams.developer.pizza.service.OrderService;
import com.ams.developer.pizza.service.dto.ApiResponseDto;
import com.ams.developer.pizza.service.dto.CustomerDto;
import com.ams.developer.pizza.service.dto.PizzaOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponseDto> getAllorders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "idOrder") String sortBy
    ){
        ApiResponseDto response = this.orderService.getAllOrders(page,size,sortBy);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/get/{idOrder}")
    public ResponseEntity<ApiResponseDto> getOrder(@PathVariable int idOrder){
        ApiResponseDto response = this.orderService.getOrder(idOrder);
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponseDto> saveCustomer(@RequestBody PizzaOrderDto pizzaOrderDto, BindingResult bindingResult){
        ApiResponseDto response = this.orderService.saveOrder(pizzaOrderDto, bindingResult);
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PutMapping("/update/{idOrder}")
    public ResponseEntity<ApiResponseDto> updateCustomer(@PathVariable int idOrder, @RequestBody PizzaOrderDto pizzaOrderDto, BindingResult bindingResult){
        ApiResponseDto response = this.orderService.updateOrder(idOrder,pizzaOrderDto,bindingResult);
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/delete/{idOrder}")
    public ResponseEntity<ApiResponseDto> deleteOrder(@PathVariable int idOrder){
        ApiResponseDto response = this.orderService.deleteOrder(idOrder);
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }

}
