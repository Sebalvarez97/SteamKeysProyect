/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import TransporterUnits.KeyDTO;
import steam.jewishs.steamkeysmaven.KeyManager;

/**
 *
 * @author eltet
 */
public class InterfaceTest {
//  NO PUEDE TENER NADA QUE VER CON LA BASE DE DATOS
//  DEBEMOS DEFINIR UN TRANSPORTER PARA NO TRABAJAR CON CLASES DEL MODELO    
public static void PrintKeys(){
    int keys = KeyManager.KeyCounter();
    System.out.println("CANTIDAD DE LLAVES " + keys);
}
public static void PrintStates(){
    int states = KeyManager.StateCounter();
    System.out.println("CANTIDAD DE ESTADOS " + states);
}
public static void PrintTypes(){
    int types = KeyManager.TypeCounter();
    System.out.println("CANTIDAD DE TIPOS " + types);
}
public static void main(String[] args){
         
          
         
         //KeyManager.EnterKey(new KeyDTO(63.99, "Breakout", "Untradeable"));
         
          
          PrintKeys();
          PrintStates();
          PrintTypes();
          
         System.out.println("FIN");
          
          
          //ShowListKeys();
//         KeyManager.EnterKey(new KeyTU(63.99, "Gamma", "Untradeable"));
////         KeyManager.KeyCounter();
//         ShowListKeys();
         //ShowListKeys();
    
  //List(new KeyType());
  //create(new Key(63.99, find(new KeyState("Untradeable")), find(new KeyType("Revolver"))));          
            //KeyManager.EnterKey(63.99, "Croma");
           // KeyManager.DeleteKey(737);
//            KeyManager.EnterKey(63.99, "Revolver");
//            KeyManager.EnterKey(63.99, "Croma 2");
//            KeyManager.EnterKey(63.99, "Espectro");
        
//        //KeyManager.EnterKey(63.99, "Espectro 2");
//List(new Key());
//KeyCounter();
//        
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
