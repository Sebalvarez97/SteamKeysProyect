/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Controller.exceptions.NonexistentEntityException;
import TransporterUnits.KeyDTO;
import TransporterUnits.TypeStateDTO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import steam.jewishs.steamkeysmaven.KeyManager;

/**
 *
 * @author eltet
 */
public class AddKey extends javax.swing.JFrame {

    DefaultComboBoxModel modelocombobox;
    
    
    public AddKey() {
        initComponents();
        SetAddKeyParameters();
        this.setLocationRelativeTo(null);
        
    }
private void SetAddKeyParameters(){
    modelocombobox = new DefaultComboBoxModel();
    ListTypes();
    ComboBoxTypes.setModel(modelocombobox);
}  
    
private void ListTypes(){
        try {
            List<TypeStateDTO> types = KeyManager.ListTypes();
            Iterator iter = types.iterator();
            String type = new String();
            TypeStateDTO dto;
            while(iter.hasNext()){
                dto = (TypeStateDTO) iter.next();
                type = dto.getDescription();
                modelocombobox.addElement(type);
            }
        } catch (NonexistentEntityException ex) {
            System.out.println("FALLA");Logger.getLogger(AddKey.class.getName()).log(Level.SEVERE, null, ex);
        }
}
private void AddNewKey(){
  try{  
    String type = (String) ComboBoxTypes.getSelectedItem();
    //double price = 63.99;
    
    double price = Double.parseDouble(BuyPriceImput.getText());
    
    String state = "Untradeable";
    int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to create a " + type + " key with " +price + " price?");
    if (confirmation == 0){
        KeyManager.EnterKey(new KeyDTO(price, type, state));
    }
  }catch(Exception e){
      System.out.println("FALLA");
  }
   
    
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BackButton = new javax.swing.JButton();
        NeyKeyTittle = new javax.swing.JLabel();
        BuyPriceTittle = new javax.swing.JLabel();
        ComboBoxTypes = new javax.swing.JComboBox<>();
        KeyTypeTittle = new javax.swing.JLabel();
        BuyDateTittle = new javax.swing.JLabel();
        CreateButton = new javax.swing.JButton();
        BuyPriceImput = new javax.swing.JTextField();
        buypriceMessage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("AddKeyFrame"); // NOI18N
        setUndecorated(true);
        setResizable(false);

        BackButton.setText("Back");
        BackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackButtonActionPerformed(evt);
            }
        });

        NeyKeyTittle.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N
        NeyKeyTittle.setText("New Key");

        BuyPriceTittle.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        BuyPriceTittle.setText("BuyPrice");

        ComboBoxTypes.setName("KeyTypeImput"); // NOI18N
        ComboBoxTypes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxTypesActionPerformed(evt);
            }
        });

        KeyTypeTittle.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        KeyTypeTittle.setText("KeyType");

        BuyDateTittle.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        BuyDateTittle.setText("BuyDate");

        CreateButton.setText("CREATE");
        CreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateButtonActionPerformed(evt);
            }
        });

        BuyPriceImput.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        BuyPriceImput.setName("BuyPriceImput"); // NOI18N
        BuyPriceImput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuyPriceImputActionPerformed(evt);
            }
        });

        buypriceMessage.setText("(use . to separate decimal part)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(NeyKeyTittle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(KeyTypeTittle)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(BuyPriceTittle)
                                    .addGap(47, 47, 47)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(BuyPriceImput, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(46, 46, 46)
                                            .addComponent(buypriceMessage))
                                        .addComponent(ComboBoxTypes, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(BuyDateTittle)
                                .addGap(299, 299, 299)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 213, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CreateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(37, 37, 37))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(NeyKeyTittle)
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BuyPriceTittle, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BuyPriceImput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buypriceMessage))
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(KeyTypeTittle)
                            .addComponent(ComboBoxTypes, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(BackButton)))
                .addGap(47, 47, 47)
                .addComponent(BuyDateTittle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
                .addComponent(CreateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackButtonActionPerformed
            this.setVisible(false);// TODO add your handling code here:
    }//GEN-LAST:event_BackButtonActionPerformed

    private void ComboBoxTypesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxTypesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboBoxTypesActionPerformed

    private void CreateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateButtonActionPerformed
       AddNewKey(); // TODO add your handling code here:
    }//GEN-LAST:event_CreateButtonActionPerformed

    private void BuyPriceImputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuyPriceImputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BuyPriceImputActionPerformed
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(AddKey.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(AddKey.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(AddKey.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(AddKey.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new AddKey().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackButton;
    private javax.swing.JLabel BuyDateTittle;
    private javax.swing.JTextField BuyPriceImput;
    private javax.swing.JLabel BuyPriceTittle;
    private javax.swing.JComboBox<String> ComboBoxTypes;
    private javax.swing.JButton CreateButton;
    private javax.swing.JLabel KeyTypeTittle;
    private javax.swing.JLabel NeyKeyTittle;
    private javax.swing.JLabel buypriceMessage;
    // End of variables declaration//GEN-END:variables
}
