package ejb;

import dao.PizzaDao;
import model.Pizza;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/pizze")
public class PizzaService {
	
	@EJB 
	private PizzaDao pizzaDao; 

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pizza> getAllPizzas() {
		List<Pizza> pizzeLoop = pizzaDao.getAllPizze();

		pizzeLoop.forEach(pizza -> {
			if (null != pizza.getImpasto()) {
				pizza.getImpasto().getPizze().forEach(pizzaImpasto -> {
					pizzaImpasto.setImpasto(null);
					pizzaImpasto.setIngredienti(null);
				});
			}
			if (null != pizza.getUser()) {
				pizza.getUser().getPizze().forEach(pizzaUser -> {
					pizzaUser.setImpasto(null);
					pizzaUser.setIngredienti(null);
					pizzaUser.setUser(null);
				});
			}
			pizza.getIngredienti().forEach(ingrediente -> {
				ingrediente.getPizze().forEach(pizzaIngrediente -> {
					pizzaIngrediente.setImpasto(null);
					pizzaIngrediente.setIngredienti(null);
				});
			});
		});

		return pizzeLoop;
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Pizza getPizzaById(@PathParam("id") Long id) {
		Pizza pizza = pizzaDao.getPizzaById(id);
		if (pizza == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return pizza;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createPizza(Pizza pizza) {
		if (pizza != null) {
			pizzaDao.savePizza(pizza.getNome(), String.valueOf(pizza.getImpasto().getId()),
					(int) pizza.getUser().getId(), pizza.getIngredienti().stream()
							.map(ingrediente -> String.valueOf(ingrediente.getId())).toArray(String[]::new));
		} else {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updatePizza(@PathParam("id") Long id, Pizza pizza) {
		if (pizza != null) {
			pizzaDao.updatePizza(id, pizza.getNome(), String.valueOf(pizza.getImpasto().getId()), pizza.getIngredienti()
					.stream().map(ingrediente -> String.valueOf(ingrediente.getId())).toArray(String[]::new));
		} else {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
	}

	@DELETE
	@Path("/{id}")
	public void deletePizza(@PathParam("id") Long id) {
		pizzaDao.deletePizzaById(id);
	}
}
