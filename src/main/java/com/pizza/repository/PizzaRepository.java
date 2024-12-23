package com.pizza.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pizza.model.*;


import java.util.List;

@Repository
public interface PizzaRepository extends CrudRepository<Pizza, Long> {

    List<Pizza> findByUserId(int userId);
}
