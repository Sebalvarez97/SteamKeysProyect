
package Interface;

import Controller.exceptions.NonexistentEntityException;
import TransporterUnits.KeyDTO;
import TransporterUnits.TypeStateDTO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import steam.jewishs.steamkeysmaven.KeyManager;


public class Inventory extends javax.swing.JFrame{
    
    DefaultTableModel modelotabla;
    
    //CONSTRUCTOR
    public Inventory() {
        initComponents();
        ReloadTable();
        this.setLocationRelativeTo(KeyManager.getAddKey()); 
    }
    //CONFIGURACION DE LA TABLA
    private void SetKeyTable(){
        KeyTable.getTableHeader().setResizingAllowed(false);//DESABILITA LA EDICION DE LAS COLUMNAS
        TableColumn columnaid = KeyTable.getColumn("ID");
        columnaid.setPreferredWidth(40);
        columnaid.setMaxWidth(100);
        
    }
    //RECARGA LA LISTA DE LLAVES
    private void ReloadTable(){
        ListTable();
        SetKeyTable();
        SetEstadistics();
    }
    private void SetEstadistics(){
        try {
            int cantidad = KeyManager.KeyCounter();
            int tradeables = KeyManager.CantWithState(new TypeStateDTO("Tradeable"));
            int untradeables = KeyManager.CantWithState(new TypeStateDTO("Untradeable"));
            CantTittle.setText("Cant.Keys: "+Integer.toString(cantidad));
            TradTittle.setText("Tradeables: "+Integer.toString(tradeables));
            UntradTittle.setText("Untradeables: "+Integer.toString(untradeables));
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //CONFIGURA EL MODELO DE LA TABLA DE LLAVES
    private void ListTable(){
           try {
            KeyManager.UpdateState();
            List<KeyDTO> keys = KeyManager.ListKeys();
            Iterator iter = keys.iterator();
            KeyDTO dto;
            //CONFIGURACION DEL MODELO DE LA TABLA
            String[] columnames = {"ID", "Type","BuyDate", "State","Release in"};
            modelotabla = new DefaultTableModel(null, columnames);
            
            //LENADO DEL MODELO DE LA TABLA
            while(iter.hasNext()){
               dto = (KeyDTO) iter.next();
               String type = dto.getType();
               String date = KeyManager.SimpleFormatDate(dto.getbuydate());
               String state = dto.getState();
               int days = 0;
               if(state.equals("Untradeable")){
                   days = KeyManager.DayDiference(Calendar.getInstance().getTime(), KeyManager.ReleaseDate(dto.getbuydate())) + 1;//SE AGREGA UN DIA PARA CONSIDERAR EL DIA DE HOY
               }
               Object[] row = {dto.getId(), type , date , state , days };
               modelotabla.addRow(row);
            }
            KeyTable.setModel(modelotabla);
        } catch (NonexistentEntityException ex) {
            System.out.println("FALLA");// Logger.getLogger(Inventory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //BORRA LA LLAVE SELECCIONADA
    private void DeleteKey(){ //**FUNCIONA MAL***
        int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete it?");
        if (confirmation == 0){
            int[] seleccion = KeyTable.getSelectedRows();
            for(int i = 0; i< seleccion.length; i++){
                int index = seleccion[i];//index es la posicion
                long id = (long) KeyTable.getValueAt(index, 0);//FORMATO (fila, columna)
                KeyDTO dto = new KeyDTO();
                dto.setId(id);
                System.out.println(dto.getId());
                KeyManager.DeleteKey(dto);
            }
            ReloadTable();
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
        CantTittle = new javax.swing.JLabel();
        TradTittle = new javax.swing.JLabel();
        UntradTittle = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("InventoryFrame"); // NOI18N
        setResizable(false);

        InitialMessage.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        InitialMessage.setText("Welcome to JewishApp");

        InventoryTittle.setFont(new java.awt.Font("Comic Sans MS", 3, 18)); // NOI18N
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

        CantTittle.setText("Cant. Keys:");

        TradTittle.setText("Tradeables:");

        UntradTittle.setText("Untradeables:");

        javax.swing.GroupLayout InventoryPanelLayout = new javax.swing.GroupLayout(InventoryPanel);
        InventoryPanel.setLayout(InventoryPanelLayout);
        InventoryPanelLayout.setHorizontalGroup(
            InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InventoryPanelLayout.createSequentialGroup()
                .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InventoryPanelLayout.createSequentialGroup()
                        .addGap(232, 232, 232)
                        .addComponent(InitialMessage))
                    .addGroup(InventoryPanelLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(InventoryTittle, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ReloadButton)
                .addGap(83, 83, 83))
            .addGroup(InventoryPanelLayout.createSequentialGroup()
                .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(InventoryPanelLayout.createSequentialGroup()
                            .addGap(170, 170, 170)
                            .addComponent(CantTittle)
                            .addGap(45, 45, 45)
                            .addComponent(TradTittle)
                            .addGap(43, 43, 43)
                            .addComponent(UntradTittle))
                        .addGroup(InventoryPanelLayout.createSequentialGroup()
                            .addGap(104, 104, 104)
                            .addComponent(KeyTableScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(InventoryPanelLayout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(NewKeyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(DeleteButton)))
                .addContainerGap(98, Short.MAX_VALUE))
        );
        InventoryPanelLayout.setVerticalGroup(
            InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InventoryPanelLayout.createSequentialGroup()
                .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InventoryPanelLayout.createSequentialGroup()
                        .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(InventoryPanelLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(InitialMessage))
                            .addGroup(InventoryPanelLayout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addComponent(ReloadButton)))
                        .addGap(33, 33, 33))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InventoryPanelLayout.createSequentialGroup()
                        .addComponent(InventoryTittle, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)))
                .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CantTittle)
                    .addComponent(TradTittle, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UntradTittle))
                .addGap(18, 18, 18)
                .addComponent(KeyTableScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DeleteButton)
                    .addComponent(NewKeyButton))
                .addContainerGap(39, Short.MAX_VALUE))
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
      
        
        if(KeyManager.getAddKey() == null){
          KeyManager.InitAddKey();
      }else{
          KeyManager.getAddKey().setVisible(true);
      }
      this.setVisible(false);
              // TODO add your handling code here:
    }//GEN-LAST:event_NewKeyButtonActionPerformed

    private void ReloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReloadButtonActionPerformed
        ReloadTable();        // TODO add your handling code here:
    }//GEN-LAST:event_ReloadButtonActionPerformed

   


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CantTittle;
    private javax.swing.JButton DeleteButton;
    private javax.swing.JLabel InitialMessage;
    private javax.swing.JPanel InventoryPanel;
    private javax.swing.JLabel InventoryTittle;
    private javax.swing.JTable KeyTable;
    private javax.swing.JScrollPane KeyTableScroll;
    private javax.swing.JButton NewKeyButton;
    private javax.swing.JButton ReloadButton;
    private javax.swing.JLabel TradTittle;
    private javax.swing.JLabel UntradTittle;
    // End of variables declaration//GEN-END:variables
}
