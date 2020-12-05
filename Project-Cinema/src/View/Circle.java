/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author loisp
 */
public class Circle extends JPanel {

    char colorCircle, colorBack;
    String discountAmount;

    public Circle(char colorCircle, char colorBack, String discountAmount) {
        this.colorCircle = colorCircle;
        this.colorBack = colorBack;
        this.discountAmount = discountAmount;
    }





    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (colorBack == 'b') {
            g.setColor(Color.blue);
        }
        if (colorBack == 'c') {
            g.setColor(Color.cyan);
        }
        if (colorBack == 'm') {
            g.setColor(Color.magenta);
        }
        if (colorBack == 'y') {
            g.setColor(Color.yellow);
        }
        if (colorBack == 'r') {
            g.setColor(Color.red);
        }
        g.fillRect(0, 0, 100, 50);

        if (colorCircle == 'b') {
            g.setColor(Color.black);
        }
        if (colorCircle == 'c') {
            g.setColor(Color.cyan);
        }
        if (colorCircle == 'm') {
            g.setColor(Color.magenta);
        }
        if (colorCircle == 'y') {
            g.setColor(Color.yellow);
        }
        if (colorCircle == 'r') {
            g.setColor(Color.red);
        }
        
        g.fillOval(0, 0, 100, 50);
        
        g.setColor(Color.white);
        
        g.drawString(discountAmount, 35, 30 );
    }
}
