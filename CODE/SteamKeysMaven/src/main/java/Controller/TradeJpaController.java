/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.SteamItem;
import java.util.ArrayList;
import java.util.List;
import Model.Key;
import Model.Trade;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
        if (trade.getItems() == null) {
            trade.setItems(new ArrayList<SteamItem>());
        }
        if (trade.getKeytraded() == null) {
            trade.setKeytraded(new ArrayList<Key>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<SteamItem> attachedItems = new ArrayList<SteamItem>();
            for (SteamItem itemsSteamItemToAttach : trade.getItems()) {
                itemsSteamItemToAttach = em.getReference(itemsSteamItemToAttach.getClass(), itemsSteamItemToAttach.getId());
                attachedItems.add(itemsSteamItemToAttach);
            }
            trade.setItems(attachedItems);
            List<Key> attachedKeytraded = new ArrayList<Key>();
            for (Key keytradedKeyToAttach : trade.getKeytraded()) {
                keytradedKeyToAttach = em.getReference(keytradedKeyToAttach.getClass(), keytradedKeyToAttach.getId());
                attachedKeytraded.add(keytradedKeyToAttach);
            }
            trade.setKeytraded(attachedKeytraded);
            em.persist(trade);
            for (SteamItem itemsSteamItem : trade.getItems()) {
                Trade oldTradeOfItemsSteamItem = itemsSteamItem.getTrade();
                itemsSteamItem.setTrade(trade);
                itemsSteamItem = em.merge(itemsSteamItem);
                if (oldTradeOfItemsSteamItem != null) {
                    oldTradeOfItemsSteamItem.getItems().remove(itemsSteamItem);
                    oldTradeOfItemsSteamItem = em.merge(oldTradeOfItemsSteamItem);
                }
            }
            for (Key keytradedKey : trade.getKeytraded()) {
                Trade oldTradeOfKeytradedKey = keytradedKey.getTrade();
                keytradedKey.setTrade(trade);
                keytradedKey = em.merge(keytradedKey);
                if (oldTradeOfKeytradedKey != null) {
                    oldTradeOfKeytradedKey.getKeytraded().remove(keytradedKey);
                    oldTradeOfKeytradedKey = em.merge(oldTradeOfKeytradedKey);
                }
            }
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
            Trade persistentTrade = em.find(Trade.class, trade.getId());
            List<SteamItem> itemsOld = persistentTrade.getItems();
            List<SteamItem> itemsNew = trade.getItems();
            List<Key> keytradedOld = persistentTrade.getKeytraded();
            List<Key> keytradedNew = trade.getKeytraded();
            List<SteamItem> attachedItemsNew = new ArrayList<SteamItem>();
            for (SteamItem itemsNewSteamItemToAttach : itemsNew) {
                itemsNewSteamItemToAttach = em.getReference(itemsNewSteamItemToAttach.getClass(), itemsNewSteamItemToAttach.getId());
                attachedItemsNew.add(itemsNewSteamItemToAttach);
            }
            itemsNew = attachedItemsNew;
            trade.setItems(itemsNew);
            List<Key> attachedKeytradedNew = new ArrayList<Key>();
            for (Key keytradedNewKeyToAttach : keytradedNew) {
                keytradedNewKeyToAttach = em.getReference(keytradedNewKeyToAttach.getClass(), keytradedNewKeyToAttach.getId());
                attachedKeytradedNew.add(keytradedNewKeyToAttach);
            }
            keytradedNew = attachedKeytradedNew;
            trade.setKeytraded(keytradedNew);
            trade = em.merge(trade);
            for (SteamItem itemsOldSteamItem : itemsOld) {
                if (!itemsNew.contains(itemsOldSteamItem)) {
                    itemsOldSteamItem.setTrade(null);
                    itemsOldSteamItem = em.merge(itemsOldSteamItem);
                }
            }
            for (SteamItem itemsNewSteamItem : itemsNew) {
                if (!itemsOld.contains(itemsNewSteamItem)) {
                    Trade oldTradeOfItemsNewSteamItem = itemsNewSteamItem.getTrade();
                    itemsNewSteamItem.setTrade(trade);
                    itemsNewSteamItem = em.merge(itemsNewSteamItem);
                    if (oldTradeOfItemsNewSteamItem != null && !oldTradeOfItemsNewSteamItem.equals(trade)) {
                        oldTradeOfItemsNewSteamItem.getItems().remove(itemsNewSteamItem);
                        oldTradeOfItemsNewSteamItem = em.merge(oldTradeOfItemsNewSteamItem);
                    }
                }
            }
            for (Key keytradedOldKey : keytradedOld) {
                if (!keytradedNew.contains(keytradedOldKey)) {
                    keytradedOldKey.setTrade(null);
                    keytradedOldKey = em.merge(keytradedOldKey);
                }
            }
            for (Key keytradedNewKey : keytradedNew) {
                if (!keytradedOld.contains(keytradedNewKey)) {
                    Trade oldTradeOfKeytradedNewKey = keytradedNewKey.getTrade();
                    keytradedNewKey.setTrade(trade);
                    keytradedNewKey = em.merge(keytradedNewKey);
                    if (oldTradeOfKeytradedNewKey != null && !oldTradeOfKeytradedNewKey.equals(trade)) {
                        oldTradeOfKeytradedNewKey.getKeytraded().remove(keytradedNewKey);
                        oldTradeOfKeytradedNewKey = em.merge(oldTradeOfKeytradedNewKey);
                    }
                }
            }
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
            List<SteamItem> items = trade.getItems();
            for (SteamItem itemsSteamItem : items) {
                itemsSteamItem.setTrade(null);
                itemsSteamItem = em.merge(itemsSteamItem);
            }
            List<Key> keytraded = trade.getKeytraded();
            for (Key keytradedKey : keytraded) {
                keytradedKey.setTrade(null);
                keytradedKey = em.merge(keytradedKey);
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
