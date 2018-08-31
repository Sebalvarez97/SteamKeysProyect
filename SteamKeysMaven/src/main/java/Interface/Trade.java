/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Controller.exceptions.NonexistentEntityException;
import TransporterUnits.KeyDTO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import steam.jewishs.steamkeysmaven.KeyManager;


public class Trade extends Interface {
    
    private List<KeyDTO> keys = new ArrayList();
    DefaultTableModel modelotabla;
    DefaultTableModel modeloitems;
    DefaultListModel modelolista;
    private List<Object[]> Items = new ArrayList();
    
    //CONSTRUCTOR
    public Trade() {
        initComponents();
        initICon();
        this.setSize(Inventory.getLastWindow().getSize());
        this.setLocationRelativeTo(Inventory.getLastWindow()); 
        initTrade();
    }

    //SET DE LA VARIABLE
    public void setKeys(List<KeyDTO> keys){
        this.keys = keys;
        Reload();
    }
    //RECARGA LAS TABLAS Y OTROS
    protected void Reload(){
        ListVMRTable();
        ListItems();
        ShowKeys();
    }
    //CREA UN TRADE CON LOS VALORES INGRESADOS
    private void AddNewTrade(){
        try {
            int confirm = JOptionPane.showOptionDialog(this, "Are you sure?", "Trade Alert", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"Yes", "No"}, "Yes");
            if(confirm == 0){
                KeyManager.EnterTrade(keys, Items,KeyManager.getKeyPrice() ,getBalanceInput());
                Items.clear();
                keys.clear();
                MessageDialog("Trade Succesfully");
            }
        } catch (Exception ex) {
            MessageDialog(ex.getMessage());
        }
        initTrade();
    }
    //INICIALIZA LAS TABLAS PARA EL TRADE
    private void initTrade(){
        try {
            PriceImput.setText("2.50");
            SellPriceInput.setText("0.00");
            BalanceInput.setText(KeyManager.numberConvertor(getPriceInput()*keys.size()));
            ItemsTable.setSelectionMode(0);
            ItemsTable.getTableHeader().setResizingAllowed(false);
            VmrTable.getTableHeader().setResizingAllowed(false);
            Reload();
        } catch (Exception ex) {
           MessageDialog(ex.getMessage());
        }
    }
    //MUESTRA LA LISTA DE ITEMS AGREGADOS
    private void ListItems(){
        //MODELO
        String [] columnames = {"Item","StorePrice","SellPrice"};
        modeloitems = new DefaultTableModel(null,columnames);
        //LLENADO DE LA TABLA
        Iterator iter = Items.iterator();
        while(iter.hasNext()){
            Object[] row = (Object[]) iter.next();
            modeloitems.addRow(row);
        }
        ItemsTable.setModel(modeloitems);
        TableColumn columnaid = ItemsTable.getColumn("Item");
        columnaid.setPreferredWidth(40);
        columnaid.setMaxWidth(100);
    }
    //AGREGA UN ITEM A LA LISTA DE ITEMS
    private void AddItem(){
        try {
            String sellprice = KeyManager.numberConvertor(KeyManager.numberConvertor(SellPriceInput.getText()));
            int index = VmrTable.getSelectedRow();
            if(index == -1){
                throw new Exception("You did not select a value");
            }else if(KeyManager.numberConvertor(sellprice) != 0){
                String storeprice = String.valueOf(VmrTable.getValueAt(index, 0));
                Object[] row ={Items.size()+1,storeprice, sellprice};
                UpdateBalance(KeyManager.numberConvertor(storeprice));
                Items.add(row);
            }else if(KeyManager.numberConvertor(sellprice) == 0){
                throw new Exception("You did not enter a sell price");
            }
            ItemsTable.setModel(modeloitems);
        } catch (Exception ex) {
            MessageDialog(ex.getMessage());
        }
        Reload();
    }
    //ELIMINA UN ITEM DE LA LISTA DE ITEMS //ERROR// 
    private void DeleteItem(){
            int index = ItemsTable.getSelectedRow();
            if(index != 0){
                int confirm = JOptionPane.showOptionDialog(this, "Are you sure?", "Errase Alert", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"Yes", "No"}, "Yes");
                if(confirm == 0){
                    try {
                        Object[] ob = Items.get(index);
                        String storeprice = (String) ob[1];
                        UpdateBalance(-KeyManager.numberConvertor(storeprice));  
                        Items.remove(index);
                    } catch (Exception ex) {
                        MessageDialog("Error in entry");
                    }
                } 
            }else{
                 MessageDialog("Select an item first");
            }
            Reload();
    }
    //MUESTRA LAS LLAVES QUE SE ESTAN TRADEANDO AL MOMENTO
    private void ShowKeys(){
        Iterator iter = keys.iterator();
        modelolista = new DefaultListModel();
        while(iter.hasNext()){
            KeyDTO dto = (KeyDTO) iter.next();
            String datos = String.valueOf(dto.getId()) + "--" + dto.getType();
            modelolista.addElement(datos);
        }
        KeysTradingList.setModel(modelolista);
    }
    //DEVUELVE EL ID DE LA KEY
    private long getKeyID(String str){
        int guion = str.indexOf("-");
        String id = str.substring( 0,guion);
        return Long.parseLong(id);
    }
    //BORRA LA KEY SELECCIONADA
    private void DeleteTradingKey(){
        try {
            String key = KeysTradingList.getSelectedValue();
            int index = KeysTradingList.getSelectedIndex();
            if(key != null){
                 long id = getKeyID(key);
                 keys.remove(index);
            }else{
                throw new Exception("select a key first");
            }
           
        } catch (Exception ex) {
            MessageDialog(ex.getMessage());
        }
        Reload();
    }
    //AGREGA UNA LLAVE AL TRADE
    private void AddTradingKey(){
        try {
            List<KeyDTO> missing = KeyManager.getmissingKeys(keys);
            if(missing.isEmpty()){
                missing = KeyManager.ListWithStateKeys(new KeyDTO("","Tradeable"));
            }
            String[] panel = new String[missing.size()];
            Iterator iter = missing.iterator();
            int i = 0;
            while(iter.hasNext()){
                KeyDTO dto = (KeyDTO) iter.next();
                String element = String.valueOf(dto.getId()) + "--" + dto.getType();
                panel[i] = element;
                i++;
            }
            String confirmacion = (String) JOptionPane.showInputDialog(this, "Select de key", "AddKeyToTradeHelper", JOptionPane.QUESTION_MESSAGE,null,panel, "Select");
            if(confirmacion != null){
                KeyDTO patron = new KeyDTO();
                patron.setId(getKeyID(confirmacion));
                KeyDTO key = KeyManager.getKeyDTO(patron);
                keys.add(key);
            }
            
        } catch (NonexistentEntityException ex) {
            MessageDialog(ex.getMessage());
        }
        Reload();
    }
    //ACTUALIZA EL BALANCE DE SALDO EN LA PAGINA
    private void UpdateBalance(int value) throws Exception{
        int last = getBalanceInput();
        int nuevo = last + value;
        BalanceInput.setText(KeyManager.numberConvertor(nuevo));
    }
    //DEVUELVE EL PRECIO INGRESADO
    private int getPriceInput() throws Exception{
        String input = PriceImput.getText(); 
        return KeyManager.numberConvertor(input);
    }
    //DEVUELVE EL PRECIO INGRESADO
    private int getSellInput() throws Exception{
        String input = SellPriceInput.getText();
        return KeyManager.numberConvertor(input);
    }
    private int getBalanceInput() throws Exception{
        String input = BalanceInput.getText();
        return KeyManager.numberConvertor(input);
    }
    //MUESTRA LA LISTA DE VMR
    private void ListVMRTable(){
        try {
            //MODELO
            String[] columnames = {"Store Price","VMR"}; 
            modelotabla = new DefaultTableModel(null, columnames);
            //LLENADO DE LA TABLA
            double input = (double) getPriceInput();
            List<Object[]> VMRlist = KeyManager.ListVMR(input);
            Iterator iter = VMRlist.iterator();
            while(iter.hasNext()){
                modelotabla.addRow((Object[]) iter.next());
            }
            VmrTable.setModel(modelotabla);
            TableColumn columnaid = VmrTable.getColumn("Store Price");
            columnaid.setPreferredWidth(80);
            columnaid.setMaxWidth(100);
            
        } catch (Exception ex) {
            MessageDialog(ex.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PriceTittle = new javax.swing.JLabel();
        TradeTittle = new javax.swing.JLabel();
        VmrScroll = new javax.swing.JScrollPane();
        VmrTable = new javax.swing.JTable();
        SellPriceInput = new javax.swing.JTextField();
        AddButton = new javax.swing.JButton();
        BalanceInput = new javax.swing.JTextField();
        ItemsScroll = new javax.swing.JScrollPane();
        ItemsTable = new javax.swing.JTable();
        BackButton = new javax.swing.JButton();
        TradeButton = new javax.swing.JButton();
        BalanceTittle = new javax.swing.JLabel();
        ReloadButton = new javax.swing.JButton();
        DeleteItemButton = new javax.swing.JButton();
        PriceImput = new javax.swing.JTextField();
        ScrollKeys = new javax.swing.JScrollPane();
        KeysTradingList = new javax.swing.JList<>();
        DeleteKeyButton = new javax.swing.JButton();
        AddKeyButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        PriceTittle.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        PriceTittle.setText("KeyPrice");

        TradeTittle.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N
        TradeTittle.setText("Trade");

        VmrTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        VmrTable.setFocusable(false);
        VmrTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        VmrScroll.setViewportView(VmrTable);

        SellPriceInput.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        SellPriceInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SellPriceInputActionPerformed(evt);
            }
        });

        AddButton.setText(">>>");
        AddButton.setFocusable(false);
        AddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddButtonActionPerformed(evt);
            }
        });

        BalanceInput.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        BalanceInput.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        ItemsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        ItemsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ItemsTable.setFocusable(false);
        ItemsScroll.setViewportView(ItemsTable);

        BackButton.setText("Back");
        BackButton.setFocusable(false);
        BackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackButtonActionPerformed(evt);
            }
        });

        TradeButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        TradeButton.setText("Trade");
        TradeButton.setFocusable(false);
        TradeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TradeButtonActionPerformed(evt);
            }
        });

        BalanceTittle.setText("Balance");
        BalanceTittle.setToolTipText("");
        BalanceTittle.setFocusable(false);

        ReloadButton.setText("Reload");
        ReloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReloadButtonActionPerformed(evt);
            }
        });

        DeleteItemButton.setText("Delete Item");
        DeleteItemButton.setFocusable(false);
        DeleteItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteItemButtonActionPerformed(evt);
            }
        });

        PriceImput.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        PriceImput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PriceImputActionPerformed(evt);
            }
        });

        KeysTradingList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        KeysTradingList.setFocusable(false);
        ScrollKeys.setViewportView(KeysTradingList);

        DeleteKeyButton.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        DeleteKeyButton.setText("X");
        DeleteKeyButton.setFocusable(false);
        DeleteKeyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteKeyButtonActionPerformed(evt);
            }
        });

        AddKeyButton.setText("Add");
        AddKeyButton.setFocusable(false);
        AddKeyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddKeyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(TradeTittle)
                .addGap(238, 238, 238)
                .addComponent(PriceTittle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PriceImput, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BackButton)
                .addGap(60, 60, 60))
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(VmrScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ScrollKeys, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DeleteKeyButton)
                            .addComponent(AddKeyButton))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(DeleteItemButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ReloadButton)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(AddButton))
                                    .addComponent(SellPriceInput, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                                .addComponent(ItemsScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(BalanceInput, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(90, 90, 90)
                                .addComponent(TradeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(BalanceTittle, javax.swing.GroupLayout.Alignment.LEADING))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(BackButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(TradeTittle)
                                .addGap(9, 9, 9))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(PriceImput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(PriceTittle)))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(ItemsScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(241, 241, 241)
                                .addComponent(SellPriceInput, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(AddButton)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(DeleteItemButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                        .addComponent(BalanceTittle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TradeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BalanceInput, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ScrollKeys, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(DeleteKeyButton)
                                    .addComponent(ReloadButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(AddKeyButton)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(VmrScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(53, 53, 53))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    private void SellPriceInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SellPriceInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SellPriceInputActionPerformed

    private void BackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackButtonActionPerformed
        if(!Items.isEmpty()){
//           int confimacion = JOptionPane.showConfirmDialog(this, "confirm");
           int confirm = JOptionPane.showOptionDialog(this, "You are trading, Are you sure you wanna leave?", "Leaving TradeHelper", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"Yes", "No"}, "Yes");
                    if (confirm == 0){
                        BackToInventory();
                    }
           }else{
            BackToInventory();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_BackButtonActionPerformed

    private void ReloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReloadButtonActionPerformed
     Reload();   // TODO add your handling code here:
    }//GEN-LAST:event_ReloadButtonActionPerformed

    private void PriceImputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PriceImputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PriceImputActionPerformed

    private void DeleteItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteItemButtonActionPerformed
      DeleteItem();  // TODO add your handling code here:
    }//GEN-LAST:event_DeleteItemButtonActionPerformed

    private void DeleteKeyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteKeyButtonActionPerformed
       DeleteTradingKey();
        // TODO add your handling code here:
    }//GEN-LAST:event_DeleteKeyButtonActionPerformed

    private void AddKeyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddKeyButtonActionPerformed
       AddTradingKey();
        // TODO add your handling code here:
    }//GEN-LAST:event_AddKeyButtonActionPerformed

    private void AddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddButtonActionPerformed
        AddItem();
        // TODO add your handling code here:
    }//GEN-LAST:event_AddButtonActionPerformed

    private void TradeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TradeButtonActionPerformed
      AddNewTrade();  // TODO add your handling code here:
    }//GEN-LAST:event_TradeButtonActionPerformed

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddButton;
    private javax.swing.JButton AddKeyButton;
    private javax.swing.JButton BackButton;
    private javax.swing.JTextField BalanceInput;
    private javax.swing.JLabel BalanceTittle;
    private javax.swing.JButton DeleteItemButton;
    private javax.swing.JButton DeleteKeyButton;
    private javax.swing.JScrollPane ItemsScroll;
    private javax.swing.JTable ItemsTable;
    private javax.swing.JList<String> KeysTradingList;
    private javax.swing.JTextField PriceImput;
    private javax.swing.JLabel PriceTittle;
    private javax.swing.JButton ReloadButton;
    private javax.swing.JScrollPane ScrollKeys;
    private javax.swing.JTextField SellPriceInput;
    private javax.swing.JButton TradeButton;
    private javax.swing.JLabel TradeTittle;
    private javax.swing.JScrollPane VmrScroll;
    private javax.swing.JTable VmrTable;
    // End of variables declaration//GEN-END:variables
}
