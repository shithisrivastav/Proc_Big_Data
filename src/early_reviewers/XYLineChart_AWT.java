package early_reviewers;

import java.awt.Color; 
import java.awt.BasicStroke; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.data.xy.XYDataset; 
import org.jfree.data.xy.XYSeries; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 
import org.jfree.chart.plot.XYPlot; 
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.data.xy.XYSeriesCollection; 
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

public class XYLineChart_AWT extends ApplicationFrame {

   public XYLineChart_AWT( String applicationTitle, String chartTitle ) {
      super(applicationTitle);
      JFreeChart xylineChart = ChartFactory.createXYLineChart(
         chartTitle ,
         "Early Reviews" ,
         "Users" ,
         createDataset() ,
         PlotOrientation.VERTICAL ,
         true , true , false);
         
      ChartPanel chartPanel = new ChartPanel( xylineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
      final XYPlot plot = xylineChart.getXYPlot( );
      
      XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
      renderer.setSeriesPaint( 0 , Color.BLUE );
      
      renderer.setSeriesStroke( 0 , new BasicStroke( 1 ) );
     
      plot.setRenderer( renderer ); 
      setContentPane( chartPanel ); 
   }
   
   private XYDataset createDataset( ) {
       try {
           final XYSeries mape = new XYSeries( "EARLY REVIEWS RATING" );
           
           
           Class.forName("com.mysql.jdbc.Driver");
           Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/amazon_review","root","");
           String sql = "select review from statistics";
           PreparedStatement ps = con.prepareStatement(sql);
           ResultSet rs = ps.executeQuery();
           int i=0;
           while(rs.next())
           {
               String review = rs.getString("review");
              int a=Integer.parseInt(review);
               
                i++;
                 mape.add( a , i );
                if(i==12)
                {
                    break;
                }
           }
          
           final XYSeriesCollection dataset = new XYSeriesCollection( );
           dataset.addSeries( mape );
           
           //dataset.addSeries( iexplorer );
           return dataset;
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(XYLineChart_AWT.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex) {
           Logger.getLogger(XYLineChart_AWT.class.getName()).log(Level.SEVERE, null, ex);
       }
       return null;
   }

   public static void main( String[ ] args ) {
      XYLineChart_AWT chart = new XYLineChart_AWT("Early Reviews",
         "Early Reviews");
      chart.pack( );          
      RefineryUtilities.centerFrameOnScreen( chart );          
      chart.setVisible( true ); 
   }
}
