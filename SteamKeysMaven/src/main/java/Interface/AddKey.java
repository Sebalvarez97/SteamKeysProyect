/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Controller.exceptions.NonexistentEntityException;
import TransporterUnits.KeyDTO;
import TransporterUnits.ParameterDTO;
import TransporterUnits.TypeStateDTO;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        this.setLocationRelativeTo(Inventory.getLastWindow());
    }

//ESTABLECE LOS PARAMETROS PARA LA CREACION DE LLAVES
private void SetAddKeyParameters(){
    
    ListDate();
    ListTypes();
    
}  
// LISTA LOS TIPOS DE LLAVE  
private void ListTypes(){
        modelocombobox = new DefaultComboBoxModel();
        try {
            List<TypeStateDTO> types = KeyManager.AlphabeticOrder(KeyManager.ListTypes());
            Iterator iter = types.iterator();
            String type = new String();
            TypeStateDTO dto;
            while(iter.hasNext()){
                dto = (TypeStateDTO) iter.next();
                type = dto.getDescription();
                modelocombobox.addElement(type);
            }
            ComboBoxTypes.setModel(modelocombobox);
        } catch (NonexistentEntityException ex) {
            System.out.println("FALLA");Logger.getLogger(AddKey.class.getName()).log(Level.SEVERE, null, ex);
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
    for(int i = 1; i< 13; i++){
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
    //DEVUELVE EL VALOR DE LA CANTIDAD DE LLAVES VALIDADO
    private int getCantValue() throws Exception{
        int input = (int) CantSpinner.getValue();
        return input;
    } 

//GENERA LA LLAVE NUEVA
private void AddNewKey(){
  try{  
    String type = (String) ComboBoxTypes.getSelectedItem();
    String state = "Untradeable";
    int cantidad = getCantValue();
    int keyprice = KeyManager.findParameter("KeysPrice").getValue();
    int total = cantidad * keyprice;
    int saldo = KeyManager.findParameter("Saldo").getValue();
    if(saldo-total <0){
        throw new Exception("Not enough money");
    }else{
         int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to create " + cantidad + " of "+ type + " key/s?");
         if (confirmation == 0){
            int i = 1;
        while(i<=cantidad){
            KeyDTO ktu = new KeyDTO();
            ktu.setState(state);
            ktu.setType(type);
            ktu.setbuydate(getImputDate());
            KeyManager.EnterKey(ktu);
            i++;
        }
         KeyManager.setValue(new ParameterDTO("Saldo", "", saldo-total));
    }
    }
   
  }catch(Exception e){
     MessageDialog(e.getMessage());
  }
    
}//GENERA EL CALENDARIO CON LA FECHA INGRESADA
  private Date getImputDate(){
      
      int day = (int) DayComboBox.getSelectedItem();
      int month = (int) MonthComboBox.getSelectedItem()-1;
      int year = (int) YearComboBox.getSelectedItem();
      Date returnated = new Date();
      
      Calendar cal = Calendar.getInstance();
      cal.clear();
      if(CheckBox.isSelected()){
          cal.set(year, month, day);
          
      }else{
          cal.set(year,month,day-1);
      }
      returnated = cal.getTime();
     return returnated;      
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

        CheckBox.setText("After 4:00 AM?");
        CheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckBoxActionPerformed(evt);
            }
        });

        CheckboxAclaration.setText("(It is important to set the exact day of release)");

        CantTittle.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        CantTittle.setText("Cant");

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
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(CreateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))
                    .addGroup(layout.createSequentialGroup()
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
                                        .addComponent(CantSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(ComboBoxTypes, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(NeyKeyTittle)
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(KeyTypeTittle)
                            .addComponent(ComboBoxTypes, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CantTittle)
                            .addComponent(CantSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(BackButton)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 154, Short.MAX_VALUE)
                .addComponent(CreateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackButtonActionPerformed
  BackToInventory();
            
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
