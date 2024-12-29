package com.ams.developer.pizza.persitence.repository;

import com.ams.developer.pizza.persitence.entity.PizzaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PizzaRepository extends JpaRepository<PizzaEntity,Integer> {

    Optional<PizzaEntity> findByNameIgnoreCase(String name);
}
