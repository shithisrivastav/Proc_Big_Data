/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package early_reviewers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 

public class BarChart_AWT extends ApplicationFrame {
     int yes=0;
           int no=0;
   public BarChart_AWT( String applicationTitle , String chartTitle ) {
      super( applicationTitle );        
       try {
           JFreeChart barChart = ChartFactory.createBarChart(
                   chartTitle,
                   "Reviews Result",
                   "Score",
                   createDataset(),
                   PlotOrientation.VERTICAL,
                   true, true, false);
         
           
     
           
           
           ChartPanel chartPanel = new ChartPanel( barChart );
           chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );
           setContentPane( chartPanel ); 
       } catch (Exception ex) {
           Logger.getLogger(BarChart_AWT.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   
   private CategoryDataset createDataset( ) {
         try {
             final String fiat = "Positive / Negative";
             //final String audi = "";
             //final String ford = "FORD";
             final String speed = "POSITIVE";        
             final String millage = "NEGATIVE";
             
             final DefaultCategoryDataset dataset =
                     new DefaultCategoryDataset( );        
             
             
             Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/amazon_review","root","");
             String sql = "select reviews_recommend from spammer_removal where review_date like '"+"%-03-03%"+"' OR review_date like '"+"%-02-24%"+"'";
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             
             while(rs.next())
             {
                 String review = rs.getString("reviews_recommend");
                 if(review.equals("TRUE"))
                 {
                     if(yes<=50){
                     yes++;}
                     
                 }
                 if(review.equals("FALSE")) 
                 {
                     no++;
                 }
             }
             
             
             
             
             dataset.addValue( yes , fiat , speed );
             
             dataset.addValue( no , fiat , millage );
             
             
             //dataset.addValue( 5.0 , audi , speed );
             
             // dataset.addValue( 10.0 , audi , millage );
             
             
             //dataset.addValue( 4.0 , ford , speed );
             
             //dataset.addValue( 3.0 , ford , millage );
             
             
             return dataset;
         } catch (SQLException ex) {
             Logger.getLogger(BarChart_AWT.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
          
   }
   
   public static void main( String[ ] args ) 
   {
      BarChart_AWT chart = new BarChart_AWT("Early Reviewers", 
         "Early Reviewers Prediction Result");
      chart.pack( );        
      RefineryUtilities.centerFrameOnScreen( chart );        
      chart.setVisible( true ); 
   }
}
