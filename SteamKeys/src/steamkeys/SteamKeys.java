/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steamkeys;
import Model.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import Controller.*;
/**
 *
 * @author eltet
 */
public class SteamKeys {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Calendar c = Calendar.getInstance();
        String dia = Integer.toString(c.get(Calendar.DATE));
        String mes = Integer.toString(c.get(Calendar.MONTH));
        String annio = Integer.toString(c.get(Calendar.YEAR)); 
        
        Date d = new Date();
        
        d.setDate(Calendar.DATE);
        d.setMonth(Calendar.MONTH);
        d.setYear(Calendar.YEAR);
        
        
        System.out.println(dia + "  " + mes + "  " + annio);
        
        KeysType t1 = new KeysType( "Revolver");
        
        System.out.println("CREADA   " + t1.getTipeDescription());
        
        KeysState s1 = new KeysState(10, "NonComerciable");
        
        Keys key = new Keys(63.99 , t1 ,d ,s1 );
        
//        Operation op = new Operation();
//        
//        op.CreateKey(key);
//        
       // KeysJpaController KController = new KeysJpaController();
        
        
        
    }
    
}
