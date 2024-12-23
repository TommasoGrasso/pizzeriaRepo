package com.pizza.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pizza.model.*;


@Repository
public interface IngredienteRepository extends CrudRepository<Ingrediente, Long> {

}
