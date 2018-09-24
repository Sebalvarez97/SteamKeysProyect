/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Controller.exceptions.NonexistentEntityException;
import TransporterUnits.TypeStateDTO;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import steam.jewishs.steamkeysmaven.KeyManager;

/**
 *
 * @author eltet
 */
public class AddKey extends Interface{

    DefaultComboBoxModel modelocombobox;
    
    DefaultComboBoxModel modelodia;
    DefaultComboBoxModel modelomes;
    DefaultComboBoxModel modeloano;
    
//CONSTRUCTOR DE LA VENTANA
    public AddKey() {
        initComponents();
        initICon();
        SetAddKeyParameters();
        this.setSize(Inventory.getLastWindow().getSize());
        this.setLocationRelativeTo(Inventory.getLastWindow());
    }

//ESTABLECE LOS PARAMETROS PARA LA CREACION DE LLAVES
private void SetAddKeyParameters(){
    
    ListDate();
    ListTypes();
    Reload();
}
protected void Reload(){
    CanBuy.setText("You can buy " + KeyManager.getCantUcanBuy(KeyManager.getBalanceMoney()));
}
// LISTA LOS TIPOS DE LLAVE  
private void ListTypes(){
        modelocombobox = new DefaultComboBoxModel();
        try {
            List<TypeStateDTO> types = KeyManager.AlphabeticOrder(KeyManager.ListTypes());
            for(TypeStateDTO dto : types){
                modelocombobox.addElement(dto.getDescription());
            }
            ComboBoxTypes.setModel(modelocombobox);
        } catch (NonexistentEntityException ex) {
            MessageDialog(ex.getMessage());
        }  
}
//LISTA LOS COMBOBOX DE LA FECHA
private void ListDate(){
    modelodia = new DefaultComboBoxModel();
    modelomes = new DefaultComboBoxModel();
    modeloano = new DefaultComboBoxModel();
    Calendar cal = Calendar.getInstance(Locale.ROOT);
    int daynow = cal.get(Calendar.DAY_OF_MONTH);
    int monthnow = cal.get(Calendar.MONTH);
    int yearnow = cal.get(Calendar.YEAR);
    
    for(int i = 1; i< 32;i++){
        modelodia.addElement(i);;
    }
    for(int i = 1; i< monthnow+2; i++){
        modelomes.addElement(i);
    }
    for(int i = yearnow; i> yearnow-10; i--){
        modeloano.addElement(i);
    }
    modelodia.setSelectedItem(daynow);
    modelomes.setSelectedItem(monthnow+1);
    modeloano.setSelectedItem(yearnow);
    DayComboBox.setModel(modelodia);
    MonthComboBox.setModel(modelomes);
    YearComboBox.setModel(modeloano);
}
    //DEVUELVE EL VALOR DE LA CANTIDAD DE LLAVES
    private int getCantValue(){
        int input = (int) CantSpinner.getValue();
        return input;
    } 

//GENERA LA LLAVE NUEVA
private void AddNewKey(){
  try{  
    String type = (String) ComboBoxTypes.getSelectedItem();
    int cantidad = getCantValue();
    if(!KeyManager.isPossibleToBuyKeys()){
        throw new Exception("Not enough money");
    }else{
       if(cantidad > 0){
         int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to create " + cantidad + " of "+ type + " key/s?");
         if (confirmation == 0){
               KeyManager.EnterSomeKeys(type, cantidad, getImputDate());
               MessageDialog("Key/s succesfully registered");
         }
        }else MessageDialog("How Many???. Please enter it");  
    }
  }catch(Exception e){
     MessageDialog(e.getMessage());
  }
  Reload();  
}//GENERA EL DATE CON LA FECHA INGRESADA
  private Date getImputDate(){
      int day = (int) DayComboBox.getSelectedItem();
      int month = (int) MonthComboBox.getSelectedItem()-1;
      int year = (int) YearComboBox.getSelectedItem();
      if(CheckBox.isSelected()){
          return KeyManager.ConvertDate(day, month, year);     
      }else{
          return KeyManager.ConvertDate(day-1, month, year);
      }       
  }  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BackButton = new javax.swing.JButton();
        NeyKeyTittle = new javax.swing.JLabel();
        ComboBoxTypes = new javax.swing.JComboBox<>();
        KeyTypeTittle = new javax.swing.JLabel();
        BuyDateTittle = new javax.swing.JLabel();
        CreateButton = new javax.swing.JButton();
        DayComboBox = new javax.swing.JComboBox<>();
        MonthComboBox = new javax.swing.JComboBox<>();
        YearComboBox = new javax.swing.JComboBox<>();
        DayTittle = new javax.swing.JLabel();
        MonthTittle = new javax.swing.JLabel();
        YearTittle = new javax.swing.JLabel();
        CheckBox = new javax.swing.JCheckBox();
        CheckboxAclaration = new javax.swing.JLabel();
        CantSpinner = new javax.swing.JSpinner();
        CantTittle = new javax.swing.JLabel();
        CanBuy = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("AddKeyFrame"); // NOI18N
        setResizable(false);

