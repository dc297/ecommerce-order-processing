package com.dc297.ecommerce.orderService.services;

import com.dc297.ecommerce.entities.*;
import com.dc297.ecommerce.orderService.dtos.*;
import com.dc297.ecommerce.orderService.exceptions.BadRequestException;
import com.dc297.ecommerce.orderService.exceptions.NotFoundException;
import com.dc297.ecommerce.orderService.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService{
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    private final Logger logger = LoggerFactory.getLogger(OrderService.class.getName());

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public OrderDto create(OrderDto order) {
        if(order.id != null) throw new BadRequestException();
        return orderEntityToDto(orderRepository.save(orderDtoToEntity(order)));
    }

    @Override
    public List<OrderDto> findAll() {
        return orderRepository.findAll().stream().map(this::orderEntityToDto).collect(Collectors.toList());
    }

    @Override
    public OrderDto getById(UUID id) {
        logger.info("Getting order by id {}", id);
        return orderEntityToDto(orderRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public void deleteById(UUID id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void cancel(UUID id) {
        logger.info("Cancel order by id {}", id);
        Order order = orderRepository.findById(id).orElseThrow(NotFoundException::new);
        order.status = 5;
        orderRepository.save(order);
    }

    private OrderDto orderEntityToDto(Order order){
        if(order == null) return null;

        logger.info("Converting order entity to dto");

        OrderDto output = new OrderDto();
        output.id = order.id;
        output.status = order.status;
        output.shippingMethod = order.shippingMethod;
        output.shippingMethodNotes = order.shippingMethodNotes;
        output.subtotal = order.subtotal;
        output.tax = order.tax;
        output.shippingCharges = order.shippingCharges;
        output.total = order.total;

        logger.info("Converting order customer entity to dto {}", order.customer);
        output.customer = customerEntityToDto(order.customer);

        return output;
    }

    private CustomerDto customerEntityToDto(Customer customer) {
        if(customer == null) return null;
        CustomerDto output = new CustomerDto();
        output.id = customer.id;
        output.name = customer.name;
        return output;
    }

    private Order orderDtoToEntity(OrderDto order) {
        if(order == null) return null;
        Order output = new Order();
        output.id = order.id;
        output.status = order.status;
        output.shippingMethod = order.shippingMethod;
        output.shippingMethodNotes = order.shippingMethodNotes;
        output.shippingCharges = order.shippingCharges;
        output.subtotal = order.subtotal;
        output.tax = order.tax;
        output.total = order.total;
        output.customer = customerDtoToEntity(order.customer);
        return output;
    }


    private Customer customerDtoToEntity(CustomerDto customer) {
        return customerRepository.findById(customer.id).orElseThrow(NotFoundException::new);
    }

}
