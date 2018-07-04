/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.EntityController;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eltet
 */
public class KeyManager {//"""""""NO ESTA FUNCIONANDO"""""""
    
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
    
    public static void DeleteKey(Key key){
      
    }
    
    
    
    
}
