package com.rga.springwebapp.domain;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    public Order() {
    }

    private List<Product> products;

    @Id
    @Column(name = "order_id")
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable (name = "prod_orders",
                joinColumns = @JoinColumn(name = "order_id"),
                inverseJoinColumns = @JoinColumn(name = "product_id")) // обр. привязка

    public List<Product> getProducts() {
        return products;
    }
    public Long getId() {
        return id;
    }
    public Customer getCustomer() {
        return customer;
    }

    @Autowired
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    @Autowired
    public void setId(Long id) {
        this.id = id;
    }
    @Autowired
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return String.format ("Order: [id = %d, products = %s]", id, products);
    }
}
