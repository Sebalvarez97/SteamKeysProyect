/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Controller.exceptions.NonexistentEntityException;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import steam.jewishs.steamkeysmaven.KeyManager;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


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
        LoadChart();
    }
    
    private void LoadChart(){
    try {
        this.ChartLabel.setIcon( new XYLineChart( this.ChartPanel.getSize() ) );
        this.ChartLabel.setText("");
    } catch (Exception ex) {
        MessageDialog(ex.getMessage());
    }
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
    public class XYLineChart extends ImageIcon{

    /** Constructor de clase 
 * @param d Dimension
 */
    public XYLineChart( Dimension d ) throws Exception{

        //se declara el grafico XY Lineal
        XYDataset xydataset = xyDataset();
        JFreeChart jfreechart = ChartFactory.createXYLineChart(
        "Money Chart" , "Time", "Total Money",  
        xydataset, PlotOrientation.VERTICAL,  true, true, false);               

        //personalización del grafico
        XYPlot xyplot = (XYPlot) jfreechart.getPlot();
        xyplot.setBackgroundPaint( Color.white );
        xyplot.setDomainGridlinePaint( Color.BLACK );
        xyplot.setRangeGridlinePaint( Color.BLACK );        
        // -> Pinta Shapes en los puntos dados por el XYDataset
        XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) xyplot.getRenderer();
        xylineandshaperenderer.setDefaultShapesVisible(true);
        //--> muestra los valores de cada punto XY
        XYItemLabelGenerator xy = new StandardXYItemLabelGenerator();
        xylineandshaperenderer.setDefaultItemLabelGenerator(xy);
        xylineandshaperenderer.setDefaultItemLabelsVisible(true);
        xylineandshaperenderer.setDefaultLinesVisible(true);
        xylineandshaperenderer.setDefaultItemLabelsVisible(true);                
        //fin de personalización

        //se crea la imagen y se asigna a la clase ImageIcon
        BufferedImage bufferedImage  = jfreechart.createBufferedImage( d.width, d.height);
        this.setImage(bufferedImage);
    }

    private XYDataset xyDataset() throws Exception
    {
        //se declaran las series y se llenan los datos
        XYSeries sIngresos = new XYSeries("Ingresos");
//        XYSeries sEgresos = new XYSeries("Egresos");        
        //serie #1
        List<Integer[]> list = KeyManager.ListHistory();
        for(Integer[] ob : list){
            int x = ob[0];
            String y = KeyManager.numberConvertor(ob[1]);
            sIngresos.add(x, Double.parseDouble(y));
        }
//        sIngresos.add( 1, 340);
//        sIngresos.add( 2, 210);
//        sIngresos.add( 3, 410);
//        sIngresos.add( 4, 200);
//        sIngresos.add( 5, 525);
//        sIngresos.add( 6, 492);
//        sIngresos.add( 7, 390);
        //serie #2
//        sEgresos.add( 1, 90);
//        sEgresos.add( 2, 434);
//        sEgresos.add( 3, 741);
//        sEgresos.add( 4, 91);
//        sEgresos.add( 5, 412);
//        sEgresos.add( 6, 361);
//        sEgresos.add( 7, 271);

        XYSeriesCollection xyseriescollection =  new XYSeriesCollection();
        xyseriescollection.addSeries( sIngresos );        
//        xyseriescollection.addSeries( sEgresos );        

        return xyseriescollection;
    }
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TradeHistoryScroll = new javax.swing.JScrollPane();
        TradeHistoryTable = new javax.swing.JTable();
        BackButton = new javax.swing.JButton();
        ReloadButton = new javax.swing.JButton();
        ChartPanel = new javax.swing.JPanel();
        ChartLabel = new javax.swing.JLabel();

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

        javax.swing.GroupLayout ChartPanelLayout = new javax.swing.GroupLayout(ChartPanel);
        ChartPanel.setLayout(ChartPanelLayout);
        ChartPanelLayout.setHorizontalGroup(
            ChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ChartPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ChartLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        ChartPanelLayout.setVerticalGroup(
            ChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ChartPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ChartLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(ReloadButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 649, Short.MAX_VALUE)
                .addComponent(BackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(ChartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TradeHistoryScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE))
                .addGap(94, 94, 94))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ChartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
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
    private javax.swing.JLabel ChartLabel;
    private javax.swing.JPanel ChartPanel;
    private javax.swing.JButton ReloadButton;
    private javax.swing.JScrollPane TradeHistoryScroll;
    private javax.swing.JTable TradeHistoryTable;
    // End of variables declaration//GEN-END:variables
}
