package Utils;

import Entity.Hit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
    private final EntityManager entityManager = factory.createEntityManager();

    synchronized public boolean addHit(Hit hit) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(hit);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e){
            entityManager.getTransaction().rollback();
            return false;
        }
    }

    synchronized public List<Hit> getHitList() {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("SELECT o FROM Hit o");
            return query.getResultList();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return new ArrayList<>();
        }
    }

    public boolean clearList() {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("DELETE FROM Hit");
            query.executeUpdate();
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return false;
        }
    }


}
