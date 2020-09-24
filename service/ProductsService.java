package com.rga.springwebapp.service;

import com.rga.springwebapp.domain.Customer;
import com.rga.springwebapp.domain.Product;
import com.rga.springwebapp.repositories.CustomerJpaDAO;
import com.rga.springwebapp.repositories.ProductJpaDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class ProductsService {

    @PersistenceContext
    private final EntityManager em;

    private final CustomerJpaDAO customerJpaDAO;

    private final ProductJpaDAO productJpaDAO;

    public ProductsService(EntityManager em, ProductJpaDAO productJpaDAO, CustomerJpaDAO customerJpaDAO) {
        this.em = em;
        this.productJpaDAO = productJpaDAO;
        this.customerJpaDAO = customerJpaDAO;
    }

    public ProductJpaDAO getProductJpaDAO() {
        return productJpaDAO;
    }

    @Transactional
    public void save (Customer customer){
        Customer savedCustomer = customerJpaDAO.save(customer);
    }

    @Transactional
    public void update (Customer customer) {
        em.getTransaction().begin();
        em.merge(customer);
        em.getTransaction().commit();
    }

    @Transactional
    public void save (Product product){
        Product savedProduct = productJpaDAO.save(product);
    }

    public void update (Product product) {
        em.getTransaction().begin();
        em.merge(product);
        em.getTransaction().commit();
    }

    @Transactional
    public void delete (Long id){
        productJpaDAO.deleteById (id);
    }

    @Transactional(readOnly = true)
    public String findTitleById(Long id){
        return productJpaDAO.findById(id).orElse(null).getTitle();
    }

    @Transactional(readOnly = true)
    public Product findById(Long id){
        return productJpaDAO.findById(id).orElse(null);
    }


    public List<Product> getAll() {
        return productJpaDAO.findAll();
    }

    public Product getById(Long id) {
        return productJpaDAO.findById(id).orElse(null);
    }

    public Product saveAndSet (Product product) {
        return productJpaDAO.save(product);
    }

    public List<Product> getByPrice(double priceFrom, double priceTo) {
        return productJpaDAO.findAllByPriceBetween(priceFrom, priceTo);
    }

}
