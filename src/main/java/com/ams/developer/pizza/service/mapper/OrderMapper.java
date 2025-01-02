package com.ams.developer.pizza.service.mapper;

import com.ams.developer.pizza.persitence.entity.PizzaOrderEntity;
import com.ams.developer.pizza.service.dto.PizzaOrderDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    @Autowired
    private ModelMapper modelMapper;

    public PizzaOrderDto convertOrderDto(PizzaOrderEntity pizzaOrderEntity){
        return this.modelMapper.map(pizzaOrderEntity, PizzaOrderDto.class);
    }

    public PizzaOrderEntity convertOrderEntity(PizzaOrderDto pizzaOrderDto){
        return this.modelMapper.map(pizzaOrderDto, PizzaOrderEntity.class);
    }

}
