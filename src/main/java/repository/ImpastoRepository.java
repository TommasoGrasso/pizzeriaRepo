package repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import model.Impasto;

@Repository
public interface ImpastoRepository extends CrudRepository<Impasto, Long> {
	
}
