/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Model.Key;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Trade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author eltet
 */
public class KeyJpaController implements Serializable {

    public KeyJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Key key) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trade trade = key.getTrade();
            if (trade != null) {
                trade = em.getReference(trade.getClass(), trade.getId());
                key.setTrade(trade);
            }
            em.persist(key);
            if (trade != null) {
                trade.getKeytraded().add(key);
                trade = em.merge(trade);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Key key) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Key persistentKey = em.find(Key.class, key.getId());
            Trade tradeOld = persistentKey.getTrade();
            Trade tradeNew = key.getTrade();
            if (tradeNew != null) {
                tradeNew = em.getReference(tradeNew.getClass(), tradeNew.getId());
                key.setTrade(tradeNew);
            }
            key = em.merge(key);
            if (tradeOld != null && !tradeOld.equals(tradeNew)) {
                tradeOld.getKeytraded().remove(key);
                tradeOld = em.merge(tradeOld);
            }
            if (tradeNew != null && !tradeNew.equals(tradeOld)) {
                tradeNew.getKeytraded().add(key);
                tradeNew = em.merge(tradeNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = key.getId();
                if (findKey(id) == null) {
                    throw new NonexistentEntityException("The key with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Key key;
            try {
                key = em.getReference(Key.class, id);
                key.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The key with id " + id + " no longer exists.", enfe);
            }
            Trade trade = key.getTrade();
            if (trade != null) {
                trade.getKeytraded().remove(key);
                trade = em.merge(trade);
            }
            em.remove(key);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Key> findKeyEntities() {
        return findKeyEntities(true, -1, -1);
    }

    public List<Key> findKeyEntities(int maxResults, int firstResult) {
        return findKeyEntities(false, maxResults, firstResult);
    }

    private List<Key> findKeyEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Key.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Key findKey(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Key.class, id);
        } finally {
            em.close();
        }
    }

    public int getKeyCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Key> rt = cq.from(Key.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
