package com.rga.springwebapp.repositories;

import com.rga.springwebapp.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerJpaDAO extends JpaRepository<Customer, Long> {
    List<Customer> findAllByNameLike(String name);
    List<Customer> findAllByIdBetween(Long startId, Long endId);

//    List<Customer> findByName(String name, Pageable page);

    @Override
    void deleteById(Long id);
}
