package com.ams.developer.pizza.persitence.repository;

import com.ams.developer.pizza.persitence.entity.PizzaOrderEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PizzaOrderListRepository extends PagingAndSortingRepository<PizzaOrderEntity,Integer> {
}
