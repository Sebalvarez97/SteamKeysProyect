/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Model.*;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
        KeyTypeJpaController typeController = new KeyTypeJpaController(emf);
        KeyStateJpaController stateController = new KeyStateJpaController(emf);
        
        
        
        //PRUEBA DE CREACION DE LLAVE
        //Key k = new Key(63.99, "untradeable", "Revolver");
        //keysController.create(k);
        
        //ARREGLAR CREACION DE TIPOS NO REPETIDOS
//        KeyType t1 = new KeyType("Revolver");
//        KeyType t2 = new KeyType("Croma");
//        KeyType t3 = new KeyType("Huntmans");
//        
//        typeController.create(t1);
//        typeController.create(t2);
//        typeController.create(t3);

//      //AGREGAR CREACION DE ESTADOS NO REPETIDOS  
//        KeyState s1 = new KeyState("Untradeable");
//        KeyState s2 = new KeyState("Tradeable");
//        
//        stateController.create(s1);
//        stateController.create(s2);
//         
//        KeyState fs = stateController.findKeyState("Untradeable");
//        System.out.println("El id del estado untradeable es  " + fs.getId());
//        
//        KeyType kt = typeController.findKeyType("Revolver");
//        System.out.println("El id del estado de revolver es  " + kt.getId());
//        
//        Key k = new Key();
//        k.setBuyprice(63.99);
//        k.setKeyState(fs);
//        k.setKeyType(kt);
//
//        keysController.create(k);
        
        //////////////////////////////////////////////////////
        
        //PRUEBA DE LISTAR LLAVES
//        List<Key> keys = (List<Key>) keysController.findKeyEntities();
//        Key key;
//        Iterator iter = keys.iterator();
//        while(iter.hasNext()){
//            key = (Key)iter.next(); 
//            System.out.println(key.getId() + " " + key.getKeyState().getStateDescription() + " " + key.getKeyType().getTypeDescription());
//        }
        //////////////////////////////////////////////////////
        //LISTA LAS LLAVES
//         List<Key> keys = (List<Key>) keysController.findKeyEntities();
//        Key key;
//        Iterator iter = keys.iterator();
//        while(iter.hasNext()){
//            key = (Key)iter.next(); 
//            System.out.println(key.getId() + " " + key.getKeyState().getStateDescription() + " " + key.getKeyType().getTypeDescription());
//            keysController.destroy(key.getId());
//        }
       
       //LISTA LOS TIPOS DE LLAVE
//          List<KeyType> keytype = (List<KeyType>) typeController.findKeyTypeEntities();
//          Key key2;
//          Iterator itera = keytype.iterator();
//        while(itera.hasNext()){
//            key2 = (Key)iter.next(); 
//            System.out.println(key2.getId() + " " + key2.getKeyState().getStateDescription() + " " + key2.getKeyType().getTypeDescription());
//            keysController.destroy(key2.getId());
//        }
//       
        /////////////////////////////////////////////////////
        //CUENTA LAS LLAVES EN LA BASE DE DATOS
        int cantidadkeys = keysController.getKeyCount(); 
        // List<Key> keys = (List<Key>) manager.createQuery("FROM key").getResultList();
        System.out.println("En esta base de datos hay " + cantidadkeys + " keys");
        
         //PRUEBA DE DESTRUCCION DE LLAVE
//        keysController.destroy(k.getId());
        
    }
    
    
    
}
