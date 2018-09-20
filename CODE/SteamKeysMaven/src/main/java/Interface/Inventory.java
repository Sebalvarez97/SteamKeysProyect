
package Interface;

import Controller.exceptions.NonexistentEntityException;
import TransporterUnits.KeyDTO;
import TransporterUnits.TradeDTO;
import TransporterUnits.TypeStateDTO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import steam.jewishs.steamkeysmaven.KeyManager;

import java.awt.image.BufferedImage;
import java.util.Date;
import javax.swing.ImageIcon;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class Inventory extends Interface{
    
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
        initICon();
        initSaldo();
        OpenWindow(null);
        Reload();
        this.setLocationRelativeTo(getLastWindow());
        OpenWindow(this);
    }
    //CARGA EL CHART
    private void LoadChart(){
        try {
            JFreeChart barra = null;
            DefaultCategoryDataset datos;
            datos = new DefaultCategoryDataset();
            int total = KeyManager.getTotalMoney();
            int valorenkeys = KeyManager.getKeysMoney();
            datos.setValue(Double.parseDouble(KeyManager.numberConvertor(total)), "Total", "");
            datos.setValue(Double.parseDouble(KeyManager.numberConvertor(valorenkeys)), "KeysValue", "");
            
            barra = ChartFactory.createBarChart("Money Chart", "","$",datos,PlotOrientation.VERTICAL,true,true,true);
            BufferedImage graficoBarra=barra.createBufferedImage(ChartPanel.getWidth(), ChartPanel.getHeight());
            
            ChartLabel.setSize(ChartPanel.getSize());
            ChartLabel.setIcon(new ImageIcon(graficoBarra));
            ChartPanel.updateUI();
        } catch (NonexistentEntityException ex) {
            MessageDialog(ex.getMessage());
        }
    }
    //CONFIGURACION DE LA TABLA
    private void SetKeyTable(){
        KeyTable.getTableHeader().setResizingAllowed(false);//DESABILITA LA EDICION DE LAS COLUMNAS
        TableColumn columnaid = KeyTable.getColumn("ID");
        columnaid.setPreferredWidth(40);
        columnaid.setMaxWidth(100);
        Balance.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
                
            }
            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_ENTER){
                    SetEstadistics();
                    LoadChart();
                }
            }
            @Override
            public void keyReleased(KeyEvent ke) {
                
            }
        });
    }
    //RECARGA LA LISTA DE LLAVES
    protected void Reload(){
        ListTable();
        SetKeyTable();
        SetEstadistics();
        LoadChart();
    }
    //INICIALIZA EL SALDO DE STEAM CON EL VALOR GUARDADO EN BASE DE DATOS
    public void initSaldo(){
        int saldo = KeyManager.getBalanceMoney();
        Balance.setText(KeyManager.numberConvertor(saldo));
    }
    //DEVUELVE LA ENTRADA DEL JTEXTFIELD DEL SALDO DE STEAM
    private int getBalanceInput() throws Exception{
        String saldo = Balance.getText();
        return KeyManager.numberConvertor(saldo);
    }
    //ACTUALIZA EL VALOR DEL SALDO DE STEAM
    private void UpdateSaldo() throws Exception {
            int input = getBalanceInput();
            KeyManager.UpdateSaldo(input);
    }

    //MUESTRA LAS ESTADISTICAS DE LAS LLAVES (CANT TOTAL, TRADEABLES Y NO TRADEABLES, ETC)
    private void SetEstadistics(){
        try {
            UpdateSaldo();
            int tradeables = KeyManager.CantWithState(new TypeStateDTO("Tradeable"));
            int untradeables = KeyManager.CantWithState(new TypeStateDTO("Untradeable"));
            int cantidad = tradeables + untradeables;
            int totalmoney = KeyManager.getTotalMoney();
            CantTittle.setText("Cant.Keys: "+Integer.toString(cantidad));
            TradTittle.setText("Tradeables: "+Integer.toString(tradeables));
            UntradTittle.setText("Untradeables: "+Integer.toString(untradeables));
            TotalTittle.setText("TOTAL   "+ "$ "+KeyManager.numberConvertor(totalmoney) + "  (Can buy: "+ KeyManager.getCantUcanBuy(KeyManager.getBalanceMoney()) +")");
            
        } catch (Exception ex) {
            MessageDialog(ex.getMessage());
        }
    }
    //CONFIGURA EL MODELO DE LA TABLA DE LLAVES Y LAS LISTA
    private void ListTable(){
           try {
            KeyManager.UpdateState();
            List<KeyDTO> keys = KeyManager.ListKeysOrderByDate();;
            //CONFIGURACION DEL MODELO DE LA TABLA
            String[] columnames = {"ID", "Type","BuyDate", "State","Release in"};
            modelotabla = new DefaultTableModel(null, columnames);
            //LENADO DEL MODELO DE LA TABLA
            for(KeyDTO dto : keys){
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
                    days = String.valueOf(day+1);
                   }
               }
               Date release = KeyManager.ReleaseDate(dto.getbuydate());
               Object[] row = {dto.getId(), type , date , state ,"("+ days + ")  "+KeyManager.SimpleFormatDate(release)};
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

    //BORRA LAS LLAVES SELECCIONADAS
    private void DeleteKey(){ 
        List<KeyDTO> keys = getKeySelection();
      if(keys.size() == 0){
            MessageDialog("Select a key first");
      }else{
        int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete selected keys?");
        if (confirmation == 0){
           for(KeyDTO dto : keys){
               try {
                   KeyManager.DeleteKey(dto);
               } catch (NonexistentEntityException ex) {
                   MessageDialog(ex.getMessage());
               }
            }
        }
      }
        initSaldo();
        Reload();
    }
    //ABRE UNA VENTANA DEL TIPO INGRESADO
    private void AddWindow(JFrame window){
        window.setTitle("SteamKeysApp");
        OpenWindow(window);
        this.setVisible(false);
        window.setVisible(true);
    }
    //VENDE UNA LLAVE O LLAVES
    private void SellKey(){
        List<KeyDTO> keys = getKeySelection();
        if(keys.size() == 0){
            MessageDialog("Select a key first");
        }else{
            try {
                int sellprice = KeyManager.numberConvertor(JOptionPane.showInputDialog("Ingrese el precio de venta"));
                int confirm = JOptionPane.showConfirmDialog(this, "You are selling this " + keys.size()+ " keys. Are you sure?");
                if(confirm == 0){
                    for(KeyDTO key : keys){
                        KeyManager.SellKey(key, sellprice);
                    }
                }
            } catch (Exception ex) {
                MessageDialog(ex.getMessage());
            }
        }
        initSaldo();
        Reload();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        InventoryPanel = new javax.swing.JPanel();
        InitialMessage = new javax.swing.JLabel();
        InventoryTittle = new javax.swing.JLabel();
        ConfigButton = new javax.swing.JButton();
        HistoryButton = new javax.swing.JButton();
        KeyPanel = new javax.swing.JPanel();
        TradeButton = new javax.swing.JButton();
        Balance = new javax.swing.JFormattedTextField();
        TotalTittle = new javax.swing.JLabel();
        SellButton = new javax.swing.JButton();
        NewKeyButton = new javax.swing.JButton();
        DeleteButton = new javax.swing.JButton();
        BalanceTittle = new javax.swing.JLabel();
        ChartPanel = new javax.swing.JPanel();
        ChartLabel = new javax.swing.JLabel();
        KeyTableScroll = new javax.swing.JScrollPane();
        KeyTable = new javax.swing.JTable();
        CantTittle = new javax.swing.JLabel();
        UntradTittle = new javax.swing.JLabel();
        TradTittle = new javax.swing.JLabel();
        ReloadButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("InventoryFrame"); // NOI18N
        setResizable(false);

        InitialMessage.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        InitialMessage.setText("Welcome to JewishApp");

        InventoryTittle.setFont(new java.awt.Font("Comic Sans MS", 3, 18)); // NOI18N
        InventoryTittle.setText("Inventory");

        ConfigButton.setText("...");
        ConfigButton.setFocusable(false);
        ConfigButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConfigButtonActionPerformed(evt);
            }
        });

        HistoryButton.setText("History");
        HistoryButton.setFocusable(false);
        HistoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HistoryButtonActionPerformed(evt);
            }
        });

        TradeButton.setText("Trade");
        TradeButton.setFocusable(false);
        TradeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TradeButtonActionPerformed(evt);
            }
        });

        Balance.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        Balance.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        Balance.setName("Balance"); // NOI18N
        Balance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BalanceActionPerformed(evt);
            }
        });

        TotalTittle.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        TotalTittle.setText("Total");

        SellButton.setText("Sell");
        SellButton.setFocusable(false);
        SellButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SellButtonActionPerformed(evt);
            }
        });

        NewKeyButton.setText("Buy Key");
        NewKeyButton.setFocusable(false);
        NewKeyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewKeyButtonActionPerformed(evt);
            }
        });

        DeleteButton.setText("Delete Key");
        DeleteButton.setFocusable(false);
        DeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteButtonActionPerformed(evt);
            }
        });

        BalanceTittle.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        BalanceTittle.setText("Balance");

        javax.swing.GroupLayout ChartPanelLayout = new javax.swing.GroupLayout(ChartPanel);
        ChartPanel.setLayout(ChartPanelLayout);
        ChartPanelLayout.setHorizontalGroup(
            ChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ChartPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(ChartLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        ChartPanelLayout.setVerticalGroup(
            ChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ChartLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        KeyTable.setAutoCreateRowSorter(true);
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

        CantTittle.setText("Cant. Keys:");

        UntradTittle.setText("Untradeables:");

        TradTittle.setText("Tradeables:");

        javax.swing.GroupLayout KeyPanelLayout = new javax.swing.GroupLayout(KeyPanel);
        KeyPanel.setLayout(KeyPanelLayout);
        KeyPanelLayout.setHorizontalGroup(
            KeyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(KeyPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(KeyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(KeyPanelLayout.createSequentialGroup()
                        .addComponent(TotalTittle)
                        .addContainerGap())
                    .addGroup(KeyPanelLayout.createSequentialGroup()
                        .addGroup(KeyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(KeyPanelLayout.createSequentialGroup()
                                .addGroup(KeyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(KeyPanelLayout.createSequentialGroup()
                                        .addComponent(BalanceTittle)
                                        .addGap(18, 18, 18)
                                        .addComponent(Balance, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(KeyPanelLayout.createSequentialGroup()
                                        .addGap(161, 161, 161)
                                        .addComponent(SellButton, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(TradeButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, KeyPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(KeyTableScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)))
                        .addGroup(KeyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, KeyPanelLayout.createSequentialGroup()
                                .addComponent(ChartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, KeyPanelLayout.createSequentialGroup()
                                .addComponent(NewKeyButton)
                                .addGap(18, 18, 18)
                                .addComponent(DeleteButton)
                                .addGap(69, 69, 69))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, KeyPanelLayout.createSequentialGroup()
                                .addComponent(CantTittle)
                                .addGap(49, 49, 49)
                                .addComponent(UntradTittle)
                                .addGap(48, 48, 48)
                                .addComponent(TradTittle)
                                .addGap(20, 20, 20))))))
        );
        KeyPanelLayout.setVerticalGroup(
            KeyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(KeyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(KeyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ChartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(KeyTableScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(KeyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BalanceTittle)
                    .addComponent(Balance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(KeyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TotalTittle)
                    .addComponent(CantTittle)
                    .addComponent(UntradTittle)
                    .addComponent(TradTittle, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(KeyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TradeButton)
                    .addComponent(DeleteButton)
                    .addComponent(SellButton)
                    .addComponent(NewKeyButton))
                .addGap(25, 25, 25))
        );

        Balance.getAccessibleContext().setAccessibleName("Balance");

        ReloadButton.setText("Reload");
        ReloadButton.setFocusable(false);
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
                .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InventoryPanelLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(InventoryTittle, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(InventoryPanelLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(ConfigButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(HistoryButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(InitialMessage)
                .addGap(180, 180, 180)
                .addComponent(ReloadButton)
                .addGap(36, 36, 36))
            .addGroup(InventoryPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(KeyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        InventoryPanelLayout.setVerticalGroup(
            InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InventoryPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InventoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ConfigButton)
                        .addComponent(HistoryButton))
                    .addComponent(InitialMessage)
                    .addComponent(ReloadButton))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(InventoryTittle, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(KeyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(InventoryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(InventoryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
        Reload();       // TODO add your handling code here:
    }//GEN-LAST:event_ReloadButtonActionPerformed

    private void ConfigButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfigButtonActionPerformed
        try {
            if(ValidateUser()){
                AddWindow(new Configurations());
            }
            
        } catch (Exception ex) {
            MessageDialog(ex.getMessage());
        }
      
    }//GEN-LAST:event_ConfigButtonActionPerformed


    
    private void TradeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TradeButtonActionPerformed
        List<KeyDTO> keys = getKeySelection();
        if(keys.isEmpty()){
            int confirmacion = JOptionPane.showConfirmDialog(this, "You did not select a key. It is correct?");
            if(confirmacion == 0){
                    Trade window = new Trade(new TradeDTO(250,0));
                    AddWindow(window);
            }    
        }else if(KeyManager.ValidateTradeSelection(keys)) {
            int confirmacion = JOptionPane.showConfirmDialog(this, "This key/s selected will be traded, Are you sure?");
            if(confirmacion == 0){
                    Trade window = new Trade(new TradeDTO(250,0,keys));
                    AddWindow(window);
            }
        }else if(!KeyManager.ValidateTradeSelection(keys)){
            MessageDialog("Invalid selection of keys");
        }
        Reload();
        // TODO add your handling code here:
    }//GEN-LAST:event_TradeButtonActionPerformed

    private void SellButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SellButtonActionPerformed
       SellKey(); // TODO add your handling code here:
    }//GEN-LAST:event_SellButtonActionPerformed

    private void HistoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HistoryButtonActionPerformed
     AddWindow(new History());
       // TODO add your handling code here:
    }//GEN-LAST:event_HistoryButtonActionPerformed

    private void BalanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BalanceActionPerformed
      
    }//GEN-LAST:event_BalanceActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField Balance;
    private javax.swing.JLabel BalanceTittle;
    private javax.swing.JLabel CantTittle;
    private javax.swing.JLabel ChartLabel;
    private javax.swing.JPanel ChartPanel;
    private javax.swing.JButton ConfigButton;
    private javax.swing.JButton DeleteButton;
    private javax.swing.JButton HistoryButton;
    private javax.swing.JLabel InitialMessage;
    private javax.swing.JPanel InventoryPanel;
    private javax.swing.JLabel InventoryTittle;
    private javax.swing.JPanel KeyPanel;
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
