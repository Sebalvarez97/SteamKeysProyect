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
import TransporterUnits.KeyTU;
import TransporterUnits.TypeStateTU;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eltet
 */
public class KeyManager { 
    
    public static void EnterKey(KeyTU ktu){
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

    public static void DeleteKey(KeyTU ktu){
        Key key = new Key();
        key.setId(ktu.getId());
        try {
            EntityController.destroy(key);
        } catch (NonexistentEntityException ex) {
           //se enviaran al ExceptionManager
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
     public static void main(String[] args) throws PreexistingEntityException {
      
        // EntityController.create(new KeyType("Croma 2"));
//         EntityController.create(new KeyType("Croma"));
//         EntityController.create(new KeyType("Croma 3"));
//         EntityController.create(new KeyType("Esports"));
//         EntityController.create(new KeyType("Guantes"));
//         EntityController.create(new KeyType("Espectro"));
//         EntityController.create(new KeyType("Espectro 2"));
//         EntityController.create(new KeyType("Clutch"));
//         EntityController.create(new KeyType("Gamma"));
//         EntityController.create(new KeyType("Gamma 2"));
//         EntityController.create(new KeyType("Wildfire"));
//         EntityController.create(new KeyType("Phoenix"));
//         EntityController.create(new KeyType("Sombria"));
//         EntityController.create(new KeyType("CS:GO"));
//         EntityController.create(new KeyType("Cazador"));
//         EntityController.create(new KeyType("Revolver"));
//         EntityController.create(new KeyType("Breakout"));
//         EntityController.create(new KeyType("Alfanje"));
//         EntityController.create(new KeyType("Vanguard"));
//         EntityController.create(new KeyType("Invernal Offensive"));
//         EntityController.create(new KeyType("Hydra"));
//         EntityController.create(new KeyType("cápsula comunidad 1"));
//         EntityController.create(new KeyType("cápsula CS:GO"));
//
//EntityController.create(new KeyState("Untradeable"));
//EntityController.create(new KeyState("Tradeable"));

//EnterKey(new KeyTU(63.99,"Revolver", "Untradeable"));


            
       
        
     }
    
}
