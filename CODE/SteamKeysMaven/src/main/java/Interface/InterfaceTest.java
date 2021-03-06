/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Controller.exceptions.NonexistentEntityException;
import TransporterUnits.KeyDTO;
import TransporterUnits.TypeStateDTO;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import steam.jewishs.steamkeysmaven.KeyManager;

/**
 *
 * @author eltet
 */
public class InterfaceTest {

    
public static void PrintAllKeys(){
    try {
        
        List<KeyDTO> dtos = KeyManager.ListKeys();
        Iterator iter = dtos.iterator();
        KeyDTO dto;
        System.out.println("____LLAVES____");
        while(iter.hasNext()){
            dto = (KeyDTO) iter.next();
            System.out.println(dto.getId() + " " + dto.getType() + " " + dto.getState());
        }
    } catch (NonexistentEntityException ex) {
        //se enviara al ExceptionManager
    }
}
public static void PrintAllTypes(){
     try {
        List<TypeStateDTO> dtos = KeyManager.ListTypes();
        Iterator iter = dtos.iterator();
        TypeStateDTO dto;
        System.out.println("____TIPOS____");
        while(iter.hasNext()){
            dto = (TypeStateDTO) iter.next();
            System.out.println(dto.getId() + " " + dto.getDescription());
        }
    } catch (NonexistentEntityException ex) {
        //se enviara al ExceptionManager
    }
}

public static void PrintCantKeys(){
    int keys = KeyManager.KeyCounter();
    System.out.println("CANTIDAD DE LLAVES " + keys);
}
public static void PrintCantStates(){
    int states = KeyManager.StateCounter();
    System.out.println("CANTIDAD DE ESTADOS " + states);
}
public static void PrintCantTypes(){
    int types = KeyManager.TypeCounter();
    System.out.println("CANTIDAD DE TIPOS " + types);
}
public static void Deletekeys(){
    try {
        KeyManager.DeleteAllKeys();
    } catch (NonexistentEntityException ex) {
        //se enviara al ExceptionManager
    }
}


}
