/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.*;
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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

/**
 *
 * @author charl
 */
public class GUI extends JFrame {

    private JPanel connexionScreen;
    private JPanel loginScreen;
    private JPanel menuScreen;
    private JPanel reservationScreen;
    private JPanel employeeScreen;
    private JPanel statisticScreen;
    private JPanel customerRecordsScreen;
    private JPanel[] films;
    private JPanel film1;
    private JPanel film2;
    private JPanel film3;
    private int choiceUser;   // 0 = guest / 1 = member / 2 = employee
    private JTextField textFieldLogin;
    private JPasswordField textFieldPassword;
    private boolean boolLogin;
    private String login;
    private String password;
    private Cinema cine;

    public GUI(Cinema cine) throws HeadlessException {
        this.cine = cine;
        setTitle("Cinema");
        setBounds(150, 80, 1600, 920);

        films = new JPanel[3];
        BuildConnexionScreen();
        BuildLoginScreen();

        BuildMenuScreen();
        //setContentPane(connexionScreen);
        setContentPane(menuScreen);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //setVisible(true);
    }

    public void BuildFilmPanel(JPanel film, Projection proj) {
        JLabel title = new JLabel(proj.getMovieTitle());
        JLabel date = new JLabel(proj.getProjectionDate());
        JLabel hour = new JLabel(proj.getProjectionHour().toString());
        int i = proj.getNbFreeSeats();
        JLabel freeSeats = new JLabel("" + i + "");
        JLabel image = new JLabel(new ImageIcon("Scarface.jpg"));
        JPanel discount;

        title.setBounds(300, 20, 200, 20);
        date.setBounds(250, 60, 150, 20);
        hour.setBounds(250, 100, 150, 20);
        freeSeats.setBounds(250, 140, 150, 20);
        image.setBounds(0, 0, 200, 250);

        // customer
        if (choiceUser != 2) {

            JLabel quantity = new JLabel("Quantity");
            JTextField quantityField = new JTextField(2);
            JButton buy = new JButton("Buy");

            buy.addActionListener(new ButtonBuyListener(proj, quantityField));

            quantity.setBounds(300, 180, 150, 20);
            quantityField.setBounds(260, 180, 35, 20);
            buy.setBounds(300, 210, 60, 20);

            film.add(quantity);
            film.add(quantityField);
            film.add(buy);
            // employee
        } else if (choiceUser == 2) {
            JTextField discountField = new JTextField(2);
            JLabel updateDiscount = new JLabel("Update discount offer");
            JButton reprogramm = new JButton("Reprogramm");
            JButton delete = new JButton("delete");
            JButton applyDiscount = new JButton("Apply discount");

            discountField.setBounds(260, 180, 35, 20);
            updateDiscount.setBounds(300, 180, 150, 20);
            reprogramm.setBounds(450, 180, 150, 25);
            delete.setBounds(450, 130, 150, 25);
            applyDiscount.setBounds(230, 220, 150, 25);

            applyDiscount.addActionListener(new ButtonApplyDiscountListener(proj, discountField));
            delete.addActionListener(new ButtonDeleteListener(proj));

            film.add(discountField);
            film.add(updateDiscount);
            film.add(reprogramm);
            film.add(delete);
            film.add(applyDiscount);
        }

        film.add(title);
        film.add(date);
        film.add(hour);
        film.add(freeSeats);
        film.add(image);
        if (proj.getDiscount() != 0) {
            discount = new Circle('r', 'y', "-" + proj.getDiscount() + " €"); //mettre la bonne couleur de font en fonction de la couleur du film
            discount.setBounds(500, 0, 100, 50);
            film.add(discount);
        }

        film.setLayout(null);
    }

    /*
     Clic on delete projection
     */
    private class ButtonDeleteListener implements ActionListener {

        private Projection Proj;
        private JTextField monTexte;

