/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Keys;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author eltet
 */
public class EntityController {
    
    private static EntityManager manager;
    private static EntityManagerFactory emf;
    
    
    public static void main(String[] args){
        //Creamos el gestor de persistencia
        emf = Persistence.createEntityManagerFactory("SteamKeysPU");
        manager = emf.createEntityManager();
        
        
        List<Keys> keys = (List<Keys>) manager.createQuery("FROM keys").getResultList();
        System.out.println("En esta base de datos hay  " + keys.size() + "keys");
        
    }
    
    
    
    
}
