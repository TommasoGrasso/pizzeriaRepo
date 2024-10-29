package dao;

import model.Impasto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class ImpastoDao {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("PIZZERIA");

    public void saveImpasto(Impasto impasto) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(impasto);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public Impasto getImpastoById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Impasto.class, id);
        } finally {
            em.close();
        }
    }

    public List<Impasto> getAllImpasti() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Impasto> query = em.createQuery("SELECT i FROM Impasto i", Impasto.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void updateImpasto(Impasto impasto) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(impasto);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void deleteImpasto(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Impasto impasto = em.find(Impasto.class, id);
            if (impasto != null) {
                em.remove(impasto);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
