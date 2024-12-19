package controller;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import model.Ingrediente;

@Repository
public interface IngredienteRepository extends CrudRepository<Ingrediente, Long> {

}
