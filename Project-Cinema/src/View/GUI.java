/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import Controller.Main;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Controller.*;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;

/**
 *
 * @author charl
 */
public class GUI extends JFrame {

    private JPanel connexionScreen;
    private JPanel loginScreen;
    private JPanel menuScreen;
    private JPanel film1;
    private JPanel film2;
    private JPanel film3;
    private int choiceUser;   // 0 = guest / 1 = member / 2 = employee
    private JTextField textFieldLogin;
    private JPasswordField textFieldPassword;
    private boolean boolLogin;
    private String login;
    private String password;

    public GUI() throws HeadlessException {
        setTitle("Cinema");
        setBounds(150, 80, 1600, 920);
        BuildConnexionScreen();
        BuildLoginScreen();
        BuildMenuScreen();
        //setContentPane(connexionScreen);
        setContentPane(menuScreen);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void BuildFilmPanel(JPanel film) {
        JLabel title = new JLabel("Scarface");
        JLabel date = new JLabel("1985");
        JLabel hour = new JLabel("2H30");
        JLabel freeSeats = new JLabel("50 sieges dispo");
        JLabel quantity = new JLabel("Quantity");
        JLabel image = new JLabel(new ImageIcon("C:\\Users\\loisp\\OneDrive\\Documents\\GitHub\\Project-Cinema\\Project-Cinema\\build\\classes\\View\\Scarface.jpg"));
        JPanel discount = new Circle('r', 'y', "2 euros"); //mettre la bonne couleur de font en fonction de la couleur du film
        JTextField quantityField = new JTextField(2);
        JButton buy = new JButton("Buy");

        title.setBounds(300, 20, 200, 20);
        date.setBounds(250, 60, 150, 20);
        hour.setBounds(250, 100, 150, 20);
        freeSeats.setBounds(250, 140, 150, 20);
        quantity.setBounds(300, 180, 150, 20);
        image.setBounds(0, 0, 200, 250);
        discount.setBounds(500, 0, 100, 50);
        quantityField.setBounds(260, 180, 35, 20);
        buy.setBounds(300, 210, 60, 20);

        buy.addActionListener(new ButtonBuyListener());

        film.add(title);
        film.add(date);
        film.add(hour);
        film.add(freeSeats);
        film.add(quantity);
        film.add(image);
        film.add(discount);
        film.add(quantityField);
        film.add(buy);

        film.setLayout(null);
    }

    private class ButtonBuyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            PaymentProgressBar p = new PaymentProgressBar();
        }

    }

    public void BuildMenuScreen() {
        menuScreen = new JPanel();
        film1 = new JPanel();
        film1.setBackground(Color.yellow);
        film2 = new JPanel();
        film2.setBackground(Color.blue);
        film3 = new JPanel();
        film3.setBackground(Color.white);

        JButton buttonEmployee = new JButton("Employee login");
        JButton buttonCustomer = new JButton("Customer login");

        film1.setBounds(10, 100, 600, 250);
        film2.setBounds(10, 350, 600, 250);
        film3.setBounds(10, 600, 600, 250);
        buttonEmployee.setBounds(1400, 10, 170, 35);
        buttonCustomer.setBounds(1200, 10, 170, 35);

        buttonEmployee.addActionListener(new ButtonEmployeeListener());
        buttonCustomer.addActionListener(new ButtonCustomerListener());

        BuildFilmPanel(film1);
        BuildFilmPanel(film2);
        BuildFilmPanel(film3);

        menuScreen.add(buttonEmployee);
        menuScreen.add(buttonCustomer);
        menuScreen.add(film1);
        menuScreen.add(film2);
        menuScreen.add(film3);

        menuScreen.setLayout(null);
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
        buttonValidate.addActionListener(new ButtonCustomerListener());
        connexionScreen.setLayout(null);
    }

    public void BuildLoginScreen() {
        loginScreen = new JPanel();
        JButton buttonValidate = new JButton("Validate");
        buttonValidate.setBounds(350, 400, 220, 35);
        buttonValidate.addActionListener(new ButtonValidateLoginListener());
        loginScreen.add(buttonValidate);
        JLabel message1 = new JLabel("Login : ");
        message1.setBounds(190, 100, 100, 50);
        loginScreen.add(message1);
        textFieldLogin = new JTextField(15);
        textFieldLogin.setBounds(250, 111, 150, 25);
        textFieldLogin.addKeyListener(new LoginKeyListener());
        loginScreen.add(textFieldLogin);
        JLabel message2 = new JLabel("Password : ");
        message2.setBounds(170, 200, 100, 50);
        loginScreen.add(message2);
        textFieldPassword = new JPasswordField(15);
        textFieldPassword.setBounds(250, 211, 150, 25);
        textFieldPassword.addKeyListener(new LoginKeyListener());
        loginScreen.add(textFieldPassword);
        JButton accueil = new JButton("Accueil");
        accueil.setBounds(10, 10, 150, 40);
        loginScreen.add(accueil);
        loginScreen.setLayout(null);
    }

    private class ButtonValidateLoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (choiceUser == 1) {
                try {
                    boolLogin = MemberCustomer.callCheckMember(login, password);
                } catch (SQLException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (boolLogin == false) {
                    //on affiche une nouvelle fenetre avec marqué : veuillez reesayer
                } else {
                    // on lance la page principale
                }
            } else if (choiceUser == 2) {
                try {
                    boolLogin = Employee.callCheckEmployee(login, password);
                } catch (SQLException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (boolLogin == false) {
                    //on affiche une nouvelle fenetre avec marqué : veuillez reesayer
                } else {
                    // on lance la page principale
                }
            }
            //System.out.println(boolLogin);
        }

    }

    private class LoginKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent ke) {
        }

        @Override
        public void keyPressed(KeyEvent ke) {
        }

        @Override
        public void keyReleased(KeyEvent ke) {

            if (textFieldLogin.getText() != null) {
                login = textFieldLogin.getText();
            }
            if (textFieldPassword.getText() != null) {
                password = textFieldPassword.getText();
            }
        }

    }

    private class RadioButtonYesListener implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            choiceUser = 1;
        }

    }

    private class RadioButtonNoListener implements ActionListener {

        public void actionPerformed(ActionEvent ae) {

            choiceUser = 0;
        }

    }

    private class ButtonCustomerListener implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            choiceUser = 1;
            setContentPane(loginScreen);
            invalidate();
            validate();

        }
    }

    private class ButtonEmployeeListener implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            choiceUser = 2;
            setContentPane(loginScreen);
            invalidate();
            validate();
        }
    }

    public static void main(String[] args) {
        GUI f = new GUI();
        f.setVisible(true);
    }
}
