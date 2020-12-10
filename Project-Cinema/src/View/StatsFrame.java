/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Cinema;
import Model.ReservationDB;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ChartPanel;

/**
 *
 * @author Tonio
 */
public class StatsFrame extends JFrame{
    
    public StatsFrame(Cinema cine)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //JScrollPane scrollPane = new JScrollPane(table);
        //add(scrollPane, BorderLayout.CENTER);
        setSize(1000, 1000);

        XYSeriesCollection dataset = new XYSeriesCollection();
        
        XYSeries series;
        for( int i=0 ; i<cine.getMovieList().size() ; ++i ){
            dataset.addSeries(ReservationDB.getAllReservationForMovieDB(cine.getMovieList().get(i).getTitle()));
        }

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Movies sales",
                "Projection dates",
                "Sales",
                dataset, 
                PlotOrientation.VERTICAL,
                true,
                true,
                false
                );
        ChartPanel chartPanel = new ChartPanel(chart);

        chartPanel.setBounds(10,10,100,100);
        chartPanel.setVisible(true);
        panel.add(chartPanel);
        
        setVisible(true);        
        this.add(panel);
    }
}
