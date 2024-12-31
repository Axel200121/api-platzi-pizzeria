package com.ams.developer.pizza.service;

import com.ams.developer.pizza.persitence.entity.CustomerEntity;
import com.ams.developer.pizza.service.dto.ApiResponseDto;
import com.ams.developer.pizza.service.dto.CustomerDto;
import org.springframework.validation.BindingResult;

public interface CustomerService {

    ApiResponseDto getAllCustomers(int page, int size, String sortBy);

    ApiResponseDto getCustomer(String idCustomer);

    CustomerEntity getCustomerById(String idCustomer);

    ApiResponseDto saveCustomer(CustomerDto customerDto, BindingResult bindingResult);

    ApiResponseDto updateCustomer(String idCustomer, CustomerDto customerDto, BindingResult bindingResult);

    ApiResponseDto deleteCustomer(String idCustomer);
}
