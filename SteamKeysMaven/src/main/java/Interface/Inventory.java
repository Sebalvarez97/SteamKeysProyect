
package Interface;

import Controller.exceptions.NonexistentEntityException;
import TransporterUnits.KeyDTO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import steam.jewishs.steamkeysmaven.KeyManager;


public class Inventory extends javax.swing.JFrame {
    
 
    
    DefaultTableModel modelotabla;
    
    public Inventory() {

        
        initComponents();
        ReloadTable();
       
        this.setLocationRelativeTo(null); 
    }
    //CONFIGURACION DE LA TABLA
    private void SetKeyTable(){
        KeyTable.setModel(modelotabla);
        KeyTable.getTableHeader().setResizingAllowed(false);//DESABILITA LA EDICION DE LAS COLUMNAS
        TableColumn columnaid = KeyTable.getColumn("ID");
        columnaid.setPreferredWidth(40);
        columnaid.setMaxWidth(100);
        
    }
    private void ReloadTable(){
        ListTable();
        SetKeyTable();
    }
            
    //CONFIGURA EL MODELO DE LA TABLA DE LLAVES
    private void ListTable(){
           try {
            List<KeyDTO> keys = KeyManager.ListKeys();
            Iterator iter = keys.iterator();
            KeyDTO dto;
            //CONFIGURACION DEL MODELO DE LA TABLA
            String[] columnames = {"ID", "Type", "BuyPrice", "State"};
            modelotabla = new DefaultTableModel(null, columnames);
            //LENADO DEL MODELO DE LA TABLA
            while(iter.hasNext()){
               dto = (KeyDTO) iter.next();
               Object[] row = {dto.getId(), dto.getType() , dto.getBuyprice(), dto.getState()};
               modelotabla.addRow(row);
            }
        } catch (NonexistentEntityException ex) {
            System.out.println("FALLA");// Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //BORRA LA LLAVE SELECCIONADA
    private void DeleteKey(){
        int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete it?");
        if (confirmation == 0){
            int[] seleccion = KeyTable.getSelectedRows();
            for(int i = 0; i< seleccion.length; i++){
                int index = seleccion[i];//index es la posicion
                long id = (long) KeyTable.getValueAt(index, 0);//FORMATO (fila, columna)
                KeyDTO dto = new KeyDTO();
                dto.setId(id);
                KeyManager.DeleteKey(dto);
                modelotabla.removeRow(index);
            }
        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        InventoryPanel = new javax.swing.JPanel();
        InitialMessage = new javax.swing.JLabel();
        InventoryTittle = new javax.swing.JLabel();
        DeleteButton = new javax.swing.JButton();
        NewKeyButton = new javax.swing.JButton();
        KeyTableScroll = new javax.swing.JScrollPane();
        KeyTable = new javax.swing.JTable();
        ReloadButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("InventoryFrame"); // NOI18N
        setResizable(false);

        InitialMessage.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        InitialMessage.setText("Welcome to JewishApp");

        InventoryTittle.setFont(new java.awt.Font("Comic Sans MS", 3, 14)); // NOI18N
        InventoryTittle.setText("Inventory");

        DeleteButton.setText("Delete Key");
        DeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteButtonActionPerformed(evt);
            }
        });

        NewKeyButton.setText("Add Key");
        NewKeyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewKeyButtonActionPerformed(evt);
            }
        });

        KeyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        KeyTable.setFocusable(false);
        KeyTable.setRequestFocusEnabled(false);
        KeyTable.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        KeyTableScroll.setViewportView(KeyTable);
        KeyTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        ReloadButton.setText("Reload");
        ReloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReloadButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout InventoryPanelLayout = new javax.swing.GroupLayout(InventoryPanel);
        InventoryPanel.setLayout(InventoryPanelLayout);
        InventoryPanelLayout.setHorizontalGroup(
            InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InventoryPanelLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(InventoryTittle, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ReloadButton)
                .addGap(82, 82, 82))
            .addGroup(InventoryPanelLayout.createSequentialGroup()
                .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(InventoryPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(NewKeyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(DeleteButton))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, InventoryPanelLayout.createSequentialGroup()
                            .addGap(104, 104, 104)
                            .addComponent(KeyTableScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(InventoryPanelLayout.createSequentialGroup()
                        .addGap(232, 232, 232)
                        .addComponent(InitialMessage)))
                .addContainerGap(98, Short.MAX_VALUE))
        );
        InventoryPanelLayout.setVerticalGroup(
            InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InventoryPanelLayout.createSequentialGroup()
                .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InventoryPanelLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(InitialMessage)
                        .addGap(48, 48, 48)
                        .addComponent(InventoryTittle, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(KeyTableScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DeleteButton)
                            .addComponent(NewKeyButton)))
                    .addGroup(InventoryPanelLayout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(ReloadButton)))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(InventoryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(InventoryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        getAccessibleContext().setAccessibleName("MenuWindow");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteButtonActionPerformed
       DeleteKey(); // TODO add your handling code here:
    }//GEN-LAST:event_DeleteButtonActionPerformed

    private void NewKeyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewKeyButtonActionPerformed
      KeyManager.InitAddKey();
              // TODO add your handling code here:
    }//GEN-LAST:event_NewKeyButtonActionPerformed

    private void ReloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReloadButtonActionPerformed
        ReloadTable();        // TODO add your handling code here:
    }//GEN-LAST:event_ReloadButtonActionPerformed

   


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DeleteButton;
    private javax.swing.JLabel InitialMessage;
    private javax.swing.JPanel InventoryPanel;
    private javax.swing.JLabel InventoryTittle;
    private javax.swing.JTable KeyTable;
    private javax.swing.JScrollPane KeyTableScroll;
    private javax.swing.JButton NewKeyButton;
    private javax.swing.JButton ReloadButton;
    // End of variables declaration//GEN-END:variables
}
