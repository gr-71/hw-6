package com.rga.springwebapp.repositories;

import com.rga.springwebapp.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductJpaDAO extends JpaRepository<Product, Long> {
    List<Product> findAllByTitleLike(String title);
    List<Product> findAllByIdBetween(Long startId, Long endId);
    List<Product> findAllByPriceBetween(double priceFrom, double priceTo);

    @Override
    void deleteById(Long id);


}

