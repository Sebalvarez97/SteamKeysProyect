/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Model.*;
import java.util.List;
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
    
    
    //CUENTA LAS LLAVES EN LA BASE DE DATOS ***MAL HECHO ARREGLAR****
    public static int KeyCant(){
         return keysController.getKeyCount(); 
    }
    public static int StateCant(){
        return stateController.getKeyStateCount(); 
    }
    public static int TypeCant(){
         return typeController.getKeyTypeCount(); 
    }
    
    
       //CREA UNA ENTIDAD EN BASE DE DATOS SEGUN TIPO 
    public static void create(Key key){
           keysController.create(key); 
    }
    public static void create(KeyState ks){
        stateController.create(ks);
    }
    public static void create(KeyType kt){
        typeController.create(kt);
    }

   //DESTRUYE LAS ENTIDADES POR TIPO DE ENTIDAD 
    public static void destroy(Key key) throws NonexistentEntityException{
            keysController.destroy(key.getId());
    }
    public static void destroy(KeyState ks) throws NonexistentEntityException{
            stateController.destroy(ks.getId());
    }
    public static void destroy(KeyType kt) throws NonexistentEntityException{
            typeController.destroy(kt.getId());
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
    //OBTIENE LA LISTA DE ENTIDADES
    public static List<Key> ListKeys() throws NonexistentEntityException{
            return keysController.findKeyEntities();
    }
    public static List<KeyState> ListStates() throws NonexistentEntityException{
            return stateController.findKeyStateEntities();
    }
    public static List<KeyType> ListTypes() throws NonexistentEntityException{
            return typeController.findKeyTypeEntities();
    }
    
    public static void Edit(Key key) throws Exception{
        keysController.edit(key);
    }
    public static void Edit(KeyState ks) throws Exception{
        stateController.edit(ks);
    }
    public static void Edit(KeyType kt) throws Exception{
        typeController.edit(kt);
    }
}
