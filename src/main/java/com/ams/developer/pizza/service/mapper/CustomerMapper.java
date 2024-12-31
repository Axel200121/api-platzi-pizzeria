package com.ams.developer.pizza.service.mapper;

import com.ams.developer.pizza.persitence.entity.CustomerEntity;
import com.ams.developer.pizza.service.dto.CustomerDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    @Autowired
    private ModelMapper modelMapper;

    public CustomerDto convertCustomerDto(CustomerEntity customerEntity){
        return this.modelMapper.map(customerEntity,CustomerDto.class);
    }

    public CustomerEntity convertCustomerEntity(CustomerDto customerDto){
        return this.modelMapper.map(customerDto, CustomerEntity.class);
    }

}
