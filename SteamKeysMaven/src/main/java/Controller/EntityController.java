/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
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
         System.out.println("En esta base de datos hay " + cantidadkeys + " keys");
        
    }
    
       //CREA UNA ENTIDAD EN BASE DE DATOS SEGUN TIPO 
    public static void create(Key key) throws PreexistingEntityException{
        
           if(findIfExist(key)){
               throw new PreexistingEntityException("Key Creation duplicate");}
           else{ 
           keysController.create(key); 
        }
    }
    public static void create(KeyState ks) throws PreexistingEntityException{
        
            if(findIfExist(ks)){
              throw new PreexistingEntityException("KeyState Creation failed");
            }else{
                stateController.create(ks);
            }
    }
    public static void create(KeyType kt) throws PreexistingEntityException{
        
            if(findIfExist(kt)){
                throw new PreexistingEntityException("KeyType Creation failed");
            }else{
                typeController.create(kt);
            }
    }
    
    
    
   //DESTRUYE LAS ENTIDADES POR TIPO DE ENTIDAD 
    public static void destroy(Key key) throws NonexistentEntityException{
        
        if(!findIfExist(key)){
            throw new NonexistentEntityException("Key destruction failed");
        }else{
            keysController.destroy(key.getId());
        }  
    }
    public static void destroy(KeyState ks) throws NonexistentEntityException{
        
        if(!findIfExist(ks)){
            throw new NonexistentEntityException("KeyState destruction failed");
        }else{
            stateController.destroy(ks.getId());
        }
    }
    public static void destroy(KeyType kt) throws NonexistentEntityException{
        if(!findIfExist(kt)){
            throw new NonexistentEntityException("KeyType destruction failed");
        }else{
            typeController.destroy(kt.getId());
        }
    }
     
    //BUSCA SI EXISTE LA ENTIDAD 
    public static boolean findIfExist(Key k){
        if(find(k) == null){
            return false;
        }else {
            return true;
        }
    }
    public static boolean findIfExist(KeyState k){
        if(find(k) == null){
            return false;
        }else {
            return true;
        }
    }
    
    public static boolean findIfExist(KeyType k){
        if(find(k) == null){
            return false;
        }else {
            return true;
        }
    }
      //ENCUENTRA LA ENTIDAD SEGUN EL TIPO 
    public static Key find(Key k){
        return keysController.findKey(k.getId());
    }
    public static KeyState find(KeyState ks){
        return stateController.findKeyState(ks.getStateDescription());
    }
    public static KeyType find(KeyType kt){
        return typeController.findKeyType(kt.getTypeDescription());
    }
    public static void List(Key k){
        keysController.List();
    }
    public static void List(KeyState ks){
        stateController.List();
    }
    public static void List(KeyType kt){
        typeController.List();
    }
     public static void main(String[] args) throws NonexistentEntityException, PreexistingEntityException{
//        
    //List(new KeyType());
            
//           KeyManager.EnterKey(63.99, "Croma");
//            KeyManager.EnterKey(63.99, "Revolver");
//            KeyManager.EnterKey(63.99, "Croma 2");
//            KeyManager.EnterKey(63.99, "Espectro");
          //List("class Model.KeyType");
        //KeyManager.EnterKey(63.99, "Espectro 2");
List(new Key());
        //List("class Model.KeyState");
          //KeyCounter();
//         KeyState ks = new KeyState();
//         ks.setStateDescription("Tradeable");
//         create(ks); 
//         KeyType kt = new KeyType();
//         List("class Model.KeyType");
//         System.out.println("Existen " + count(kt.getClass().toString()) + " tipos de llave");
//           List("class Model.KeyState");
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
