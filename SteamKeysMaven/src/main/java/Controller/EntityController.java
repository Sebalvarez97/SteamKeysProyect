/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Key;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityController {
    
    private static EntityManager manager;
    
    private static EntityManagerFactory emf;
    
     public static void main(String[] args){
        //Creamos el gestor de persistencia
        emf = Persistence.createEntityManagerFactory("SteamKeysPU");
        //manager = emf.createEntityManager();
        
        KeyJpaController keysController = new KeyJpaController(emf);
        int cantidadkeys = keysController.getKeyCount(); 
       // List<Key> keys = (List<Key>) manager.createQuery("FROM key").getResultList();
        System.out.println("En esta base de datos hay " + cantidadkeys + " keys");
        
    }
    
    
    
}
