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

public interface EntityController {
    
    //Creamos el gestor de persistencia
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("SteamKeysPU");
    static EntityManager Ownmanager = emf.createEntityManager();
    
    static KeyJpaController keysController = new KeyJpaController(emf);
    static KeyTypeJpaController typeController = new KeyTypeJpaController(emf);
    static KeyStateJpaController stateController = new KeyStateJpaController(emf);
    
    
    //CUENTA LAS LLAVES EN LA BASE DE DATOS
    public static void KeyCounter(){
        
         int cantidadkeys = keysController.getKeyCount(); 
         System.out.println("En esta base de datos hay " + cantidadkeys + " keys");
        
    }
    
       //CREA UNA ENTIDAD EN BASE DE DATOS SEGUN TIPO 
    public static void create(Key key) throws PreexistingEntityException{
        
           keysController.create(key); 
        
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
    public static List<Key> List(Key k) throws NonexistentEntityException{
        
        if(keysController.findKeyEntities().isEmpty()){
            throw new NonexistentEntityException("There are any keys avaliable");
        }else{
            return keysController.findKeyEntities();
        }
        
    }
    public static List<KeyState> List(KeyState ks) throws NonexistentEntityException{
        if(stateController.findKeyStateEntities().isEmpty()){
            throw new NonexistentEntityException("There are any keystate avaliable");
        }else{
            return stateController.findKeyStateEntities();
        }
    }
    public static List<KeyType> List(KeyType kt) throws NonexistentEntityException{
        if(typeController.findKeyTypeEntities().isEmpty()){
            throw new NonexistentEntityException("There are any keytype avaliable");
        }else{
            return typeController.findKeyTypeEntities();
        }
    }
    
}
