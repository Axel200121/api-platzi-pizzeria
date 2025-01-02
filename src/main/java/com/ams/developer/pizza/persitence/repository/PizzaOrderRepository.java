package com.ams.developer.pizza.persitence.repository;

import com.ams.developer.pizza.persitence.entity.PizzaOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaOrderRepository extends JpaRepository<PizzaOrderEntity,Integer> {
}
