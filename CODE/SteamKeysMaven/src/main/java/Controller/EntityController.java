/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Model.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public interface EntityController {
    
    //Creamos el gestor de persistencia
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("SteamKeysPU");
    static EntityManager Ownmanager = emf.createEntityManager();
    
    static SteamItemJpaController itemController = new SteamItemJpaController(emf);
    static TradeJpaController tradeController = new TradeJpaController(emf);
    static SteamParametersJpaController paramController = new SteamParametersJpaController(emf); 
    static KeyJpaController keysController = new KeyJpaController(emf);
    static KeyTypeJpaController typeController = new KeyTypeJpaController(emf);
    static KeyStateJpaController stateController = new KeyStateJpaController(emf);
    static HistoryJpaController historyController = new HistoryJpaController(emf);
    
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
    public static int TradeCant(){
        return tradeController.getTradeCount();
    }
    public static int HistoryCant(){
        return historyController.getHistoryCount();
    }
    public static int ItemCant(){
        return itemController.getSteamItemCount();
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
    public static void create(SteamItem si){
        itemController.create(si);
    }
    public static void create(Trade t){
        tradeController.create(t);
    }
    public static void create(History h){
        historyController.create(h);
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
    public static void destroy(SteamItem si) throws NonexistentEntityException{
        itemController.destroy(si.getId());
    }
    public static void destroy(Trade t) throws NonexistentEntityException{
        tradeController.destroy(t.getId());
    }
    public static void destroy(History h) throws NonexistentEntityException{
        historyController.destroy(h.getId());
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
        return stateController.findIfExists(k.getStateDescription());
    }
    public static boolean findIfExist(KeyType k){
        return typeController.findIfExists(k.getTypeDescription());
    }
    public static boolean findIfExist(SteamParameters sp){
        if(find(sp) == null){
            return false;
        }else {
            return true;
        }
    }
    public static boolean finIfExist(Trade t){
        if(find(t) == null){
            return false;
        }else{
            return true;
        }
    }
    public static boolean findIfExist(SteamItem item){
        if(find(item) == null){
            return false;
        }else{
            return true;
        }
    }
    
      //ENCUENTRA LA ENTIDAD SEGUN EL TIPO 
    public static Key find(Key k){
        return keysController.findKey(k.getId());
    }
    public static KeyState find(KeyState ks){
        if(findIfExist(ks)){
            return stateController.findKeyState(ks.getStateDescription());
        }else return null;
    }
    public static SteamItem find(SteamItem item){
        return itemController.findSteamItem(item.getId());
    }
    public static KeyType find(KeyType kt){
        if(findIfExist(kt)){
            return typeController.findKeyType(kt.getTypeDescription());
        }else return null;
    }
    public static SteamParameters find(SteamParameters sp){
        return paramController.findSteamParameters(sp.getName());
    }
    public static Trade find(Trade t){
        return tradeController.findTrade(t.getId());
    }
    public static History find(History h){
        return historyController.findHistory(h.getId());
    }
    public static Trade findWithDate(Trade t){
        return tradeController.findTrade(t.getDateoftrade());
    }
    public static History findWithDate(History h){
        return historyController.findHistory(h.getDate());
    }
    public static Trade getLast(Trade t){
        Trade trade = new Trade();
        if(!EntityController.ListTrades().isEmpty()){
            trade = tradeController.findTradeEntities(1, TradeCant()-1).get(0);
        }
        return trade;
    }
    public static SteamItem getLast(SteamItem item){
        SteamItem i = new SteamItem();
        if(!EntityController.ListItems().isEmpty()){
            i = itemController.findSteamItemEntities(1, ItemCant()-1).get(0);
        }
        return i;
    }
    public static List<History> getLast(History h){
        List<History> list = new ArrayList();
        if(!EntityController.ListHistory().isEmpty()){
            list = historyController.findHistoryEntities(3, HistoryCant()-3);
        }
        return list;
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
    public static List<Trade> ListTrades(){
            return tradeController.findTradeEntities();
    }
    public static List<History> ListHistory(){
            return historyController.findHistoryEntities();
    }
    public static List<SteamItem> ListItems(){
            return itemController.findSteamItemEntities();
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
    public static void Edit(Trade t) throws Exception{
        tradeController.edit(t);
    }
    public static void Edit(History h) throws Exception{
        historyController.edit(h);
    }
    public static void Edit(SteamItem i) throws Exception{
        itemController.edit(i);
    }
}
