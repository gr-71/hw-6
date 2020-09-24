package com.rga.springwebapp.domain;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    private List<Order> orders;

    public Product() {
    }

    public Product(Long id, String title, Double price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "title_fld")
    private String title;

    @Column(name = "price_fld")
    private Double price;

    @ManyToMany
    @JoinTable (name = "products_orders",
                joinColumns = @JoinColumn(name = "product_id"),
                inverseJoinColumns = @JoinColumn(name = "order_id")) // обратная привязка

    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public Double getPrice() {
        return price;
    }
    public List<Order> getOrders() {
        return orders;
    }

    @Autowired
    public void setId(Long id) {
        this.id = id;
    }

    @Autowired
    public void setTitle(String title) {
        this.title = title;
    }

    @Autowired
    public void setPrice(Double price) {
        this.price = price;
    }

    @Autowired
    public void setOrders(List<Order> orders) { this.orders = orders; }

    @Override
    public String toString() {
        return String.format
                ("Product: [id = %d, title = %s, price = %d]", id, title, price);
    }
}
