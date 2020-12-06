package com.dc297.ecommerce.orderService.repositories;

import com.dc297.ecommerce.entities.OrderPricing;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface OrderPricingRepository extends CrudRepository<OrderPricing, UUID> {
    List<OrderPricing> findAll();
}
