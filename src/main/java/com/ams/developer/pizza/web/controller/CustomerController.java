package com.ams.developer.pizza.web.controller;

import com.ams.developer.pizza.service.CustomerService;
import com.ams.developer.pizza.service.dto.ApiResponseDto;
import com.ams.developer.pizza.service.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponseDto> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy
    ){
        ApiResponseDto response = this.customerService.getAllCustomers(page,size,sortBy);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @GetMapping("/get/{idCustomer}")
    public ResponseEntity<ApiResponseDto> getCustomer(@PathVariable String idCustomer){
        ApiResponseDto response = this.customerService.getCustomer(idCustomer);
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponseDto> saveCustomer(@RequestBody CustomerDto customerDto, BindingResult bindingResult){
        ApiResponseDto response = this.customerService.saveCustomer(customerDto, bindingResult);
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @PutMapping("/update/{idCustomer}")
    public ResponseEntity<ApiResponseDto> updateCustomer(@PathVariable String idCustomer, @RequestBody CustomerDto customerDto, BindingResult bindingResult){
        ApiResponseDto response = this.customerService.updateCustomer(idCustomer,customerDto, bindingResult);
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/delete/{idCustomer}")
    public ResponseEntity<ApiResponseDto> deleteCustomer(@PathVariable String idCustomer){
        ApiResponseDto response = this.customerService.deleteCustomer(idCustomer);
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatusCode()));
    }


}
