/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Model.Trade;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author eltet
 */
public class TradeJpaController implements Serializable {

    public TradeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Trade trade) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(trade);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Trade trade) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            trade = em.merge(trade);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = trade.getId();
                if (findTrade(id) == null) {
                    throw new NonexistentEntityException("The trade with id " + id + " no longer exists.");
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
            Trade trade;
            try {
                trade = em.getReference(Trade.class, id);
                trade.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The trade with id " + id + " no longer exists.", enfe);
            }
            em.remove(trade);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Trade> findTradeEntities() {
        return findTradeEntities(true, -1, -1);
    }

    public List<Trade> findTradeEntities(int maxResults, int firstResult) {
        return findTradeEntities(false, maxResults, firstResult);
    }

    private List<Trade> findTradeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Trade.class));
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

    public Trade findTrade(Date date){
        EntityManager em = getEntityManager();
        try{
            List<Object> query = em.createQuery("select t.id from Trade as t where t.dateoftrade= '" + date+ "'").getResultList();
            return em.find(Trade.class, query.get(0));
        } finally {
            em.close();
        }
    }
    
    public Trade findTrade(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Trade.class, id);
        } finally {
            em.close();
        }
    }

    public int getTradeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Trade> rt = cq.from(Trade.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
