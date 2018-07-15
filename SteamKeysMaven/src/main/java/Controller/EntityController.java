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
    
    static SteamParametersJpaController paramController = new SteamParametersJpaController(emf); 
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
    public static int ParameterCant(){
        return paramController.getSteamParametersCount();
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
    public static void create(SteamParameters sp){
        paramController.create(sp);
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
    public static void destroy(SteamParameters sp) throws NonexistentEntityException{
            paramController.destroy(sp.getId());
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
    public static boolean findIfExist(SteamParameters sp){
        if(find(sp) == null){
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
    public static SteamParameters find(SteamParameters sp){
        return paramController.findSteamParameters(sp.getName());
    }
    
    //OBTIENE LA LISTA DE ENTIDADES
    public static List<Key> ListKeys(){
            return keysController.findKeyEntities();
    }
    public static List<KeyState> ListStates(){
            return stateController.findKeyStateEntities();
    }
    public static List<KeyType> ListTypes(){
            return typeController.findKeyTypeEntities();
    }
    public static List<SteamParameters> ListParameters(){
            return paramController.findSteamParametersEntities();
    }
    //EDITA LA ENTIDAD
    public static void Edit(Key key) throws Exception{
        keysController.edit(key);
    }
    public static void Edit(KeyState ks) throws Exception{
        stateController.edit(ks);
    }
    public static void Edit(KeyType kt) throws Exception{
        typeController.edit(kt);
    }
    public static void Edit(SteamParameters sp) throws Exception{
        paramController.edit(sp);
    }
}
