package com.ams.developer.pizza.service.mapper;

import com.ams.developer.pizza.persitence.entity.PizzaEntity;
import com.ams.developer.pizza.service.dto.PizzaDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PizzaMapper {

    @Autowired
    private ModelMapper modelMapper;

    public PizzaDto convertPizzaDto(PizzaEntity pizzaEntity){
        return this.modelMapper.map(pizzaEntity,PizzaDto.class);
    }

    public PizzaEntity convertPizzaEntity(PizzaDto pizzaDto){
        return this.modelMapper.map(pizzaDto, PizzaEntity.class);
    }
}
