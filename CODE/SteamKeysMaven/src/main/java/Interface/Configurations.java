/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Controller.exceptions.NonexistentEntityException;
import TransporterUnits.ParameterDTO;
import TransporterUnits.TypeStateDTO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import steam.jewishs.steamkeysmaven.KeyManager;

/**
 *
 * @author eltet
 */
public class Configurations extends Interface {
    DefaultListModel modeloestado;
    DefaultListModel modelotipos;
    //CONSTRUCTOR
    public Configurations() throws Exception {
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
        TypeTextField.setText(null);
        AddStateTextField.setText(null);
        ListStates();
        ListTypes();
    }
    protected void Reload(){
        ShowConfiguration();
    }
    private void ListTypes(){
        try {
            List<String> list = KeyManager.ListStatesTypesString(KeyManager.AlphabeticOrder(KeyManager.ListTypes()));
            modelotipos = new DefaultListModel();
            for(String dto: list){
                modelotipos.addElement(dto);
            }
            TypeList.setModel(modelotipos);
        } catch (NonexistentEntityException ex) {
            MessageDialog(ex.getMessage());
        }
    }
    private void ListStates(){
        try {
            List<String> list = KeyManager.ListStatesTypesString(KeyManager.AlphabeticOrder(KeyManager.ListStates()));
            modeloestado = new DefaultListModel();
            for(String dto: list){
                modeloestado.addElement(dto);
            }
            StateList.setModel(modeloestado);
        } catch (NonexistentEntityException ex) {
            MessageDialog(ex.getMessage());
        }
    }
    private void AddTypeState(String str, int what){
        String TyOrSt = null;
        if(!str.isEmpty()){
            if(what == 0){
                TyOrSt = "state";
            }else if(what == 1){
                TyOrSt = "type";
            }
            if(JOptionPane.showConfirmDialog(this, "Are you sure you want to create this new "+TyOrSt +"?") == 0){
                KeyManager.EnterState(str);
                MessageDialog("The "+ TyOrSt + " was succesfully created");
            }
        }else{
            MessageDialog("Empty input");
        }
        Reload();
    }
    private String getText(JTextField field){
        String ret = field.getText();
        
        return ret;
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
        StateTittle = new javax.swing.JLabel();
        StateListScrol = new javax.swing.JScrollPane();
        StateList = new javax.swing.JList<>();
        AddDelStatePanel = new javax.swing.JPanel();
        AddStateTextField = new javax.swing.JTextField();
        AddStateButton = new javax.swing.JButton();
        DeleteStateButton = new javax.swing.JButton();
        TypesTittle = new javax.swing.JLabel();
        TypeListScroll = new javax.swing.JScrollPane();
        TypeList = new javax.swing.JList<>();
        DeleteTypeButton = new javax.swing.JButton();
        AddDelTypePanel = new javax.swing.JPanel();
        TypeTextField = new javax.swing.JTextField();
        AddTypeButton = new javax.swing.JButton();

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

        StateTittle.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        StateTittle.setText("States");

        StateList.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        StateList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        StateListScrol.setViewportView(StateList);

        AddStateTextField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        AddStateTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        AddStateTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddStateTextFieldActionPerformed(evt);
            }
        });

        AddStateButton.setText("Add");
        AddStateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddStateButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddDelStatePanelLayout = new javax.swing.GroupLayout(AddDelStatePanel);
        AddDelStatePanel.setLayout(AddDelStatePanelLayout);
        AddDelStatePanelLayout.setHorizontalGroup(
            AddDelStatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddDelStatePanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(AddStateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(AddStateButton)
                .addContainerGap())
        );
        AddDelStatePanelLayout.setVerticalGroup(
            AddDelStatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddDelStatePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AddDelStatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddStateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddStateButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        DeleteStateButton.setText("Delete");

        TypesTittle.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        TypesTittle.setText("Types");

        TypeList.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TypeList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TypeListScroll.setViewportView(TypeList);

        DeleteTypeButton.setText("Delete");
        DeleteTypeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteTypeButtonActionPerformed(evt);
            }
        });

        TypeTextField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TypeTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        TypeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TypeTextFieldActionPerformed(evt);
            }
        });

        AddTypeButton.setText("Add");
        AddTypeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddTypeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddDelTypePanelLayout = new javax.swing.GroupLayout(AddDelTypePanel);
        AddDelTypePanel.setLayout(AddDelTypePanelLayout);
        AddDelTypePanelLayout.setHorizontalGroup(
            AddDelTypePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddDelTypePanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(TypeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(AddTypeButton)
                .addContainerGap())
        );
        AddDelTypePanelLayout.setVerticalGroup(
            AddDelTypePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddDelTypePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AddDelTypePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TypeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddTypeButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(ConfigurationTittle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(StateTittle)
                                    .addComponent(TypesTittle))
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(StateListScrol, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(DeleteStateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(TypeListScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(DeleteTypeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(AddDelStatePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(AddDelTypePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(309, 309, 309)
                        .addComponent(KeyPriceTittle)
                        .addGap(34, 34, 34)
                        .addComponent(KeyField, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ChangeKeyButton)))
                .addContainerGap(309, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BackButton)
                    .addComponent(ConfigurationTittle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(KeyPriceTittle)
                    .addComponent(KeyField, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ChangeKeyButton))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(StateTittle)
                    .addComponent(StateListScrol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DeleteStateButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AddDelStatePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TypesTittle)
                    .addComponent(TypeListScroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DeleteTypeButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AddDelTypePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
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

    private void AddStateTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddStateTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddStateTextFieldActionPerformed

    private void AddStateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddStateButtonActionPerformed
       AddTypeState(getText(AddStateTextField),0); // TODO add your handling code here:
    }//GEN-LAST:event_AddStateButtonActionPerformed

    private void DeleteTypeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteTypeButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DeleteTypeButtonActionPerformed

    private void AddTypeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddTypeButtonActionPerformed
       AddTypeState(getText(TypeTextField),1); // TODO add your handling code here:
    }//GEN-LAST:event_AddTypeButtonActionPerformed

    private void TypeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TypeTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TypeTextFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddDelStatePanel;
    private javax.swing.JPanel AddDelTypePanel;
    private javax.swing.JButton AddStateButton;
    private javax.swing.JTextField AddStateTextField;
    private javax.swing.JButton AddTypeButton;
    private javax.swing.JButton BackButton;
    private javax.swing.JButton ChangeKeyButton;
    private javax.swing.JLabel ConfigurationTittle;
    private javax.swing.JButton DeleteStateButton;
    private javax.swing.JButton DeleteTypeButton;
    private javax.swing.JTextField KeyField;
    private javax.swing.JLabel KeyPriceTittle;
    private javax.swing.JList<String> StateList;
    private javax.swing.JScrollPane StateListScrol;
    private javax.swing.JLabel StateTittle;
    private javax.swing.JList<String> TypeList;
    private javax.swing.JScrollPane TypeListScroll;
    private javax.swing.JTextField TypeTextField;
    private javax.swing.JLabel TypesTittle;
    // End of variables declaration//GEN-END:variables
}
