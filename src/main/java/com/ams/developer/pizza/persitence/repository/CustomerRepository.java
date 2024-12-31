package com.ams.developer.pizza.persitence.repository;

import com.ams.developer.pizza.persitence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity,String> {
}
