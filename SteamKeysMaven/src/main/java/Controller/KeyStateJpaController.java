/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Model.KeyState;
import java.io.Serializable;
import java.util.Iterator;
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
public class KeyStateJpaController implements Serializable {

    public KeyStateJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(KeyState keyState) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(keyState);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(KeyState keyState) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            keyState = em.merge(keyState);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = keyState.getId();
                if (findKeyState(id) == null) {
                    throw new NonexistentEntityException("The keyState with id " + id + " no longer exists.");
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
            KeyState keyState;
            try {
                keyState = em.getReference(KeyState.class, id);
                keyState.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The keyState with id " + id + " no longer exists.", enfe);
            }
            em.remove(keyState);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<KeyState> findKeyStateEntities() {
        return findKeyStateEntities(true, -1, -1);
    }

    public List<KeyState> findKeyStateEntities(int maxResults, int firstResult) {
        return findKeyStateEntities(false, maxResults, firstResult);
    }

    private List<KeyState> findKeyStateEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(KeyState.class));
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
                        public KeyState findKeyState(String statename){
                            EntityManager em = getEntityManager();
                            try {
                                List <Object> query = em.createQuery("select ks.id from KeyState as ks where ks.stateDescription='" + statename + "'").getResultList();
                                return em.find(KeyState.class, query.get(0) );
                            } finally {
                                em.close();
                            }
                        }
                        
                        public boolean findIfExists(String statename){
                            EntityManager em = getEntityManager();
                            try {
                                List <Object> query = em.createQuery("select ks.id from KeyState as ks where ks.stateDescription='" + statename + "'").getResultList();
                                if(query.isEmpty()){
                                    return false;
                                }else return true;
                                
                            } finally {
                                em.close();
                            }
                        }
                        
                          public boolean findIfExists(KeyState state){
                            EntityManager em = getEntityManager();
                            try {
                                List <Object> query = em.createQuery("select ks.id from KeyState as ks where ks.stateDescription='" + state.getStateDescription() + "'").getResultList();
                                if(query.isEmpty()){
                                    return false;
                                }else return true;
                                
                            } finally {
                                em.close();
                            }
                        }
    //LISTA LAS ENTIDADES
    public void List(){
            List <KeyState> states = (List<KeyState>) findKeyStateEntities();
            KeyState state;
            Iterator iter = states.iterator();
        while(iter.hasNext()){
            state = (KeyState)iter.next(); 
            System.out.println(state.getId() + " " + state.getStateDescription());
            
        }
        
    }                      
                          
    public KeyState findKeyState(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(KeyState.class, id);
        } finally {
            em.close();
        }
    }

    public int getKeyStateCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<KeyState> rt = cq.from(KeyState.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