        BackButton.setText("Back");
        BackButton.setFocusable(false);
        BackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackButtonActionPerformed(evt);
            }
        });

        NeyKeyTittle.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N
        NeyKeyTittle.setText("New Key");

        ComboBoxTypes.setFocusable(false);
        ComboBoxTypes.setName("KeyTypeImput"); // NOI18N
        ComboBoxTypes.setOpaque(false);
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

        DayComboBox.setFocusable(false);
        DayComboBox.setName("DayImput"); // NOI18N

        MonthComboBox.setFocusable(false);
        MonthComboBox.setName("DayImput"); // NOI18N

        YearComboBox.setFocusable(false);
        YearComboBox.setName("DayImput"); // NOI18N

        DayTittle.setText("Day");

        MonthTittle.setText("Month");

        YearTittle.setText("Year");

        CheckBox.setSelected(true);
        CheckBox.setText("After 4:00 AM?");
        CheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckBoxActionPerformed(evt);
            }
        });

        CheckboxAclaration.setText("(It is important to set the exact day of release)");

        CantTittle.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        CantTittle.setText("Cant");

        CanBuy.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(NeyKeyTittle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 643, Short.MAX_VALUE)
                .addComponent(BackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(CreateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(KeyTypeTittle)
                            .addComponent(BuyDateTittle))
                        .addGap(51, 51, 51)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(DayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(DayTittle))
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(MonthComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(MonthTittle))
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(YearTittle)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(114, 114, 114)
                                        .addComponent(CheckboxAclaration))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(YearComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(43, 43, 43)
                                        .addComponent(CheckBox))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(94, 94, 94)
                                        .addComponent(CantTittle)
                                        .addGap(18, 18, 18)
                                        .addComponent(CantSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(CanBuy))))
                            .addComponent(ComboBoxTypes, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(89, 89, 89))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(NeyKeyTittle))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(BackButton)))
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(KeyTypeTittle)
                    .addComponent(ComboBoxTypes, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CantTittle)
                    .addComponent(CantSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CanBuy))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DayTittle)
                    .addComponent(MonthTittle)
                    .addComponent(YearTittle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BuyDateTittle)
                    .addComponent(DayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MonthComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(YearComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CheckboxAclaration)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 268, Short.MAX_VALUE)
                .addComponent(CreateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackButtonActionPerformed
  Back();
            
    }//GEN-LAST:event_BackButtonActionPerformed

    private void ComboBoxTypesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxTypesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboBoxTypesActionPerformed

    private void CreateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateButtonActionPerformed
       AddNewKey(); // TODO add your handling code here:
    }//GEN-LAST:event_CreateButtonActionPerformed

    private void CheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CheckBoxActionPerformed
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
    private javax.swing.JLabel CanBuy;
    private javax.swing.JSpinner CantSpinner;
    private javax.swing.JLabel CantTittle;
    private javax.swing.JCheckBox CheckBox;
    private javax.swing.JLabel CheckboxAclaration;
    private javax.swing.JComboBox<String> ComboBoxTypes;
    private javax.swing.JButton CreateButton;
    private javax.swing.JComboBox<String> DayComboBox;
    private javax.swing.JLabel DayTittle;
    private javax.swing.JLabel KeyTypeTittle;
    private javax.swing.JComboBox<String> MonthComboBox;
    private javax.swing.JLabel MonthTittle;
    private javax.swing.JLabel NeyKeyTittle;
    private javax.swing.JComboBox<String> YearComboBox;
    private javax.swing.JLabel YearTittle;
    // End of variables declaration//GEN-END:variables
}
