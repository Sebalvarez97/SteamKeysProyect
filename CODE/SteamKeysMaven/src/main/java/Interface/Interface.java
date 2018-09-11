/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Interface extends JFrame{
    
        //INICIA EL ICONO
      protected void initICon() {
//        try {
//            Image img = ImageIO.read(new File("D:\\Sebastian\\Programacion\\SteamKeysProyect\\IMAGENES\\icono.jpg"));
//            this.setIconImage(img);
//        } catch (IOException ex) {
//            MessageDialog(ex.getMessage());
//        }
    }
       //PERMITE MOSTRAR UNA ALERTA O MENSAJE EN PANTALLA
    protected void MessageDialog(String scr){
        JOptionPane.showMessageDialog(this, scr, "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
    }
    
   protected void Reload(){
        
    }
   protected void BackToInventory(){
          Inventory.CloseLastWindow();
          Inventory inventory = (Inventory) Inventory.getLastWindow();
          inventory.setLocationRelativeTo(this);
          inventory.initSaldo();
          inventory.Reload();  
          inventory.setVisible(true);
          dispose();
   } 
   
   
}
