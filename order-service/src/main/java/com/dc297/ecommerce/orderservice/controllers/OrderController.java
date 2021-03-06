package com.dc297.ecommerce.orderservice.controllers;

import com.dc297.ecommerce.orderservice.dtos.OrderDto;
import com.dc297.ecommerce.orderservice.services.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final IOrderService orderService;
    private final Logger LOGGER = LoggerFactory.getLogger(OrderController.class.getName());

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

//    @GetMapping
//    public List<OrderDto> findAll() {
//        return orderService.findAll();
//    }

    @GetMapping("/{orderId}")
    public OrderDto findById(@PathVariable UUID orderId) {
        LOGGER.info("Received request to fetch order by order id {}", orderId);
        return orderService.getById(orderId);
    }

    @PostMapping
    public OrderDto create(@RequestBody OrderDto order) {
        LOGGER.info("Received request to create order.");
        return orderService.create(order);
    }

//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable UUID id){
//        orderService.deleteById(id);
//    }

    @GetMapping("/{id}/cancel")
    public void cancel(@PathVariable UUID id){
        LOGGER.info("Received request to cancel order");
        orderService.cancel(id);
    }

    @PutMapping("/{id}/status")
    public void putStatus(@PathVariable UUID id, @RequestBody int status){
        LOGGER.info("Received request to update status id {} status {} ", id, status);
        orderService.updateStatus(id, status);
    }
}
