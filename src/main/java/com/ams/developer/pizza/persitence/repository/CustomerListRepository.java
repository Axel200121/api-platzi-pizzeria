package com.ams.developer.pizza.persitence.repository;

import com.ams.developer.pizza.persitence.entity.CustomerEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerListRepository extends PagingAndSortingRepository<CustomerEntity,String> {
}
