
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
import steam.jewishs.steamkeysmaven.KeyManager;


public class GraphicInterface extends javax.swing.JFrame {
    
    DefaultListModel modelolistakeys;
    DefaultListModel modeloidlist;
    List<Long> ids;
    
    public GraphicInterface() {
        modelolistakeys = new DefaultListModel();
       // modeloidlist = new DefaultListModel();
        ids = new ArrayList();
        ListKeys();
        initComponents();
        //ListOfId.setModel(modeloidlist);
        Keys.setModel(modelolistakeys);
        this.setLocationRelativeTo(null);
        
    }
    private void ListKeys(){
        try {
            List<KeyDTO> keys = KeyManager.ListKeys();
            String mostrar;
            KeyDTO dto;
            Iterator iter = keys.iterator();
            while(iter.hasNext()){
                dto = (KeyDTO) iter.next();
                //String id = Long.toString(dto.getId());
                String type = dto.getType();
                String state = dto.getState();
                mostrar = type + " " + state;
               // modeloidlist.addElement(id);
                modelolistakeys.addElement(mostrar);
                ids.add(dto.getId());
            }
        } catch (NonexistentEntityException ex) {
            System.out.println("FALLA");// Logger.getLogger(GraphicInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private void DeleteKey(){
        int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete it?");
        if (confirmation == 0){
            int pos = Keys.getSelectedIndex();
            //String id = (String) modeloidlist.getElementAt(pos);
            Long id = ids.get(pos);
            
            KeyDTO dto = new KeyDTO();
            dto.setId(id);
            KeyManager.DeleteKey(dto);
            
           // modeloidlist.remove(pos);
            modelolistakeys.remove(pos);
        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        InventoryTittle = new javax.swing.JLabel();
        InitialMessage = new javax.swing.JLabel();
        DeleteButton = new javax.swing.JButton();
        KeyTittle = new javax.swing.JLabel();
        KeysScrollPane = new javax.swing.JScrollPane();
        Keys = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        InventoryTittle.setFont(new java.awt.Font("Comic Sans MS", 3, 14)); // NOI18N
        InventoryTittle.setText("Inventory");

        InitialMessage.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        InitialMessage.setText("Welcome to JewishApp");

        DeleteButton.setText("Delete Key");
        DeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteButtonActionPerformed(evt);
            }
        });

        KeyTittle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        KeyTittle.setText("Keys");

        Keys.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        KeysScrollPane.setViewportView(Keys);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(88, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DeleteButton)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(InitialMessage)
                        .addGap(174, 174, 174))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(KeysScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(338, 338, 338))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(InventoryTittle, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(KeyTittle)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(InitialMessage)
                .addGap(17, 17, 17)
                .addComponent(InventoryTittle, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(KeyTittle)
                .addGap(11, 11, 11)
                .addComponent(KeysScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(DeleteButton)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleName("MenuWindow");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteButtonActionPerformed
       DeleteKey(); // TODO add your handling code here:
    }//GEN-LAST:event_DeleteButtonActionPerformed

   


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DeleteButton;
    private javax.swing.JLabel InitialMessage;
    private javax.swing.JLabel InventoryTittle;
    private javax.swing.JLabel KeyTittle;
    private javax.swing.JList<String> Keys;
    private javax.swing.JScrollPane KeysScrollPane;
    // End of variables declaration//GEN-END:variables
}
