package com.pizza.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pizza.model.*;

@Repository
public interface ImpastoRepository extends CrudRepository<Impasto, Long> {
	
}
