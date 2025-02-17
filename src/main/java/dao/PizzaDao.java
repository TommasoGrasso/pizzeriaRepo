package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import jakarta.persistence.NoResultException;
//import model.Pizza;
import model.User;
import model.Impasto;
import model.Ingrediente;
import model.Pizza;

import java.util.ArrayList;
import java.util.List;

public class PizzaDao {
	private EntityManagerFactory emf;

	public PizzaDao() {
		emf = Persistence.createEntityManagerFactory("PIZZERIA");
	}

	public User authenticateUser(String username, String password) {
		EntityManager em = emf.createEntityManager();
		try {
			User user = em
					.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password",
							User.class)
					.setParameter("username", username).setParameter("password", password).getSingleResult();
			return user;
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
		}
	}

	public void savePizza(String pizzaName, String impastoId, int userId, String[] ingredienti) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		try {
			System.out.println("Nome Pizza: " + pizzaName);
			System.out.println("Impasto ID: " + impastoId);
			System.out.println("User ID: " + userId);

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

			em.merge(pizza);

			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			System.err.println("Errore durante il salvataggio della pizza: " + e.getMessage());
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	public List<Pizza> getPizzeByUser(int userId) {
		EntityManager em = emf.createEntityManager();
		List<Pizza> pizze = new ArrayList<>();

		try {
			pizze = em.createQuery("SELECT p FROM Pizza p WHERE p.user.id = :userId", Pizza.class)
					.setParameter("userId", userId).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}

		return pizze;
	}

	public void deletePizzaById(Long id) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			Pizza pizza = em.find(Pizza.class, id);
			if (pizza != null) {
				em.remove(pizza);
			}
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	public void updatePizza(Long pizzaId, String nuovoNome, String nuovoImpastoId, String[] nuoviIngredienti) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();

			Pizza pizza = em.find(Pizza.class, pizzaId);
			if (pizza != null) {
				pizza.setNome(nuovoNome);

				Impasto nuovoImpasto = em.find(Impasto.class, Integer.parseInt(nuovoImpastoId));
				if (nuovoImpasto != null) {
					pizza.setImpasto(nuovoImpasto);
				} else {
					System.err.println("Impasto non trovato con ID: " + nuovoImpastoId);
				}

				if (nuoviIngredienti != null && nuoviIngredienti.length > 0) {
					pizza.getIngredienti().clear();
					for (String ingredienteId : nuoviIngredienti) {
						Ingrediente ingrediente = em.find(Ingrediente.class, Integer.parseInt(ingredienteId));
						if (ingrediente != null) {
							pizza.addIngrediente(ingrediente);
						} else {
							System.err.println("Ingrediente non trovato con ID: " + ingredienteId);
						}
					}
				}

			} else {
				System.err.println("Pizza non trovata con ID: " + pizzaId);
			}

			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			System.err.println("Errore durante l'aggiornamento della pizza: " + e.getMessage());
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	public List<Impasto> getAllImpasti() {
		List<Impasto> impasti = new ArrayList<>();
		EntityManager em = emf.createEntityManager();
		try {
			impasti = em.createQuery("SELECT i FROM Impasto i", Impasto.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return impasti;
	}

	public List<Ingrediente> getAllIngredienti() {
		List<Ingrediente> ingredienti = new ArrayList<>();
		EntityManager em = emf.createEntityManager();
		try {
			ingredienti = em.createQuery("SELECT i FROM Ingrediente i", Ingrediente.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return ingredienti;
	}

}