        public ButtonDeleteListener(Projection Proj) {
            this.Proj = Proj;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                Proj.setAvailibility(false);
                cine.refreshProjList();
                choiceUser = 2;
                employeeScreen.revalidate();
                
                BuildMenuScreen();
                
                invalidate();
                validate();
            } catch (SQLException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /*
     Clic on apply discount bn
     */
    private class ButtonApplyDiscountListener implements ActionListener {

        private Projection proj;
        private JTextField monTexte;

        public ButtonApplyDiscountListener(Projection proj, JTextField monTexte) {
            this.proj = proj;
            this.monTexte = monTexte;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            String s = monTexte.getText();

            if (!s.equalsIgnoreCase("")) {
                // is it numeric ?
                try {

                    int i = Integer.parseInt(s);
                    if (i >= 0 && i < 10) {

                        proj.setDiscount(i);
                        cine.refreshProjList();
                        monTexte.setText("");

                    } else {
                        JOptionPane.showMessageDialog(null, "Maximum discount is 10 ( ͡° ͜ʖ ͡°)");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "a numeric value please (ง ͡ʘ ͜ʖ ͡ʘ)ง");
                } catch (SQLException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Please type something good ( ͡° ͜ʖ ͡°)");

            }
        }

    }

    /*
     clic on buy listener as a guest
     */
    private class ButtonBuyListener implements ActionListener {

        private Projection proj;
        private JTextField monTexte;

        public ButtonBuyListener(Projection proj, JTextField monTexte) {
            this.proj = proj;
            this.monTexte = monTexte;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            String s = monTexte.getText();

            if (!s.equalsIgnoreCase("")) {
                // is it numeric ?
                try {

                    int i = Integer.parseInt(s);
                    if (i > 0 && i < proj.getNbFreeSeats()) {

                        proj.addReservation(new GuestCustomer(), i);
                        monTexte.setText("");
                        PaymentProgressBar p = new PaymentProgressBar();

                    } else {
                        JOptionPane.showMessageDialog(null, "Max number of seats is : " + proj.getNbFreeSeats() + " ( ͡° ͜ʖ ͡°)");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "a numeric value please (ง ͡ʘ ͜ʖ ͡ʘ)ง");
                } catch (SQLException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Please type something good ( ͡° ͜ʖ ͡°)");

            }

        }

        /*private class ButtonDiscountListener implements ActionListener {

         private int idProj;
         private JTextField monTexte;

         public ButtonDiscountListener(int idProj, JTextField monTexte) {
         this.idProj = idProj;
         this.monTexte = monTexte;
         }

         @Override
         public void actionPerformed(ActionEvent ae) {
         String s = monTexte.getText();

         if (!s.equalsIgnoreCase("")) {
         // is it numeric ?
         try {

         int i = Integer.parseInt(s);
         if (i > 0 && i < cine.getProjList().get(idProj).getNbFreeSeats()) {
                        
         cine.getProjList().get(idProj).;
                        
                        
                        
         } else {
         JOptionPane.showMessageDialog(null, "Max number of seats is : " + cine.getProjList().get(idProj).getNbFreeSeats() + " ( ͡° ͜ʖ ͡°)");
         }
         } catch (NumberFormatException e) {
         JOptionPane.showMessageDialog(null, "a numeric value please (ง ͡ʘ ͜ʖ ͡ʘ)ง");
         } catch (SQLException ex) {
         Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
         }

         } else {
         JOptionPane.showMessageDialog(null, "Please type something good ( ͡° ͜ʖ ͡°)");

         }

         }*/
    }

    public void BuildMenuScreen() {
        menuScreen = new JPanel();

        /*
         film1 = new JPanel();
         film1.setBackground(Color.yellow);
         film2 = new JPanel();
         film2.setBackground(Color.blue);
         film3 = new JPanel();
         film3.setBackground(Color.white);

         film1.setBounds(10, 100, 600, 250);
         film2.setBounds(10, 350, 600, 250);
         film3.setBounds(10, 600, 600, 250);
         */
        //cine.getProjList().get(2).afficherProjection();
        for (int i = 0; i < 3; ++i) {
            // build the panel
            if (cine.getProjList().size() > i) {
                //  add a panel
                films[i] = new JPanel();
                switch (i) {
                    case 0:
                        films[i].setBackground(Color.LIGHT_GRAY);
                        break;
                    case 1:
                        films[i].setBackground(Color.ORANGE);
                        break;
                    case 2:
                        films[i].setBackground(Color.green);
                        break;

                }
                // define bounds
                films[i].setBounds(10, 100 + 250 * i, 600, 250);
                BuildFilmPanel(films[i], cine.getProjList().get(i));
                // add it to the screencine.getProjList().get(i)
                menuScreen.add(films[i]);
            }

        }

        if (boolLogin == false) {
            JButton buttonEmployee = new JButton("Employee login");
            JButton buttonCustomer = new JButton("Customer login");

            buttonEmployee.setBounds(1400, 10, 170, 35);
            buttonCustomer.setBounds(1200, 10, 170, 35);

            buttonEmployee.addActionListener(new ButtonEmployeeListener());
            buttonCustomer.addActionListener(new ButtonCustomerListener());

            menuScreen.add(buttonEmployee);
            menuScreen.add(buttonCustomer);
        }
        //customer login menu
        if (boolLogin == true) {
            JLabel loginOfCustomer = new JLabel(cine.getCustLogin());
            JButton buttonReservations = new JButton("See all your reservations");
            JButton buttonLogOut = new JButton("Log out");

            loginOfCustomer.setBounds(300, 10, 230, 35);
            loginOfCustomer.setForeground(new Color(0, 0, 0));
            loginOfCustomer.setBackground(Color.GRAY);
            loginOfCustomer.setOpaque(true);
            buttonReservations.setBounds(1250, 10, 200, 35);
            buttonLogOut.setBounds(1470, 10, 100, 35);

            buttonLogOut.addActionListener(new ButtonLogOutListener());
            buttonReservations.addActionListener(new ButtonReservationListener());

            menuScreen.add(loginOfCustomer);
            menuScreen.add(buttonReservations);
            menuScreen.add(buttonLogOut);
        }

        /*
         menuScreen.add(film1);
         menuScreen.add(film2);
         menuScreen.add(film3);
         */
        menuScreen.setLayout(null);
    }

    //employee login menu
    public void BuildEmployeeScreen() {
        employeeScreen = new JPanel();
        /*
         film1 = new JPanel();
         film1.setBackground(Color.yellow);
         film2 = new JPanel();
         film2.setBackground(Color.blue);
         film3 = new JPanel();
         film3.setBackground(Color.white);
         */

        JButton buttonLogOut = new JButton("Log out");
        JLabel loginOfEmployee = new JLabel(cine.getEmpLogin());
        JButton buttonAddMovie = new JButton("Add movie");
        JButton buttonStatistic = new JButton("View statistics");
        JButton buttonCustomerRecords = new JButton("Customer records");

        buttonLogOut.setBounds(1470, 10, 100, 35);
        loginOfEmployee.setBounds(300, 10, 230, 35);
        loginOfEmployee.setForeground(new Color(0, 0, 0));
        loginOfEmployee.setBackground(Color.white);
        loginOfEmployee.setOpaque(true);
        buttonAddMovie.setBounds(1100, 400, 100, 35);
        buttonStatistic.setBounds(1250, 10, 200, 35);
        buttonCustomerRecords.setBounds(1000, 10, 200, 35);
        /*
         film1.setBounds(10, 100, 600, 250);
         film2.setBounds(10, 350, 600, 250);
         film3.setBounds(10, 600, 600, 250);
         */
        buttonLogOut.addActionListener(new ButtonLogOutListener());
        buttonStatistic.addActionListener(new ButtonStatisticListener());
        buttonCustomerRecords.addActionListener(new ButtonCustomerRecordsListener());

        employeeScreen.add(buttonLogOut);
        employeeScreen.add(loginOfEmployee);
        employeeScreen.add(buttonAddMovie);
        employeeScreen.add(buttonStatistic);
        employeeScreen.add(buttonCustomerRecords);
        for (int i = 0; i < 3; ++i) {
            // build the panel
            if (cine.getProjList().size() > i) {
                //  add a panel
                films[i] = new JPanel();
                switch (i) {
                    case 0:
                        films[i].setBackground(Color.LIGHT_GRAY);
                        break;
                    case 1:
                        films[i].setBackground(Color.ORANGE);
                        break;
                    case 2:
                        films[i].setBackground(Color.green);
                        break;

                }
                // define bounds
                films[i].setBounds(10, 100 + 250 * i, 600, 250);
                BuildFilmPanel(films[i], cine.getProjList().get(i));
                // add it to the screen
                employeeScreen.add(films[i]);
            }

        }
        /*
         employeeScreen.add(film1);
         employeeScreen.add(film2);
         employeeScreen.add(film3);
         */

        employeeScreen.setLayout(null);
    }

    private class ButtonCustomerRecordsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            BuildCustomerRecordsScreen();
            setContentPane(customerRecordsScreen);
            invalidate();
            validate();
        }

    }

    public void BuildCustomerRecordsScreen() {
        customerRecordsScreen = new JPanel();

        JButton buttonBack = new JButton("Back");
        JLabel loginOfEmployee = new JLabel("          Login");

        buttonBack.setBounds(1470, 10, 100, 35);
        loginOfEmployee.setBounds(300, 10, 170, 35);
        loginOfEmployee.setForeground(new Color(0, 0, 0));
        loginOfEmployee.setBackground(Color.GRAY);
        loginOfEmployee.setOpaque(true);

        buttonBack.addActionListener(new buttonBackListener());
        /*
         String[] colNames = {"Name", "Telephone"};
         String[][] rowData ={{"Jean","555-2222"},
         {"Tim","555-2222"}};
        
        
         setBounds(150,80,1600,920);
         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

         // Create a JTable with the results.
         JTable table = new JTable(rowData, colNames);

         // Put the table in a scroll pane.
         JScrollPane scrollPane = new JScrollPane(table);

         // Add the table to the content pane.
         //add(scrollPane, );

         // Set the size and display.
      
         */
        customerRecordsScreen.add(buttonBack);

        customerRecordsScreen.add(loginOfEmployee);
        //customerRecordsScreen.add(scrollPane,BorderLayout.CENTER);
        //pack();
        //setSize(WIDTH, HEIGHT);
        //setVisible(true);
        customerRecordsScreen.setLayout(null);
        MemberCustomer.callAfficherJTable();
        invalidate();
        revalidate();

    }

    private class ButtonStatisticListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            BuildStatisticScreen();
            setContentPane(statisticScreen);
            invalidate();
            validate();
        }

    }

