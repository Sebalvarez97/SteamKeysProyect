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
    public static void List(String type){
        
        switch(type){
            case "class Modle.Key":
                keysController.List();
                break;
            case "class Model.KeyState":
                stateController.List();
                break;
            case "class Model.KeyType":
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
    
    public static Object find(String type, long id){
        
        switch(type){
            case "class Model.Key":
                return keysController.findKey(id);
            case "class Model.KeyState":
                return stateController.findKeyState(id);
            case "class Model.KeyType":
                return typeController.findKeyType(id);
            default:
                return null;
        }
        
    }
    
     public static void main(String[] args) throws NonexistentEntityException{
        
           List("class Model.KeyType");
           System.out.println("Existen " + typeController.getKeyTypeCount() + " tipos de llave");
        
//         KeyType kt = new KeyType();
//         kt.setTypeDescription("Guantes");
//         create(kt);
//         KeyType kt1 = new KeyType();
//         kt1.setTypeDescription("Esports");
//         create(kt1);
//         KeyType kt2 = new KeyType();
//         kt2.setTypeDescription("Espectro 2");
//         create(kt2);
//         KeyType kt3 = new KeyType();
//         kt3.setTypeDescription("Gamma 2");
//         create(kt3);
//         KeyType kt4 = new KeyType();
//         kt4.setTypeDescription("Gamma");
//         create(kt4);
//         KeyType kt5 = new KeyType();
//         kt5.setTypeDescription("Espectro");
//         create(kt5);
//         KeyType kt6 = new KeyType();
//         kt6.setTypeDescription("Phoenix");
//         create(kt6);
//         KeyType kt7 = new KeyType();
//         kt7.setTypeDescription("Breakout");
//         create(kt7);
//         KeyType kt8 = new KeyType();
//         kt8.setTypeDescription("Alfanje");
//         create(kt8);
//         KeyType kt9 = new KeyType();
//         kt9.setTypeDescription("Sombria");
//         create(kt9);
//         KeyType k1t = new KeyType();
//         k1t.setTypeDescription("Croma 3");
//         create(k1t);
//         KeyType k2t = new KeyType();
//         k2t.setTypeDescription("Wildfire");
//         create(k2t);
//         KeyType k3t = new KeyType();
//         k3t.setTypeDescription("Croma");
//         create(k3t);
//         KeyType k4t = new KeyType();
//         k4t.setTypeDescription("Ofensiva Invernal");
//         create(k4t);
//         KeyType k5t = new KeyType();
//         k5t.setTypeDescription("Vanguard");
//         create(k5t);
//         KeyType k6t = new KeyType();
//         k6t.setTypeDescription("Hydra");
//         create(k6t);
//         KeyType k7t = new KeyType();
//         k7t.setTypeDescription("CS:GO");
//         create(k7t);
//         KeyType k8t = new KeyType();
//         k8t.setTypeDescription("capsula pegatinas comunidad 1");
//         create(k8t);
//         KeyType k9t = new KeyType();
//         k9t.setTypeDescription("capsula CS:GO");
//         create(k9t);
//         
      
//         KeyType kt = new KeyType();
//         kt.setTypeDescription("Clutch");
//         KeyType kt = (KeyType)find("class Model.KeyType", 418);
//         System.out.println("El tipo de llave correspondiente al numero 418 es "+ kt.getTypeDescription());
           
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
