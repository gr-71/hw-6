package com.rga.springwebapp.service;

import com.rga.springwebapp.domain.Product;
import com.rga.springwebapp.domain.Customer;

import javax.persistence.EntityManager;
import java.util.ArrayList;

public class InitInfoService {

    public static final double CUSTOMERS_COUNTING = 5;
    public static final double PRODUCTS_COUNTING = 20;

    private static ArrayList<Customer> customers = new ArrayList<> ();
    private static ArrayList<Product> products = new ArrayList<>();

    public InitInfoService() {
    }

    public static ArrayList<Customer> getCustomers() {
        return customers;
    }
    public static ArrayList<Product> getProducts() {
        return products;
    }

    public static void setCustomers(ArrayList<Customer> customers) {
        InitInfoService.customers = customers;
    }
    public static void setProducts(ArrayList<Product> products) {
        InitInfoService.products = products;
    }

    private static void initCustomersAndProducts (EntityManager em) {
        em.getTransaction().begin();
        for (Customer customer : customers) {
            em.merge(customer);
        }
        for (Product product : products) {
            em.merge(product);
        }
        System.out.println("Some data was initialized successfully!");
        em.getTransaction().commit();
    }

    private static void initInfo(EntityManager em){
        initCustomersAndProducts(em);
    }

    // Automatic generation some data to work with
    static {
        int n = 0;
    // to generate customers with some data
        for (int cc = 0; cc < CUSTOMERS_COUNTING; cc++) {
            Customer ourCustomer = new Customer();
            ourCustomer.setName("customer N " + n++);
            ourCustomer.setEmail(cc++ + "@email.com");
            customers.add (ourCustomer);
        }
    // to generate products with some data
        for (int pc = 0; pc < PRODUCTS_COUNTING; pc++) {
            Product newProduct = new Product ();
            newProduct.setTitle ("item N " + n++);
            newProduct.setPrice (10 + (Math.random() * 300));
            products.add (newProduct);
        }
    }

}
