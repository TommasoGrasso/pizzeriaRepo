package dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
//import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import model.Impasto;
import model.Ingrediente;
import model.Pizza;
//import model.Pizza;
import model.User;

@Stateless 
public class PizzaDao {

	 @PersistenceContext
	 private EntityManager em;

	    public void savePizza(String pizzaName, String impastoId, int userId, String[] ingredienti) {
	        try {
	            Impasto impasto = em.find(Impasto.class, Integer.parseInt(impastoId));
	            User user = em.find(User.class, userId);

	            Pizza pizza = new Pizza();
	            pizza.setNome(pizzaName);
	            pizza.setImpasto(impasto);
	            pizza.setUser(user);

	            for (String ingredienteId : ingredienti) {
	                Ingrediente ingrediente = em.find(Ingrediente.class, Integer.parseInt(ingredienteId));
	                pizza.addIngrediente(ingrediente);
	            }

	            em.persist(pizza);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    public List<Pizza> getPizzeByUser(int userId) {
	        List<Pizza> pizze = new ArrayList<>();
	        try {
	            pizze = em.createQuery("SELECT p FROM Pizza p WHERE p.user.id = :userId", Pizza.class)
	                      .setParameter("userId", userId)
	                      .getResultList();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return pizze;
	    }

	    // Eliminazione pizza per ID
	    public void deletePizzaById(Long id) {
	        try {
	            Pizza pizza = em.find(Pizza.class, id);
	            if (pizza != null) {
	                em.remove(pizza);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    public void updatePizza(Long pizzaId, String nuovoNome, String nuovoImpastoId, String[] nuoviIngredienti) {
	        try {
	            Pizza pizza = em.find(Pizza.class, pizzaId);
	            if (pizza != null) {
	                pizza.setNome(nuovoNome);

	                Impasto nuovoImpasto = em.find(Impasto.class, Integer.parseInt(nuovoImpastoId));
	                if (nuovoImpasto != null) {
	                    pizza.setImpasto(nuovoImpasto);
	                }

	                if (nuoviIngredienti != null && nuoviIngredienti.length > 0) {
	                    pizza.getIngredienti().clear();
	                    for (String ingredienteId : nuoviIngredienti) {
	                        Ingrediente ingrediente = em.find(Ingrediente.class, Integer.parseInt(ingredienteId));
	                        if (ingrediente != null) {
	                            pizza.addIngrediente(ingrediente);
	                        }
	                    }
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    // Recupero di tutti gli impasti
	    public List<Impasto> getAllImpasti() {
	        List<Impasto> impasti = new ArrayList<>();
	        try {
	            impasti = em.createQuery("SELECT i FROM Impasto i", Impasto.class).getResultList();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return impasti;
	    }

	    public List<Ingrediente> getAllIngredienti() {
	        List<Ingrediente> ingredienti = new ArrayList<>();
	        try {
	            ingredienti = em.createQuery("SELECT i FROM Ingrediente i", Ingrediente.class).getResultList();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return ingredienti;
	    }

	    public List<Pizza> getAllPizze() {
	        List<Pizza> pizze = new ArrayList<>();
	        try {
	            pizze = em.createQuery("SELECT p FROM Pizza p", Pizza.class).getResultList();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return pizze;
	    }

	    public Pizza getPizzaById(Long id) {
	        Pizza pizza = null;
	        try {
	            pizza = em.find(Pizza.class, id);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return pizza;
	    }
	}
	 