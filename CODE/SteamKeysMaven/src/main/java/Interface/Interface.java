/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import steam.jewishs.steamkeysmaven.KeyManager;


public class Interface extends JFrame{
    
        //INICIA EL ICONO
      protected void initICon() {
//        try {
//            Image img = ImageIO.read(new File("D:\\Sebastian\\Programacion\\SteamKeysProyect\\IMAGENES\\icono.jpg"));
//            this.setIconImage(img);
//        } catch (IOException ex) {
//            MessageDialog(ex.getMessage());
//    }
    }
       //PERMITE MOSTRAR UNA ALERTA O MENSAJE EN PANTALLA
    protected void MessageDialog(String scr){
        JOptionPane.showMessageDialog(this, scr, "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
    }
    protected boolean ValidateUser(){
        String contraseña = KeyManager.getGlobalPassword();
        boolean ret = false;
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter a password:");
        JPasswordField pass = new JPasswordField(10);
        panel.add(label);
        panel.add(pass);
        String[] options = new String[]{"OK", "Cancel"};
        int option = JOptionPane.showOptionDialog(this, panel, "The title",
                                 JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                                 null, options, options[1]);
        if(option == 0) // pressing OK button
        {   ret = true;
            char[] password = pass.getPassword();
            if(password.length == contraseña.length()){
                for(int i = 0; i<password.length; i++){
                    if(password[i]!= contraseña.charAt(i)){
                        ret = false;
                    }
                }
            }else ret = false;
            
        }
        return ret;
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
