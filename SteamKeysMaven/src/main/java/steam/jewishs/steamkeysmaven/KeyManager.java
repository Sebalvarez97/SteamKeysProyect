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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eltet
 */
public class KeyManager { 
    
    public static void EnterKey(double buyprice, String type){
        try{
         KeyState ks = EntityController.find(new KeyState("Untradeable"));
         KeyType kt = EntityController.find(new KeyType(type));
        if(ks != null && kt != null){ 
            Key k = new Key();
            k.setBuyDate();
            k.setBuyprice(buyprice);
            k.setKeyState(ks);
            k.setKeyType(kt);
            EntityController.create(k);
        }else {
            throw new NonexistentEntityException("The type or the state does not exist");
        }
        }catch(NonexistentEntityException Ne){
            //se enviaran al ExceptionManager
        }catch(PreexistingEntityException Pe){
            //se enviaran al ExceptionManager
        }

    }

    public static void DeleteKey(long id){
        Key key = new Key();
        key.setId(id);
        try {
            EntityController.destroy(key);
        } catch (NonexistentEntityException ex) {
           //se enviaran al ExceptionManager
        }
        
    }
    
  public static List<Key> ListKeys(){
        try {
            return EntityController.List(new Key());
        } catch (NonexistentEntityException ex) {
            System.out.println("NO EXISTEN LLAVES");//se enviara en ExceptionManager
            return null;
        }  
  }  
  public static List<KeyType> ListTypes(){
      try{
          return EntityController.List(new KeyType());
      }catch(NonexistentEntityException ex){
          System.out.println("NO EXISTEN TIPOS");//se enviara al ExceptionManager
          return null;
      }
  } 
  //QUIERO QUE ESTE SEA EL MAIN DE LA APP
     public static void main(String[] args){
     
     
     }
    
}
