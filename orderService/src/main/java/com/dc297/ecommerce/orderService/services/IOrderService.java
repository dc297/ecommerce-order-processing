package com.dc297.ecommerce.orderService.services;

import com.dc297.ecommerce.entities.Order;
import com.dc297.ecommerce.orderService.dtos.OrderDto;

import java.util.List;
import java.util.UUID;

public interface IOrderService {
    OrderDto create(OrderDto order);
    List<OrderDto> findAll();
    OrderDto getById(UUID id);
    void deleteById(UUID id);
    void cancel(UUID id);
}
