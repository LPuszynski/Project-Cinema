/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//inspiration du code : https://openclassrooms.com/forum/sujet/comment-faire-une-barre-de-chargement-java
package View;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

/**
 *
 * @author loisp
 */
public class PaymentProgressBar extends JFrame{
  private Thread t;
  private JProgressBar bar;
  public PaymentProgressBar(){      
    this.setSize(300, 100);
    this.setTitle("*** JProgressBar ***");
    this.setLocationRelativeTo(null);      
    t = new Thread(new Traitement());
    bar  = new JProgressBar();
    bar.setMaximum(500);
    bar.setMinimum(0);
    bar.setStringPainted(true);
    this.getContentPane().add(bar, BorderLayout.CENTER);
    t = new Thread(new Traitement());
        t.start();
   
    this.setVisible(true);      
  }
  class Traitement implements Runnable{   
    public void run(){
      for(int val = 0; val <= 500; val++){
        bar.setValue(val);
        try {
          t.sleep(5);
        } catch (InterruptedException e) {
        e.printStackTrace();
        }
      }
      dispose();
    }   
  }

}
