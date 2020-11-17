/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author charl
 */
public class GUI extends JFrame {

    private JPanel connexionScreen;
    private JPanel loginScreen;

    public GUI() throws HeadlessException {
        setTitle("Connexion");
        setSize(300, 300);
        setBounds(700, 250, 600, 500);
        BuildConnexionScreen();
        BuildLoginScreen();
        setContentPane(connexionScreen);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void BuildConnexionScreen() {
        connexionScreen = new JPanel();
        JLabel message1 = new JLabel("Are you a member?");
        connexionScreen.add(message1);
        JRadioButton buttonYes = new JRadioButton("Yes");
        connexionScreen.add(buttonYes);
        JRadioButton buttonNo = new JRadioButton("No");
        connexionScreen.add(buttonNo);
        buttonYes.addActionListener(new RadioButtonYesListener());
        buttonNo.addActionListener(new RadioButtonNoListener());
        ButtonGroup group1 = new ButtonGroup();
        group1.add(buttonNo);
        group1.add(buttonYes);
        JButton buttonEmployee = new JButton("If you are an employee click here");
        connexionScreen.add(buttonEmployee);
        buttonEmployee.addActionListener(new ButtonEmployeeListener());
    }

    public void BuildLoginScreen() {
        loginScreen = new JPanel();
        JLabel message1 = new JLabel("Login : ");
        loginScreen.add(message1);
        JLabel message2 = new JLabel("Password : ");
        loginScreen.add(message2);
        JTextField textFieldLogin = new JTextField(10);
        loginScreen.add(textFieldLogin);
        JTextField textFieldPassword = new JTextField(5);
        loginScreen.add(textFieldPassword);
    }

    private class RadioButtonYesListener implements ActionListener {

        public void actionPerformed(ActionEvent ae) {

            System.out.println("On m'a cliqué dessus Yes");
            setContentPane(loginScreen);
            invalidate();
            validate();

        }

    }

    private class RadioButtonNoListener implements ActionListener {

        public void actionPerformed(ActionEvent ae) {

            System.out.println("On m'a cliqué dessus No");
            //setContentPane(loginScreen);
            invalidate();
            validate();

        }

    }

    private class ButtonEmployeeListener implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            System.out.println("On m'a cliqué dessus Employee");
        }
    }

    public static void main(String[] args) {
        GUI f = new GUI();
        f.setVisible(true);
    }
}
