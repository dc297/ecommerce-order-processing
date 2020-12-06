package com.dc297.ecommerce.entities;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;

    @Column(name = "status")
    public int status;

    @Column(name = "shipping_method")
    public int shippingMethod;

    @Column(name = "shipping_method_notes")
    public String shippingMethodNotes;

    @ManyToOne
    public Customer customer;

    @OneToMany(mappedBy = "order")
    public List<OrderItem> items;

    @OneToMany(mappedBy = "order")
    public List<OrderAddress> addresses;

    @OneToMany(mappedBy = "order")
    public List<OrderPayment> payments;

    @OneToOne
    @JoinColumn(name = "pricing_id")
    public OrderPricing pricing;
}
