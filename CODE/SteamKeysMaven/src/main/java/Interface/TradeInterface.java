/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Controller.exceptions.NonexistentEntityException;;
import TransporterUnits.KeyDTO;
import TransporterUnits.SteamItemDTO;
import TransporterUnits.TradeDTO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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


public class TradeInterface extends Interface {
    
    private TradeDTO activetrade;
    DefaultTableModel modelotabla;
    DefaultTableModel modeloitems;
    DefaultListModel modelolista;
    
    //CONSTRUCTOR
    public TradeInterface(TradeDTO dto) {
        initComponents();
        initICon();
        this.setSize(Inventory.getLastWindow().getSize());
        this.setLocationRelativeTo(Inventory.getLastWindow()); 
        initTrade(dto);
    }
  private void initTrade(TradeDTO dto){
        try {
            activetrade = dto;
            PriceImput.setText(KeyManager.numberConvertor(activetrade.getPriceinstore()));
            SellPriceInput.setText("0.00");;
            BalanceInput.setText(KeyManager.numberConvertor(activetrade.getBalance()));
            ItemsTable.setSelectionMode(0);
            ItemsTable.getTableHeader().setResizingAllowed(false);
            VmrTable.getTableHeader().setResizingAllowed(false);
            PriceImput.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent ke) {
                    
                }
                @Override
                public void keyPressed(KeyEvent ke) {
                    if (ke.getKeyCode() == KeyEvent.VK_ENTER){
                    Reload();    
                    }
                }
                @Override
                public void keyReleased(KeyEvent ke) {
                   
                }});
            SellPriceInput.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent ke) {
                    
                }
                @Override
                public void keyPressed(KeyEvent ke) {
                    if(ke.getKeyCode() == KeyEvent.VK_ENTER){
                        AddItem();
                    }
                }
                @Override
                public void keyReleased(KeyEvent ke) {
                    
                }
            });
            Reload();
        } catch (Exception ex) {
           MessageDialog(ex.getMessage());
        }
    }
    //RECARGA LAS TABLAS Y OTROS
    protected void Reload(){
        try {
            ListVMRTable();
            ListItems();
            ShowKeys();
            UpdateBalance(0);
        } catch (Exception ex) {
            MessageDialog(ex.getMessage());
        }
    }
    //CREA UN TRADE CON LOS VALORES INGRESADOS
    private void AddNewTrade(){
        try {
            int confirm = JOptionPane.showOptionDialog(this, "Are you sure?", "Trade Alert", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"Yes", "No"}, "Yes");
            if(confirm == 0){
                KeyManager.EnterTrade(activetrade);
                MessageDialog("Trade Succesfully");
                initTrade(new TradeDTO(250,0));
            }
        } catch (Exception ex) {
            MessageDialog(ex.getMessage());
        }
        
    }
    //INICIALIZA LAS TABLAS PARA EL TRADE
  
    //MUESTRA LA LISTA DE ITEMS AGREGADOS
    private void ListItems(){
        try {
            //MODELO
            String [] columnames = {"StorePrice","SellPrice"};
            String [] vacio = {"------","------"};
            Integer [] totalizer = {0,0};
            if(!activetrade.getItems().isEmpty()){
                totalizer = KeyManager.getTotalOfItems(activetrade.getItems());
            }
            String [] total = {KeyManager.numberConvertor(totalizer[0]),KeyManager.numberConvertor(totalizer[1])};
            modeloitems = new DefaultTableModel(null,columnames);
            //LLENADO DE LA TABLA
            for(SteamItemDTO row: activetrade.getItems()){
                String[] element = {KeyManager.numberConvertor(row.getStoreprice()), KeyManager.numberConvertor(row.getSellprice())};
                modeloitems.addRow(element);
            }
            modeloitems.addRow(vacio);
            modeloitems.addRow(total);
            ItemsTable.setModel(modeloitems);
        } catch (Exception ex) {
            MessageDialog(ex.getMessage());
        }
    }
    //AGREGA UN ITEM A LA LISTA DE ITEMS
    private void AddItem(){
        try {
            int sellprice = KeyManager.numberConvertor(SellPriceInput.getText());
            int index = VmrTable.getSelectedRow();
            if(index == -1){
                throw new Exception("You did not select a value");
            }else if(sellprice != 0){
                int storeprice = KeyManager.numberConvertor(String.valueOf(VmrTable.getValueAt(index, 0)));
                activetrade.AddItem(new SteamItemDTO(isPending(),storeprice,sellprice));
                UpdateBalance(-storeprice);
            }else if(sellprice == 0){
                throw new Exception("You did not enter a sell price");
            }
        } catch (Exception ex) {
            MessageDialog(ex.getMessage());
        }
        Reload();
    }
    //ELIMINA UN ITEM DE LA LISTA DE ITEMS //ERROR// 
    private void DeleteItem(){
            int index = ItemsTable.getSelectedRow();
            if(index >= 0){
                int confirm = JOptionPane.showOptionDialog(this, "Are you sure?", "Errase Alert", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"Yes", "No"}, "Yes");
                if(confirm == 0){
                    try {
                        List<SteamItemDTO> items = activetrade.getItems();
                        UpdateBalance(items.get(index).getStoreprice());
                        items.remove(index);
                        activetrade.setItems(items);
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
        modelolista = new DefaultListModel();
        for(KeyDTO dto : activetrade.getKeys()){
            String datos = String.valueOf(dto.getId()) + "--" + dto.getType();
            modelolista.addElement(datos);
        }
        KeysTradingList.setModel(modelolista);
    }
    //DEVUELVE EL ID DE LA KEY
    private long getKeyID(String str){
        return KeyManager.getKeyID(str);
    }
    //BORRA LA KEY SELECCIONADA
    private void DeleteTradingKey(){
        try {
            String key = KeysTradingList.getSelectedValue();
            int index = KeysTradingList.getSelectedIndex();
            if(key != null){
                 long id = getKeyID(key);
                 List<KeyDTO> keys = activetrade.getKeys();
                     UpdateBalance(-getPriceInput());
                     keys.remove(index);
                     activetrade.setKeys(keys);
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
            List<KeyDTO> missing = KeyManager.getmissingKeys(activetrade.getKeys());
            if(missing.isEmpty()){
                missing = KeyManager.ListWithStateKeys(new KeyDTO("","Tradeable"));
            }
            String[] panel = new String[missing.size()];
            int i = 0;
            for(KeyDTO dto : missing){
                String element = String.valueOf(dto.getId()) + "--" + dto.getType();
                panel[i] = element;
                i++;
            }
            String confirmacion = (String) JOptionPane.showInputDialog(this, "Select de key", "AddKeyToTradeHelper", JOptionPane.QUESTION_MESSAGE,null,panel, "Select");
            if(confirmacion != null){
                UpdateBalance(getPriceInput());
                KeyDTO key = KeyManager.getKeyDTO(getKeyID(confirmacion));
                activetrade.AddKey(key); 
            }
            
        } catch (NonexistentEntityException ex) {
            MessageDialog(ex.getMessage());
        } catch (Exception ex) {
            MessageDialog(ex.getMessage());
        }
        Reload();
    }
    //ACTUALIZA EL BALANCE DE SALDO EN LA PAGINA //SIN USO POR AHORA
    private void UpdateBalance(int value) throws Exception{
//        int last = getBalanceInput();
//        if(last-value >= 0){
//            activetrade.setBalance(last + value);
//        }else{
//            throw new Exception("You can´t");
//        }
//        BalanceInput.setText(KeyManager.numberConvertor(activetrade.getBalance()));
    }
    //DEVUELVE EL PRECIO INGRESADO
    private int getPriceInput() throws Exception{
        String input = PriceImput.getText(); 
        return KeyManager.numberConvertor(input);
    }
    //DEVUELVE EL PRECIO DE VENTA INGRESADO
    private int getSellInput() throws Exception{
        String input = SellPriceInput.getText();
        return KeyManager.numberConvertor(input);
    }
    private int getBalanceInput() throws Exception{
        String input = BalanceInput.getText();
        return KeyManager.numberConvertor(input);
    }
    private boolean isPending(){
        return PendingCheckBox.isSelected();
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
        PendingCheckBox = new javax.swing.JCheckBox();

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
        BalanceInput.setFocusable(false);

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

        PendingCheckBox.setText("Pending");

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ReloadButton)
                            .addComponent(SellPriceInput, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ItemsScroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DeleteItemButton, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(BalanceInput, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(90, 90, 90)
                                    .addComponent(TradeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(BalanceTittle, javax.swing.GroupLayout.Alignment.LEADING))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(PendingCheckBox)
                                .addGap(18, 18, 18)
                                .addComponent(AddButton)
                                .addGap(192, 192, 192)))
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
                                .addComponent(ItemsScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(DeleteItemButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(241, 241, 241)
                                .addComponent(SellPriceInput, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(AddButton)
                                    .addComponent(PendingCheckBox))))
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
        if(!activetrade.getItems().isEmpty()){
           int confirm = JOptionPane.showOptionDialog(this, "You are trading, Are you sure you wanna leave?", "Leaving TradeHelper", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"Yes", "No"}, "Yes");
                    if (confirm == 0){
                        BackToInventory();
                    }
           }else if(activetrade.getItems().isEmpty()){
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
    private javax.swing.JCheckBox PendingCheckBox;
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
