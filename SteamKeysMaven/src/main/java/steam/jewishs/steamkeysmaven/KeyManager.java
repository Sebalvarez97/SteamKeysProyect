/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steam.jewishs.steamkeysmaven;

import Controller.EntityController;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Model.Key;
import Model.KeyState;
import Model.KeyType;
import TransporterUnits.*;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eltet
 */
public class KeyManager { 
 //INGRESA UNA NUEVA KEY
    public static void EnterKey(KeyDTO ktu){
        try{
         KeyState ks = EntityController.find(new KeyState(ktu.getState()));
         KeyType kt = EntityController.find(new KeyType(ktu.getType()));
        if(ks != null && kt != null){ 
            Key k = new Key();
            k.setBuyDate();
            k.setBuyprice(ktu.getBuyprice());
            k.setKeyState(ks);
            k.setKeyType(kt);
            EntityController.create(k);
        }else {
            throw new NonexistentEntityException("The type or the state does not exist");
        }
        }catch(NonexistentEntityException Ne){
            //se enviaran al ExceptionManager
        }
    }
//BORRA LA KEY
    public static void DeleteKey(KeyDTO ktu){
        Key key = new Key();
        key.setId(ktu.getId());
        try {
            EntityController.destroy(key);
        } catch (NonexistentEntityException ex) {
           //se enviaran al ExceptionManager
        }  
    }
    //DEVUELVE UNA LISTA DE LAS LLAVES PARA LA INTERFAZ
    public static List<KeyDTO> ListKeys() throws NonexistentEntityException{
        List<Key> keys = EntityController.ListKeys();
        Key key;
        List<KeyDTO> dtos = new ArrayList();
        Iterator iter = keys.iterator();
        while(iter.hasNext()){
            key = (Key) iter.next();
            KeyDTO dto = new KeyDTO(63.99, key.getKeyType().getTypeDescription(), key.getKeyState().getStateDescription());
            dto.setId(key.getId());
            dtos.add(dto);
        }
        return dtos;
    }
    //DEVUELVE UNA LISTA DE LOS TIPOS DE LLAVE EXISTENTES
    public static List<TypeStateDTO> ListTypes() throws NonexistentEntityException{
        List<KeyType> types = EntityController.ListTypes();
        KeyType type;
        List<TypeStateDTO> dtos = new ArrayList();
        Iterator iter = types.iterator();
        while(iter.hasNext()){
            type = (KeyType) iter.next();
            TypeStateDTO dto = new TypeStateDTO(type.getId(), type.getTypeDescription());
            dtos.add(dto);
        }
        return dtos;
    }
    //DEVUELVE UNA LISTA CON LOS ESTADOS
        public static List<TypeStateDTO> ListStates() throws NonexistentEntityException{
        List<KeyState> states = EntityController.ListStates();
        KeyState state;
        List<TypeStateDTO> dtos = new ArrayList();
        Iterator iter = states.iterator();
        while(iter.hasNext()){
            state = (KeyState) iter.next();
            TypeStateDTO dto = new TypeStateDTO(state.getId(), state.getStateDescription());
            dtos.add(dto);
        }
        return dtos;
    }
        
    //DESTRUYE TODAS LAS LLAVES
    public static void DeleteAllKeys() throws NonexistentEntityException{
        List<Key> keys = EntityController.ListKeys();
        Key key;
        Iterator iter = keys.iterator();
        while(iter.hasNext()){
            key = (Key) iter.next();
            EntityController.destroy(key);
        } 
    }
    
  public static int KeyCounter(){
     return EntityController.KeyCant();
  }
  public static int StateCounter(){
     return EntityController.StateCant();
  }
  public static int TypeCounter(){
     return EntityController.TypeCant();
  }
  
  
  
  //QUIERO QUE ESTE SEA EL MAIN DE LA APP
     public static void main(String[] args) {
       
        
     }
    
}
