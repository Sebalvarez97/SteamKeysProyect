
package Interface;

import Controller.exceptions.NonexistentEntityException;
import TransporterUnits.KeyDTO;
import TransporterUnits.ParameterDTO;
import TransporterUnits.TypeStateDTO;
import java.text.DecimalFormat;
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
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import steam.jewishs.steamkeysmaven.KeyManager;


public class Inventory extends javax.swing.JFrame{
    
    DefaultTableModel modelotabla; //MODELO PARA LA TABLE DE LLAVES
    //MANEJO DE VENTANAS
    public static List<JFrame> windows = new ArrayList();
    public static JFrame getLastWindow(){
       return windows.get(windows.size()-1);
    }
    //ABRE UNA NUEVA VENTANA
    public void OpenWindow(JFrame window){
        windows.add(window);
    }
    //CIERRA LA ULTIMA VENTANA ABIERTA
    public static void CloseLastWindow(){
        int lastindex = windows.size()-1;
        windows.remove(lastindex);
    }
    
    //CONSTRUCTOR
    public Inventory() {
        initComponents();
        initSaldo();
        OpenWindow(null);
        ReloadTable();
        this.setLocationRelativeTo(getLastWindow());
        OpenWindow(this);
    }
    //CONFIGURACION DE LA TABLA
    private void SetKeyTable(){
        KeyTable.getTableHeader().setResizingAllowed(false);//DESABILITA LA EDICION DE LAS COLUMNAS
        TableColumn columnaid = KeyTable.getColumn("ID");
        columnaid.setPreferredWidth(40);
        columnaid.setMaxWidth(100);  
    }
    //RECARGA LA LISTA DE LLAVES
    public void ReloadTable(){
        ListTable();
        SetKeyTable();
        SetEstadistics();
    }
    //INICIALIZA EL SALDO DE STEAM CON EL VALOR GUARDADO EN BASE DE DATOS
    private void initSaldo(){
        ParameterDTO dto = KeyManager.findParameter("Saldo");
        double saldo = (double) dto.getValue();
        saldo = saldo/100;
        Balance.setText(String.valueOf(saldo));
    }
    //DEVUELVE LA ENTRADA DEL JTEXTFIELD DEL SALDO DE STEAM
    private int getBalanceInput() throws Exception{
        String saldo = Balance.getText();
        return KeyManager.numberConvertor(saldo);
    }
    //PERMITE MOSTRAR UNA ALERTA O MENSAJE EN PANTALLA
    private void MessageDialog(String scr){
        JOptionPane.showMessageDialog(this, scr, "WARNING_MESSAGE", JOptionPane.WARNING_MESSAGE);
    }
    //ACTUALIZA EL VALOR DEL SALDO DE STEAM
    private void UpdateSaldo() throws Exception {
        try{
            int input = getBalanceInput();
            KeyManager.setValue(new ParameterDTO("Saldo", "",input));
        }catch(Exception ex){
            throw new Exception("bad enter");
        }
    }
    //MUESTRA EL TOTAL EN PESOS
    private void showTotal(){
        try {
            UpdateSaldo();
            double total = (double) KeyManager.getTotalMoney();
            total = total/100;
            TotalTittle.setText("TOTAL   "+ " $ " + Double.toString(total));
        } catch (Exception ex) {
            if (ex.getMessage().equals("bad enter")){
                initSaldo();
                MessageDialog("You entered a bad input in balance");
                ReloadTable();
            }
        }
    }
    //MUESTRA LAS ESTADISTICAS DE LAS LLAVES (CANT TOTAL, TRADEABLES Y NO TRADEABLES, ETC)
    private void SetEstadistics(){
        try {
            int cantidad = KeyManager.KeyCounter();
            int tradeables = KeyManager.CantWithState(new TypeStateDTO("Tradeable"));
            int untradeables = KeyManager.CantWithState(new TypeStateDTO("Untradeable"));
            CantTittle.setText("Cant.Keys: "+Integer.toString(cantidad));
            TradTittle.setText("Tradeables: "+Integer.toString(tradeables));
            UntradTittle.setText("Untradeables: "+Integer.toString(untradeables));
            showTotal();
            
        } catch (NonexistentEntityException ex) {
            MessageDialog("bad enter estadistics");
        }
    }
    //CONFIGURA EL MODELO DE LA TABLA DE LLAVES Y LAS LISTA
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
               if(dto.getState().equals("Tradeable") || dto.getState().equals("Untradeable")){
                   String type = dto.getType();
                   String date = KeyManager.SimpleFormatDate(dto.getbuydate());
                   String state = dto.getState();
                   String days = "-";
               if(state.equals("Untradeable")){
                   int day = KeyManager.DayDiference(Calendar.getInstance().getTime(), KeyManager.ReleaseDate(dto.getbuydate()));
                   if(day == 0){
                       days = " < 24hs";
                   }else{
                    days = String.valueOf(day);
                   }
               }
               String release = KeyManager.SimpleFormatDate(KeyManager.ReleaseDate(dto.getbuydate()));
               Object[] row = {dto.getId(), type , date , state ,"("+ days + ")"};
               modelotabla.addRow(row);
               }
            }
            KeyTable.setModel(modelotabla);
        } catch (NonexistentEntityException ex) {
            MessageDialog("The entity does not exist");
        } catch (Exception ex) {
            MessageDialog(ex.getMessage());
        }
    }
    //DEVUELVE LA LISTA DE LLAVES SELECCIONADAS 
    private List<KeyDTO> getKeySelection(){
        List<KeyDTO> keys = new ArrayList();
        int[] seleccion = KeyTable.getSelectedRows();
        for(int i = 0;i < seleccion.length; i++){
            int index = seleccion[i];
            long id;
            String type;
            String state;
            id = (long) KeyTable.getValueAt(index, 0);
            type = (String) KeyTable.getValueAt(index, 1);
            state = (String) KeyTable.getValueAt(index, 3);
            KeyDTO dto = new KeyDTO();
            dto.setState(state);
            dto.setType(type);
            dto.setId(id);
            keys.add(dto);
        }
        return keys;
    }
    //VALIDA LA SELECCION PARA TRADEAR
    private boolean ValidateTradeSelection(List<KeyDTO> list){
        boolean validation = true;
        Iterator iter = list.iterator();
        while(iter.hasNext()){
            KeyDTO dto = (KeyDTO) iter.next();
            if(!dto.getState().equals("Tradeable")){
                validation = false;
            }
        }
        return validation;
    }
    //BORRA LAS LLAVES SELECCIONADAS
    private void DeleteKey(){ 
        List<KeyDTO> keys = getKeySelection();
      if(keys.size() == 0){
            MessageDialog("Select a key first");
      }else{
        int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete selected keys?");
        if (confirmation == 0){
           Iterator iter = keys.iterator();
           while(iter.hasNext()){
                KeyDTO dto = (KeyDTO) iter.next();
                KeyManager.DeleteKey(dto);
            }
        }
      }
        ReloadTable();
    }
    //ABRE UNA VENTANA DEL TIPO INGRESADO
    private void AddWindow(JFrame window){
        window.setTitle("SteamKeysApp");
        OpenWindow(window);
        this.setVisible(false);
        window.setVisible(true);
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
        BalanceTittle = new javax.swing.JLabel();
        TotalTittle = new javax.swing.JLabel();
        ConfigButton = new javax.swing.JButton();
        TradeButton = new javax.swing.JButton();
        SellButton = new javax.swing.JButton();
        Balance = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("InventoryFrame"); // NOI18N
        setResizable(false);

        InitialMessage.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        InitialMessage.setText("Welcome to JewishApp");

        InventoryTittle.setFont(new java.awt.Font("Comic Sans MS", 3, 18)); // NOI18N
        InventoryTittle.setText("Inventory");

        DeleteButton.setText("Delete Key");
        DeleteButton.setFocusable(false);
        DeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteButtonActionPerformed(evt);
            }
        });

        NewKeyButton.setText("Add Key");
        NewKeyButton.setFocusable(false);
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
        ReloadButton.setFocusable(false);
        ReloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReloadButtonActionPerformed(evt);
            }
        });

        CantTittle.setText("Cant. Keys:");

        TradTittle.setText("Tradeables:");

        UntradTittle.setText("Untradeables:");

        BalanceTittle.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        BalanceTittle.setText("Saldo");

        TotalTittle.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        TotalTittle.setText("Total");

        ConfigButton.setText("...");
        ConfigButton.setFocusable(false);
        ConfigButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConfigButtonActionPerformed(evt);
            }
        });

        TradeButton.setText("Trade");
        TradeButton.setFocusable(false);
        TradeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TradeButtonActionPerformed(evt);
            }
        });

        SellButton.setText("Sell");
        SellButton.setFocusable(false);

        Balance.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        Balance.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        Balance.setName("Balance"); // NOI18N

        javax.swing.GroupLayout InventoryPanelLayout = new javax.swing.GroupLayout(InventoryPanel);
        InventoryPanel.setLayout(InventoryPanelLayout);
        InventoryPanelLayout.setHorizontalGroup(
            InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InventoryPanelLayout.createSequentialGroup()
                .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InventoryPanelLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(ConfigButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(179, 179, 179)
                        .addComponent(InitialMessage))
                    .addGroup(InventoryPanelLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(InventoryTittle, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ReloadButton)
                .addGap(83, 83, 83))
            .addGroup(InventoryPanelLayout.createSequentialGroup()
                .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(InventoryPanelLayout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(KeyTableScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(InventoryPanelLayout.createSequentialGroup()
                                .addComponent(CantTittle)
                                .addGap(45, 45, 45)
                                .addComponent(TradTittle)
                                .addGap(43, 43, 43)
                                .addComponent(UntradTittle)
                                .addGap(196, 196, 196))))
                    .addGroup(InventoryPanelLayout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InventoryPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(SellButton, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(TradeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(NewKeyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(DeleteButton))
                            .addGroup(InventoryPanelLayout.createSequentialGroup()
                                .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TotalTittle)
                                    .addGroup(InventoryPanelLayout.createSequentialGroup()
                                        .addComponent(BalanceTittle)
                                        .addGap(18, 18, 18)
                                        .addComponent(Balance, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(98, Short.MAX_VALUE))
        );
        InventoryPanelLayout.setVerticalGroup(
            InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InventoryPanelLayout.createSequentialGroup()
                .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InventoryPanelLayout.createSequentialGroup()
                        .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(InventoryPanelLayout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addComponent(ReloadButton))
                            .addGroup(InventoryPanelLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ConfigButton)
                                    .addComponent(InitialMessage))))
                        .addGap(33, 33, 33))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InventoryPanelLayout.createSequentialGroup()
                        .addComponent(InventoryTittle, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)))
                .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CantTittle)
                    .addComponent(TradTittle, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UntradTittle))
                .addGap(18, 18, 18)
                .addComponent(KeyTableScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BalanceTittle)
                    .addComponent(Balance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(TotalTittle)
                .addGap(20, 20, 20)
                .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DeleteButton)
                    .addComponent(NewKeyButton)
                    .addComponent(TradeButton)
                    .addComponent(SellButton))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        Balance.getAccessibleContext().setAccessibleName("Balance");

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
            
            AddWindow(new AddKey());

              // TODO add your handling code here:
    }//GEN-LAST:event_NewKeyButtonActionPerformed

    private void ReloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReloadButtonActionPerformed
        ReloadTable();        // TODO add your handling code here:
    }//GEN-LAST:event_ReloadButtonActionPerformed

    private void ConfigButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfigButtonActionPerformed
        // TODO add your handling code here:
      AddWindow(new Configurations());
      
    }//GEN-LAST:event_ConfigButtonActionPerformed


    
    private void TradeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TradeButtonActionPerformed
        List<KeyDTO> keys = getKeySelection();
        if(keys.size() == 0){
             MessageDialog("Select a valid key first");
        }else if(ValidateTradeSelection(keys)) {
            int confirmacion = JOptionPane.showConfirmDialog(this, "This key/s selected will be traded, Are you sure?");
            if(confirmacion == 0){
                    Trade window = new Trade();
                    window.setKeys(keys);
                    AddWindow(window);
            }
        }else if(!ValidateTradeSelection(keys)){
            MessageDialog("Invalid selection of keys");
        }
        ReloadTable();
        // TODO add your handling code here:
    }//GEN-LAST:event_TradeButtonActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField Balance;
    private javax.swing.JLabel BalanceTittle;
    private javax.swing.JLabel CantTittle;
    private javax.swing.JButton ConfigButton;
    private javax.swing.JButton DeleteButton;
    private javax.swing.JLabel InitialMessage;
    private javax.swing.JPanel InventoryPanel;
    private javax.swing.JLabel InventoryTittle;
    private javax.swing.JTable KeyTable;
    private javax.swing.JScrollPane KeyTableScroll;
    private javax.swing.JButton NewKeyButton;
    private javax.swing.JButton ReloadButton;
    private javax.swing.JButton SellButton;
    private javax.swing.JLabel TotalTittle;
    private javax.swing.JLabel TradTittle;
    private javax.swing.JButton TradeButton;
    private javax.swing.JLabel UntradTittle;
    // End of variables declaration//GEN-END:variables
}
