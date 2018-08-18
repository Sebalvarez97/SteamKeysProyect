/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import TransporterUnits.ParameterDTO;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import steam.jewishs.steamkeysmaven.KeyManager;

/**
 *
 * @author eltet
 */
public class Configurations extends javax.swing.JFrame {

    /**
     * Creates new form Configurations
     */
    public Configurations() {
        initComponents();
        ShowConfiguration();
        this.setSize(Inventory.getLastWindow().getSize());
        this.setLocationRelativeTo(Inventory.getLastWindow()); 
    }

    private boolean isDouble(String str) {
        return (str.matches("[+-]?\\d*(\\.\\d+)?") && str.equals("")==false);
    }
    private double getDoubleInput() throws Exception{
        String input = KeyField.getText();
        if(isDouble(input)){
            return Double.parseDouble(input);
        }else{
            throw new Exception("bad enter");
        }
    } 
    private void MessageDialog(String scr){
        JOptionPane.showMessageDialog(this, scr);
    }
    
    private void ShowConfiguration(){
        ParameterDTO dto = KeyManager.findParameter("KeysPrice");
        double keyprice = dto.getValue();
        KeyField.setText(String.valueOf(keyprice));
    }
    private void ChangeKeyValue(){
        try {
            double value = getDoubleInput();
            ParameterDTO pd = KeyManager.findParameter("KeysPrice");
            int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to change it?");
            if(confirmation == 0){
                pd.setValue(value);
                KeyManager.setValue(pd);
            }else{
                ShowConfiguration();
            }
        } catch (Exception ex) {
            ShowConfiguration();
            MessageDialog("You entered a bad input");
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BackButton = new javax.swing.JButton();
        ConfigurationTittle = new javax.swing.JLabel();
        KeyPriceTittle = new javax.swing.JLabel();
        KeyField = new javax.swing.JTextField();
        ChangeKeyButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        BackButton.setText("Back");
        BackButton.setFocusable(false);
        BackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackButtonActionPerformed(evt);
            }
        });

        ConfigurationTittle.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N
        ConfigurationTittle.setText("Configuration");

        KeyPriceTittle.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        KeyPriceTittle.setText("KeysPrice");

        KeyField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        ChangeKeyButton.setText("Change");
        ChangeKeyButton.setFocusable(false);
        ChangeKeyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChangeKeyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(ConfigurationTittle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(KeyPriceTittle)
                .addGap(34, 34, 34)
                .addComponent(KeyField, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(ChangeKeyButton)
                .addContainerGap(308, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BackButton)
                    .addComponent(ConfigurationTittle))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(KeyPriceTittle)
                    .addComponent(KeyField, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ChangeKeyButton))
                .addContainerGap(347, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackButtonActionPerformed
          Inventory.CloseLastWindow();
          Inventory inventory = (Inventory) Inventory.getLastWindow();
          inventory.setLocationRelativeTo(this);
          inventory.ReloadTable();  
          inventory.setVisible(true);
          dispose();
           // TODO add your handling code here:
    }//GEN-LAST:event_BackButtonActionPerformed

    private void ChangeKeyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChangeKeyButtonActionPerformed
       ChangeKeyValue(); // TODO add your handling code here:
    }//GEN-LAST:event_ChangeKeyButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Configurations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Configurations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Configurations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Configurations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Configurations().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackButton;
    private javax.swing.JButton ChangeKeyButton;
    private javax.swing.JLabel ConfigurationTittle;
    private javax.swing.JTextField KeyField;
    private javax.swing.JLabel KeyPriceTittle;
    // End of variables declaration//GEN-END:variables
}
