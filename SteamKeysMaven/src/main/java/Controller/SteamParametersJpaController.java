/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Model.SteamParameters;
import java.io.Serializable;
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
public class SteamParametersJpaController implements Serializable {

    public SteamParametersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SteamParameters steamParameters) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(steamParameters);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SteamParameters steamParameters) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            steamParameters = em.merge(steamParameters);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = steamParameters.getId();
                if (findSteamParameters(id) == null) {
                    throw new NonexistentEntityException("The steamParameters with id " + id + " no longer exists.");
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
            SteamParameters steamParameters;
            try {
                steamParameters = em.getReference(SteamParameters.class, id);
                steamParameters.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The steamParameters with id " + id + " no longer exists.", enfe);
            }
            em.remove(steamParameters);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SteamParameters> findSteamParametersEntities() {
        return findSteamParametersEntities(true, -1, -1);
    }

    public List<SteamParameters> findSteamParametersEntities(int maxResults, int firstResult) {
        return findSteamParametersEntities(false, maxResults, firstResult);
    }

    private List<SteamParameters> findSteamParametersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SteamParameters.class));
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
                        //PERMITE LA BUSQUEDA EN DB POR NOMBRE DE PARAMETRO
                        public SteamParameters findSteamParameters(String name){
                             EntityManager em = getEntityManager();
                            try {
                                List <Object> query = em.createQuery("select sp.id from SteamParameters as sp where sp.name='" + name + "'").getResultList();
                                return em.find(SteamParameters.class, query.get(0) );
                            } finally {
                                em.close();
                            }
                        }
    
    public SteamParameters findSteamParameters(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SteamParameters.class, id);
        } finally {
            em.close();
        }
    }

    public int getSteamParametersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SteamParameters> rt = cq.from(SteamParameters.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