    public void BuildStatisticScreen() {
        statisticScreen = new JPanel();

        JLabel loginOfEmployee = new JLabel("          Login");
        JButton buttonBack = new JButton("Back");

        loginOfEmployee.setBounds(300, 10, 170, 35);
        loginOfEmployee.setForeground(new Color(0, 0, 0));
        loginOfEmployee.setBackground(Color.GRAY);
        loginOfEmployee.setOpaque(true);
        buttonBack.setBounds(1470, 10, 100, 35);

        buttonBack.addActionListener(new buttonBackListener());

        statisticScreen.add(loginOfEmployee);
        statisticScreen.add(buttonBack);

        statisticScreen.setLayout(null);
    }

    public void BuildReservationScreen() {
        reservationScreen = new JPanel();

        JLabel loginOfCustomer = new JLabel("          Login");
        JButton buttonBack = new JButton("Back");

        loginOfCustomer.setBounds(300, 10, 170, 35);
        loginOfCustomer.setForeground(new Color(0, 0, 0));
        loginOfCustomer.setBackground(Color.GRAY);
        loginOfCustomer.setOpaque(true);
        buttonBack.setBounds(1470, 10, 100, 35);

        buttonBack.addActionListener(new buttonBackListener());

        reservationScreen.add(loginOfCustomer);
        reservationScreen.add(buttonBack);

        reservationScreen.setLayout(null);
    }

