/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.util.Iterator;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import steam.jewishs.steamkeysmaven.KeyManager;

/**
 *
 * @author eltet
 */
public class History extends Interface {

DefaultTableModel modelotrades = new DefaultTableModel();
    public History() {
        initComponents();
        initICon();
        this.setSize(Inventory.getLastWindow().getSize());
        this.setLocationRelativeTo(Inventory.getLastWindow());
        initHistory();
    }

    public void initHistory(){
        Reload();
    }
    private void SetHistory(){
        TradeHistoryTable.getTableHeader().setResizingAllowed(false);
        TableColumn columnaid = TradeHistoryTable.getColumn("TradeID");
        columnaid.setPreferredWidth(50);
        columnaid.setMaxWidth(100);
    }
    public void Reload(){
        ListHistory();
        SetHistory();
    }
    

    private void ListHistory(){
        try {
            List<Object[]> trades = KeyManager.getTradeList();
            Iterator iter = trades.iterator();
            String[] columnames = {"TradeID","Date", "CantKeys", "Profit/key", "Left", "StorePrice" };
            modelotrades = new DefaultTableModel(null, columnames);
            //LLENADO
            while(iter.hasNext()){
                Object[] listelement = (Object[]) iter.next();
                Object[] row = {listelement[0], listelement[1], listelement[2], "$ "+listelement[3], "$ " +listelement[4], "$ " +listelement[5]};
                modelotrades.addRow(row);
            }
            TradeHistoryTable.setModel(modelotrades);
        } catch (Exception ex) {
            MessageDialog(ex.getMessage());
        }
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TradeHistoryScroll = new javax.swing.JScrollPane();
        TradeHistoryTable = new javax.swing.JTable();
        BackButton = new javax.swing.JButton();
        ReloadButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TradeHistoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        TradeHistoryTable.setFocusable(false);
        TradeHistoryScroll.setViewportView(TradeHistoryTable);
        if (TradeHistoryTable.getColumnModel().getColumnCount() > 0) {
            TradeHistoryTable.getColumnModel().getColumn(0).setResizable(false);
            TradeHistoryTable.getColumnModel().getColumn(1).setResizable(false);
            TradeHistoryTable.getColumnModel().getColumn(3).setResizable(false);
        }

        BackButton.setText("Back");
        BackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackButtonActionPerformed(evt);
            }
        });

        ReloadButton.setText("Reload");
        ReloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReloadButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(ReloadButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 465, Short.MAX_VALUE)
                .addComponent(BackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TradeHistoryScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BackButton)
                    .addComponent(ReloadButton))
                .addGap(18, 18, 18)
                .addComponent(TradeHistoryScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(271, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackButtonActionPerformed
          BackToInventory();// TODO add your handling code here:
    }//GEN-LAST:event_BackButtonActionPerformed

    private void ReloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReloadButtonActionPerformed
        Reload();// TODO add your handling code here:
    }//GEN-LAST:event_ReloadButtonActionPerformed




    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackButton;
    private javax.swing.JButton ReloadButton;
    private javax.swing.JScrollPane TradeHistoryScroll;
    private javax.swing.JTable TradeHistoryTable;
    // End of variables declaration//GEN-END:variables
}
