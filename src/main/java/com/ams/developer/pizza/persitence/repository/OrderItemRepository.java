package com.ams.developer.pizza.persitence.repository;

import com.ams.developer.pizza.persitence.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity,Integer> {

    @Query(value = "SELECT * FROM public.order_item order by id_item desc LIMIT 1", nativeQuery = true)
    Optional<OrderItemEntity> findLastItemOrder();
}
