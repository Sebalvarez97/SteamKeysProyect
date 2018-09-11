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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class History extends Interface {

DefaultTableModel modelotrades = new DefaultTableModel();
    //CONSTRUCTOR
    public History() {
        initComponents();
        initICon();
        this.setSize(Inventory.getLastWindow().getSize());
        this.setLocationRelativeTo(Inventory.getLastWindow());
        initHistory();
    }
    //INICIA LA PANTALLA
    public void initHistory(){
        Reload();
    }
    //SETEA LOS PARAMETROS DE LA PANTALLA
    private void SetHistory(){
        TableColumn columnaid = TradeHistoryTable.getColumn("TradeID");
        columnaid.setPreferredWidth(50);
        columnaid.setMaxWidth(100);
        TradeHistoryTable.getTableHeader().setResizingAllowed(false);
    }
    //CLASE PADRE, RECARGA LA PAGINA
    public void Reload(){
        ListHistory();
        SetHistory();
        LoadChart();
    }
    //CARGA EL CHART
    private void LoadChart(){
    try {
        ChartPanel.setSize(791,305);
        XYLineChart chart = new XYLineChart(ChartPanel.getSize());
        ChartLabel.setIcon(new ImageIcon(chart.getImage()));
        ChartPanel.updateUI();
    } catch (Exception ex) {
        MessageDialog(ex.getMessage());
    }
    }
    //MUESTRA LA LISTA DE TRADES
    private void ListHistory(){
        try {
            List<Object[]> trades = KeyManager.getTradeList();
            Iterator iter = trades.iterator();
            String[] columnames = {"TradeID","Date", "CantKeys", "Profit/key", "Left", "KeyPrice" };
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
    //CLASE NECESARIA PARA EL CHART
public class XYLineChart extends ImageIcon{
    
    double maxy = 0;
    double miny = 0;
    double maxx = 0;
    double minx = 0;
            
    public XYLineChart( Dimension d ) throws Exception{
        //se declara el grafico XY Lineal
        XYDataset xydataset = xyDataset();
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(
        "Money Chart" , "Day", "Total Money",  
        xydataset,  true, true, false);               
    
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
        xylineandshaperenderer.setDefaultItemLabelsVisible(false);
        xylineandshaperenderer.setDefaultLinesVisible(true);             
        
        xyplot.setNoDataMessage("NO DATA");
        //SET RANGE OF AXIS
//        NumberAxis domain = (NumberAxis) xyplot.getDomainAxis();
        DateAxis domain = (DateAxis) xyplot.getDomainAxis();
        domain.setDateFormatOverride(new SimpleDateFormat("dd/MM"));
//        domain.setRange(minx-1,maxx+1);
        domain.setVerticalTickLabels(true);
        NumberAxis range = (NumberAxis) xyplot.getRangeAxis();;
        range.setRange(miny-50, maxy+50);
        range.setTickUnit(new NumberTickUnit(100.00));
        //fin de personalización
        //se crea la imagen y se asigna a la clase ImageIcon
        BufferedImage bufferedImage  = jfreechart.createBufferedImage( d.width , d.height);
        this.setImage(bufferedImage);
    }

    private XYDataset xyDataset() throws Exception
    {
        //se declaran las series y se llenan los datos
        TimeSeries sIngresos = new TimeSeries("Ingresos");
        //XYSeries keyMoney = new XYSeries("Money in keys");
        //serie #1
        List<Object[]> list = KeyManager.ListHByType("Total");
        
        Date fecha = (Date) list.get(0)[0];
        double hvalue = (int) list.get(0)[1];
        miny = hvalue/100;
        
        for(Object[] ob : list){
            hvalue = (int) ob[1];
            double value = hvalue/100;
            fecha = (Date) ob[0];
//            if(day>maxx){
//                maxx = day;
//            }else if(day<minx){
//                minx = day;
//            }
            if(value>maxy){
                maxy = value;
            }else if(value<miny){
                miny =  value;
            }
            Minute dia = new Minute(fecha);
            sIngresos.addOrUpdate(dia,value);
        }
        TimeSeriesCollection xyseriescollection =  new TimeSeriesCollection();
        xyseriescollection.addSeries( sIngresos );        
//        xyseriescollection.addSeries( keyMoney ); 
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
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setFocusable(false);
        setResizable(false);

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
            .addComponent(ChartLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ChartPanelLayout.setVerticalGroup(
            ChartPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ChartLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel1.setText("Trades");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(TradeHistoryScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(111, 111, 111))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(ChartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(56, 56, 56))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(ReloadButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BackButton, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BackButton)
                    .addComponent(ReloadButton))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TradeHistoryScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ChartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(134, Short.MAX_VALUE))
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
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
