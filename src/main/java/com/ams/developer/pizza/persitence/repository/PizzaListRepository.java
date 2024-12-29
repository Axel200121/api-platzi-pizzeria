package com.ams.developer.pizza.persitence.repository;

import com.ams.developer.pizza.persitence.entity.PizzaEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PizzaListRepository extends PagingAndSortingRepository<PizzaEntity,Integer> {
}
