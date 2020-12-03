/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

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
    private int choiceMember;

    public GUI() throws HeadlessException {
        setTitle("Connexion");
        setBounds(700, 250, 600, 500);
        BuildConnexionScreen();
        BuildLoginScreen();
        setContentPane(connexionScreen);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void BuildConnexionScreen() {
        connexionScreen = new JPanel();
        JLabel message1 = new JLabel("Are you a member?");
        message1.setBounds(150, 160, 200, 25);
        connexionScreen.add(message1);
        JRadioButton buttonYes = new JRadioButton("Yes");
        buttonYes.setBounds(300, 160, 50, 25);
        connexionScreen.add(buttonYes);
        JRadioButton buttonNo = new JRadioButton("No");
        buttonNo.setBounds(350, 160, 50, 25);
        connexionScreen.add(buttonNo);
        buttonYes.addActionListener(new RadioButtonYesListener());
        buttonNo.addActionListener(new RadioButtonNoListener());
        ButtonGroup group1 = new ButtonGroup();
        group1.add(buttonNo);
        group1.add(buttonYes);
        JButton buttonEmployee = new JButton("If you are an employee click here");
        buttonEmployee.setBounds(350, 400, 220, 35);
        connexionScreen.add(buttonEmployee);
        buttonEmployee.addActionListener(new ButtonEmployeeListener());
        JButton buttonValidate = new JButton("Validate");
        buttonValidate.setBounds(220, 250, 100, 25);
        connexionScreen.add(buttonValidate);
        buttonValidate.addActionListener(new ButtonValidateListener());
        connexionScreen.setLayout(null);
    }

    public void BuildLoginScreen() {
        loginScreen = new JPanel();
        JLabel message1 = new JLabel("Login : ");
        message1.setBounds(190, 100, 100, 50);
        loginScreen.add(message1);
        JTextField textFieldLogin = new JTextField(10);
        textFieldLogin.setBounds(250, 111, 150, 25);
        loginScreen.add(textFieldLogin);
        JLabel message2 = new JLabel("Password : ");
        message2.setBounds(170, 200, 100, 50);
        loginScreen.add(message2);
        JTextField textFieldPassword = new JTextField(5);
        textFieldPassword.setBounds(250, 211, 150, 25);
        loginScreen.add(textFieldPassword);
        loginScreen.setLayout(null);
    }

    private class RadioButtonYesListener implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            choiceMember = 1;
        }

    }

    private class RadioButtonNoListener implements ActionListener {

        public void actionPerformed(ActionEvent ae) {

            choiceMember = 0;
        }

    }

    private class ButtonValidateListener implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            if (choiceMember == 1) {
                System.out.println("On m'a cliqué dessus Yes");
                setContentPane(loginScreen);
                invalidate();
                validate();
            } else if (choiceMember == 0) {
                System.out.println("On m'a cliqué dessus No");
            }
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
