package com.ams.developer.pizza.service.mapper;

import com.ams.developer.pizza.persitence.entity.OrderItemEntity;
import com.ams.developer.pizza.service.dto.OrderItemDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {
    @Autowired
    private ModelMapper modelMapper;

    public OrderItemDto convertOrderItemDto(OrderItemEntity orderItemEntity){
        return this.modelMapper.map(orderItemEntity, OrderItemDto.class);
    }

    public OrderItemEntity convertOrderItemEntity(OrderItemDto orderItemDto){
        return this.modelMapper.map(orderItemDto, OrderItemEntity.class);
    }
}
