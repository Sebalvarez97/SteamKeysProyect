/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import TransporterUnits.ParameterDTO;
import javax.swing.JOptionPane;
import steam.jewishs.steamkeysmaven.KeyManager;

/**
 *
 * @author eltet
 */
public class Configurations extends Interface {

    //CONSTRUCTOR
    public Configurations() {
        initComponents();
        initICon();
        ShowConfiguration();
        this.setSize(Inventory.getLastWindow().getSize());
        this.setLocationRelativeTo(Inventory.getLastWindow()); 
    }
    //DEVUELVE EL VALOR INGRESADO EN EL CAMPO DE LA LLAVE
    private int getKeyFieldInput() throws Exception{
        String input = KeyField.getText();
        return KeyManager.numberConvertor(input);
    }
    //MUESTRA LA CONFIGURACION
    private void ShowConfiguration(){     
        int keyprice = KeyManager.getKeyPrice();
        KeyField.setText(KeyManager.numberConvertor(keyprice));
    }
    //CAMBIA EL VALOR DE LA KEY
    private void ChangeKeyValue(){
        try {
            int value = getKeyFieldInput();
            int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to change it?");
            if(confirmation == 0){
                KeyManager.UpdateKeyPrice(value);
            }else{
                ShowConfiguration();
            }
        } catch (Exception ex) {
            ShowConfiguration();
            MessageDialog("You entered a bad input");
        }
    }
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
          BackToInventory();
           // TODO add your handling code here:
    }//GEN-LAST:event_BackButtonActionPerformed

    private void ChangeKeyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChangeKeyButtonActionPerformed
       ChangeKeyValue(); // TODO add your handling code here:
    }//GEN-LAST:event_ChangeKeyButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackButton;
    private javax.swing.JButton ChangeKeyButton;
    private javax.swing.JLabel ConfigurationTittle;
    private javax.swing.JTextField KeyField;
    private javax.swing.JLabel KeyPriceTittle;
    // End of variables declaration//GEN-END:variables
}