    private class buttonBackListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (choiceUser != 2) {
                setContentPane(menuScreen);
            } else if (choiceUser == 2) {
                setContentPane(employeeScreen);
            }

            invalidate();
            validate();
        }

    }

    private class ButtonReservationListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            BuildReservationScreen();
            setContentPane(reservationScreen);
            invalidate();
            validate();
        }

    }

    private class ButtonLogOutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            boolLogin = false;
            choiceUser = 0;
            BuildMenuScreen();
            setContentPane(menuScreen);
            invalidate();
            validate();
        }

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

    // build login screen
    public void BuildLoginScreen() {
        loginScreen = new JPanel();
        JButton buttonValidate = new JButton("Validate");
        buttonValidate.setBounds(350, 400, 220, 35);
        buttonValidate.addActionListener(new ButtonValidateLoginListener());
        loginScreen.add(buttonValidate);
        JLabel message1 = new JLabel("Login : ");
        message1.setBounds(190, 100, 100, 50);
        loginScreen.add(message1);
        textFieldLogin = new JTextField(30);
        textFieldLogin.setBounds(250, 111, 230, 25);
        textFieldLogin.addKeyListener(new LoginKeyListener());
        loginScreen.add(textFieldLogin);
        JLabel message2 = new JLabel("Password : ");
        message2.setBounds(170, 200, 100, 50);
        loginScreen.add(message2);
        textFieldPassword = new JPasswordField(15);
        textFieldPassword.setBounds(250, 211, 230, 25);
        textFieldPassword.addKeyListener(new LoginKeyListener());
        loginScreen.add(textFieldPassword);
        JButton accueil = new JButton("Accueil");
        accueil.setBounds(10, 10, 150, 40);
        accueil.addActionListener(new ButtonAccueilListener());
        loginScreen.add(accueil);
        loginScreen.setLayout(null);
    }

    private class ButtonAccueilListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            setContentPane(menuScreen);
        }

    }

    private class ButtonValidateLoginListener implements ActionListener {

        @Override

        public void actionPerformed(ActionEvent ae) {
            cine.setCustLogin(null);
            cine.setEmpLogin(null);
            //member login
            if (choiceUser == 1) {
                try {
                    boolLogin = MemberCustomer.callCheckMember(login, password);
                } catch (SQLException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (boolLogin == false) {
                    // wrong id or password
                    JOptionPane.showMessageDialog(null, "Wrong id or password (ง ͡ʘ ͜ʖ ͡ʘ)ง");
                } else {
                    cine.setCustLogin(login);
                    BuildMenuScreen();
                    setContentPane(menuScreen);

                    invalidate();

                    validate();

                }
                // employee login
            } else if (choiceUser == 2) {
                try {
                    boolLogin = Employee.callCheckEmployee(login, password);
                } catch (SQLException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (boolLogin == false) {
                    //on affiche une nouvelle fenetre avec marqué : veuillez reesayer
                } else {
                    cine.setEmpLogin(login);
                    BuildEmployeeScreen();
                    setContentPane(employeeScreen);

                    invalidate();

                    validate();

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

}

