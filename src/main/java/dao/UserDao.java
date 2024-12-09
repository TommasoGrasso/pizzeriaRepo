package dao;

import model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class UserDao {

	 @PersistenceContext
	 private EntityManager em;
//    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("PIZZERIA");

	 public User authenticateUser(String username, String password) {
	        try {
	            User user = em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class)
	                          .setParameter("username", username)
	                          .setParameter("password", password)
	                          .getSingleResult();
	            return user;
	        } catch (NoResultException e) {
	            return null;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	 
    public void saveUser(User user) {
    	em.persist(user);
//        EntityManager em = emf.createEntityManager();
//        try {
//            em.getTransaction().begin();
//            em.persist(user);
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

    public User getUserById(int id) {
    	return em.find(User.class, id);
//        EntityManager em = emf.createEntityManager();
//        try {
//            return em.find(User.class, id);
//        } finally {
//            em.close();
//        }
    }

    public List<User> getAllUsers() {
      TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
      return query.getResultList();
//        EntityManager em = emf.createEntityManager();
//        try {
//            TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
//            return query.getResultList();
//        } finally {
//            em.close();
//        }
    }

    public void updateUser(User user) {
    	em.merge(user);
//        EntityManager em = emf.createEntityManager();
//        try {
//            em.getTransaction().begin();
//            em.merge(user);
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

    public void deleteUser(int id) {
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
//        EntityManager em = emf.createEntityManager();
//        try {
//            em.getTransaction().begin();
//            User user = em.find(User.class, id);
//            if (user != null) {
//                em.remove(user);
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
