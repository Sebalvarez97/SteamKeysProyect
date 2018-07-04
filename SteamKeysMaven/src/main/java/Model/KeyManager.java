/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.EntityController;
import Controller.exceptions.NonexistentEntityException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eltet
 */
public class KeyManager {
    
    public static void EnterKey(double buyprice, String type){
        
        KeyType kt = (KeyType) EntityController.findIfExists("class Model.KeyType", type);
        KeyState ks = (KeyState) EntityController.findIfExists("class Model.KeyState", "Untradeable");
        
        if(kt != null & ks != null){
             Key k = new Key();
        k.setBuyDate();
        k.setBuyprice(buyprice);
        k.setKeyState(ks);
        k.setKeyType(kt);
        EntityController.create(k);
        }else {
            System.out.println("The key you want to create does not pair with an existing type");
        }
    }
    
    public static void DeleteKey(Key key){
        
        try {
            EntityController.destroy(key);
        } catch (NonexistentEntityException ex) {
            System.out.println("The Key you want to delete does not exist"); 
        }
        
        
    }
    
    
    
    
}
