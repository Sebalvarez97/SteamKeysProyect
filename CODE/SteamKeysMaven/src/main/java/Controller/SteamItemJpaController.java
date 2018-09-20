/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Model.SteamItem;
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
public class SteamItemJpaController implements Serializable {

    public SteamItemJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SteamItem steamItem) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trade trade = steamItem.getTrade();
            if (trade != null) {
                trade = em.getReference(trade.getClass(), trade.getId());
                steamItem.setTrade(trade);
            }
            em.persist(steamItem);
            if (trade != null) {
                trade.getItems().add(steamItem);
                trade = em.merge(trade);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SteamItem steamItem) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SteamItem persistentSteamItem = em.find(SteamItem.class, steamItem.getId());
            Trade tradeOld = persistentSteamItem.getTrade();
            Trade tradeNew = steamItem.getTrade();
            if (tradeNew != null) {
                tradeNew = em.getReference(tradeNew.getClass(), tradeNew.getId());
                steamItem.setTrade(tradeNew);
            }
            steamItem = em.merge(steamItem);
            if (tradeOld != null && !tradeOld.equals(tradeNew)) {
                tradeOld.getItems().remove(steamItem);
                tradeOld = em.merge(tradeOld);
            }
            if (tradeNew != null && !tradeNew.equals(tradeOld)) {
                tradeNew.getItems().add(steamItem);
                tradeNew = em.merge(tradeNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = steamItem.getId();
                if (findSteamItem(id) == null) {
                    throw new NonexistentEntityException("The steamItem with id " + id + " no longer exists.");
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
            SteamItem steamItem;
            try {
                steamItem = em.getReference(SteamItem.class, id);
                steamItem.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The steamItem with id " + id + " no longer exists.", enfe);
            }
            Trade trade = steamItem.getTrade();
            if (trade != null) {
                trade.getItems().remove(steamItem);
                trade = em.merge(trade);
            }
            em.remove(steamItem);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SteamItem> findSteamItemEntities() {
        return findSteamItemEntities(true, -1, -1);
    }

    public List<SteamItem> findSteamItemEntities(int maxResults, int firstResult) {
        return findSteamItemEntities(false, maxResults, firstResult);
    }

    private List<SteamItem> findSteamItemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SteamItem.class));
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

    public SteamItem findSteamItem(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SteamItem.class, id);
        } finally {
            em.close();
        }
    }

    public int getSteamItemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SteamItem> rt = cq.from(SteamItem.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
