package dao;

import model.Ingrediente;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class IngredienteDao {

	@PersistenceContext(unitName = "PIZZERIA-0.0.1-SNAPSHOT")
	private EntityManager em;
//    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("PIZZERIA");

	public void saveIngrediente(Ingrediente ingrediente) {
		em.merge(ingrediente);
//        EntityManager em = emf.createEntityManager();
//        try {
//            em.getTransaction().begin();
//            em.merge(ingrediente);
//            em.getTransaction().commit();
//        } catch (Exception e) {
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            em.close();
//        }
	}

	public Ingrediente getIngredienteById(int id) {
		return em.find(Ingrediente.class, id);
//        EntityManager em = emf.createEntityManager();
//        try {
//            return em.find(Ingrediente.class, id);
//        } finally {
//            em.close();
//        }
	}

	public List<Ingrediente> getAllIngrediente() {
		TypedQuery<Ingrediente> query = em.createQuery("SELECT i FROM Ingrediente i", Ingrediente.class);
		return query.getResultList();

//        EntityManager em = emf.createEntityManager();
//        try {
//            TypedQuery<Ingrediente> query = em.createQuery("SELECT i FROM Ingrediente i", Ingrediente.class);
//            return query.getResultList();
//        } finally {
//            em.close();
//        }
	}

	public void updateIngrediente(Ingrediente ingrediente) {
		em.merge(ingrediente);
//        EntityManager em = emf.createEntityManager();
//        try {
//            em.getTransaction().begin();
//            em.merge(ingrediente);
//            em.getTransaction().commit(); 
//        } catch (Exception e) {
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            em.close();
//        }
	}

	public void deleteIngrediente(int id) {
		Ingrediente ingrediente = em.find(Ingrediente.class, id);
		if (ingrediente != null) {
			em.remove(ingrediente);
		}
//        EntityManager em = emf.createEntityManager();
//        try {
//            em.getTransaction().begin();
//            Ingrediente ingrediente = em.find(Ingrediente.class, id);
//            if (ingrediente != null) {
//                em.remove(ingrediente);
//            }
//            em.getTransaction().commit();
//        } catch (Exception e) {
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            em.close();
//        }
	}
}
