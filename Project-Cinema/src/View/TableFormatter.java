/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.*;
import javax.swing.*;
import javax.swing.WindowConstants;

/**
 *
 * @author charl
 */
public class TableFormatter extends JFrame{
    public TableFormatter(Object[][] data, Object[] colNames)
    {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JTable table = new JTable(data, colNames);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        setSize(600, 1000);
        setVisible(true);
    }
    public TableFormatter(){
        
    }
}
