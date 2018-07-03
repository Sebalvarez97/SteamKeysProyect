/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Model.KeyType;
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
public class KeyTypeJpaController implements Serializable {

    public KeyTypeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(KeyType keyType) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(keyType);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(KeyType keyType) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            keyType = em.merge(keyType);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = keyType.getId();
                if (findKeyType(id) == null) {
                    throw new NonexistentEntityException("The keyType with id " + id + " no longer exists.");
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
            KeyType keyType;
            try {
                keyType = em.getReference(KeyType.class, id);
                keyType.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The keyType with id " + id + " no longer exists.", enfe);
            }
            em.remove(keyType);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<KeyType> findKeyTypeEntities() {
        return findKeyTypeEntities(true, -1, -1);
    }

    public List<KeyType> findKeyTypeEntities(int maxResults, int firstResult) {
        return findKeyTypeEntities(false, maxResults, firstResult);
    }

    private List<KeyType> findKeyTypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(KeyType.class));
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

                                public KeyType findKeyType(String typename) {
                                    EntityManager em = getEntityManager();
                                    try {
                                        List <Object> query = em.createQuery("select kt.id from KeyType as kt where kt.typeDescription='" + typename +"'" ).getResultList();
                                        return em.find(KeyType.class, query.get(0));
                                    } finally {
                                        em.close();
                                    }
                                }
                                
                                public boolean findIfExists(KeyType type){
                                    EntityManager em = getEntityManager();
                                    try {
                                        List <Object> query = em.createQuery("select kt.id from KeyType as kt where kt.typeDescription='" + type.getTypeDescription() + "'").getResultList();
                                        if(query.isEmpty()){
                                            return false;
                                        }else return true;
                                
                                    } finally {
                                     em.close();
                                    }
                                }
                                public boolean findIfExists(String type){
                                    EntityManager em = getEntityManager();
                                    try {
                                        List <Object> query = em.createQuery("select kt.id from KeyType as kt where kt.typeDescription='" + type + "'").getResultList();
                                        if(query.isEmpty()){
                                            return false;
                                        }else return true;
                                
                                    } finally {
                                     em.close();
                                    }
                                }
//LISTA LAS ENTIDADES
        public void List(){
            List <KeyType> types = (List<KeyType>) findKeyTypeEntities();
            KeyType type;
            Iterator iter = types.iterator();
        while(iter.hasNext()){
            type = (KeyType)iter.next(); 
            System.out.println(type.getId() + " " + type.getTypeDescription());
            
        }
        
    }                           
    public KeyType findKeyType(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(KeyType.class, id);
        } finally {
            em.close();
        }
    }

    public int getKeyTypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<KeyType> rt = cq.from(KeyType.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
