/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Model.*;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityController {
    
    //Creamos el gestor de persistencia
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("SteamKeysPU");
    private static EntityManager Ownmanager = emf.createEntityManager();
    
    private static KeyJpaController keysController = new KeyJpaController(emf);
    private static KeyTypeJpaController typeController = new KeyTypeJpaController(emf);
    private static KeyStateJpaController stateController = new KeyStateJpaController(emf);
    
    
    //CUENTA LAS LLAVES EN LA BASE DE DATOS
    public static void KeyCounter(){
        
         int cantidadkeys = keysController.getKeyCount(); 
        // List<Key> keys = (List<Key>) manager.createQuery("FROM key").getResultList();
        System.out.println("En esta base de datos hay " + cantidadkeys + " keys");
        
    }
    //CREA UNA ENTIDAD EN BASE DE DATOS SEGUN TIPO
    public static void create(Object entity){
        
        switch(entity.getClass().toString()){
            
            case "class Model.Key":
                keysController.create((Key) entity);
                break;
            case "class Model.KeyState":
                if(stateController.findIfExists((KeyState)entity)){
                    System.out.println("The state you want to enter already exists");
                }else{stateController.create((KeyState) entity);}
                break;
            case "class Model.KeyType":
                if(typeController.findIfExists((KeyType)entity)){
                    System.out.println("The type you want to enter already exists");
                }else{typeController.create((KeyType) entity);}
                break;
            default:
                System.out.println("The entity type doesn not exist");
                break;
        }
    }  
    
    //LISTA LAS ENTIDADES por tipo de entidad
    public static void List(String clase){
        
        switch(clase){
            case "Key":
                keysController.List();
                break;
            case "KeyState":
                stateController.List();
                break;
            case "KeyType":
                typeController.List();
                break;
            default:
                System.out.println("The entity you want to list does not exist");
                break;
        }
 
    }
     //DESTRUYE LAS ENTIDADES POR TIPO DE ENTIDAD   
    public static void destroy(Object entity) throws NonexistentEntityException{
        
        switch(entity.getClass().toString()){
            case "class Model.Key":
               Key k = (Key) entity;
               keysController.destroy(k.getId());
               break;
            case "class Model.KeyState":
               KeyState ks = (KeyState) entity;
               stateController.destroy(ks.getId());
               break;
            case "class Model.KeyType":
                KeyType kt = (KeyType) entity;
                typeController.destroy(kt.getId());
                break;
            default:
                System.out.println("The entity you want to destroy does not exist");
                break;
        }
    }   
    
     public static void main(String[] args) throws NonexistentEntityException{
        
//           
//         KeyState ks = new KeyState();
//         ks.setStateDescription("Untradeable");
//         KeyType kt = new KeyType();
//         kt.setTypeDescription("Revolver");
         
//         Key k = new Key();
//         k.setKeyState(ks);
//         k.setKeyType(kt);
//         k.setBuyprice(63.99);
//         
//         System.out.println(ks.getClass().toString());
//         
//         EntityController.create(ks);
//         EntityController.create(kt);
//         EntityController.create(k);
//         KeyType kt2 = new KeyType();
//         kt2.setTypeDescription("Huntsman");
//         EntityController.List("KeyState");
//         EntityController.create(kt2);  
//         EntityController.KeyCounter();
//         List("KeyType");
//         List("KeyState");
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
        
         //PRUEBA DE DESTRUCCION DE LLAVE
//        keysController.destroy(k.getId());
    
        
    }

  
    
    
}
