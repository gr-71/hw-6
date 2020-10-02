package com.rga.springwebapp.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @Column(name = "customer_id")
    @GeneratedValue
    private Long id;

    @Column(name = "name_fld")
    private String name = "customer";

    @Column(name = "e-mail_fld")
    private String email;

    @OneToMany()
    @JoinColumn(name = "order_id")
    private List<Order> orders;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOrders(List<Order> order1s) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return String.format ("Customer: [ id = %d,  name = %s, email = %s]", id, name, email);
    }

}
