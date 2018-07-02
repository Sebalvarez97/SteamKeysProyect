/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Model.Key;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityController {
    
    private static EntityManager manager;
    
    private static EntityManagerFactory emf;
    
     public static void main(String[] args) throws NonexistentEntityException{
        //Creamos el gestor de persistencia
        emf = Persistence.createEntityManagerFactory("SteamKeysPU");
        //manager = emf.createEntityManager();
        KeyJpaController keysController = new KeyJpaController(emf);
        
        
        //PRUEBA DE CREACION DE LLAVE
        //Key k = new Key(63.99, "untradeable", "Revolver");
        //keysController.create(k);
        //////////////////////////////////////////////////////
        
        //PRUEBA DE LISTAR LLAVES
        List<Key> keys = (List<Key>) keysController.findKeyEntities();
        Key key;
//        Iterator iter = keys.iterator();
//        while(iter.hasNext()){
//            key = (Key)iter.next(); 
//            System.out.println(key.getId() + " " + key.getKeyState() + " " + key.getKeyType());
//        }
        //////////////////////////////////////////////////////
       
        //PRUEBA DE DESTRUCCION DE LLAVE
        //keysController.destroy((long) 33);
       
        /////////////////////////////////////////////////////
        int cantidadkeys = keysController.getKeyCount(); 
       // List<Key> keys = (List<Key>) manager.createQuery("FROM key").getResultList();
        System.out.println("En esta base de datos hay " + cantidadkeys + " keys");
        
    }
    
    
    
}
