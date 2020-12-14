/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.*;
import Model.ReservationDB;
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
import java.awt.FlowLayout;
import java.awt.Image;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;

/**
 *class qui gère la majeure partie de l'interprétation graphique du projet
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

    /*
        Builds GUI panel to display movie info, projection info and action buttons
    */
    public void BuildFilmPanel(JPanel film, Projection proj, double extraDiscount,Color clrBack) {
        boolean bCanBuy = true;
        JLabel title = new JLabel(proj.getMovieTitle());
        JLabel date = new JLabel("Projection  : "+proj.getProjectionDate());
        JLabel hour = new JLabel("at : "+proj.getProjectionHour().toString());
        
        // are there seats available ?
        int i = proj.getNbFreeSeats();
        int j = proj.getAllReservationForProj();
        JLabel freeSeats = new JLabel("Seats available " + (i-j) + " / " + i);
        if( i<=j ){
            freeSeats.setText("SOLD OUT !");
            bCanBuy = false;
        }
        JLabel price = new JLabel("Price " + (10*(1-extraDiscount)-proj.getDiscount()) + " €");
        
        // Get more information on movie
        Movie leFilm = cine.getMovieInfos(proj.getMovieTitle());
        // Get movie picture
        ImageIcon imgI = new ImageIcon("C:\\Users\\Tonio\\Desktop\\Projet_Cinéma\\images\\"+leFilm.getFichier());
        Image img = imgI.getImage();
        Image newImg = img.getScaledInstance(180,230,java.awt.Image.SCALE_SMOOTH);
        imgI = new ImageIcon(newImg);
        JLabel image = new JLabel(imgI);
        JLabel movieInfo = new JLabel(leFilm.getType()+" released in "+leFilm.getReleaseDate()+" duration "+leFilm.getRunningTime());
        
        JPanel discount;

        title.setBounds(300, 20, 200, 20);
        /* todo - modifier la taille de la fonte pour le titre - en fait déjà en gras
        Font f;
        f = title.getFont();
        title.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));
        */
        movieInfo.setBounds(250, 60, 300, 20);
        date.setBounds(250, 100, 150, 20);
        hour.setBounds(450, 100, 150, 20);
        freeSeats.setBounds(250, 130, 150, 20);
        price.setBounds(250, 155, 150, 20);
        image.setBounds(0, 0, 200, 250);

        // customer ou guest
        if (choiceUser != 2 && bCanBuy ) {

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
            JButton reschedule = new JButton("Reschedule");
            JButton delete = new JButton("Delete");
            JButton applyDiscount = new JButton("Apply discount");

            discountField.setBounds(260, 180, 35, 20);
            updateDiscount.setBounds(300, 180, 150, 20);
            reschedule.setBounds(450, 180, 140, 25);
            delete.setBounds(450, 130, 140, 25);
            applyDiscount.setBounds(230, 220, 150, 25);

            applyDiscount.addActionListener(new ButtonApplyDiscountListener(proj, discountField));
            delete.addActionListener(new ButtonDeleteListener(proj));
            reschedule.addActionListener(new ButtonRescheduleListener( proj));

            film.add(discountField);
            film.add(updateDiscount);
            film.add(reschedule);
            film.add(delete);
            film.add(applyDiscount);
        }

        film.add(title);
        film.add(movieInfo);
        film.add(date);
        film.add(hour);
        film.add(freeSeats);
        film.add(price);
        film.add(image);
        
        // discount on movie
        if (proj.getDiscount() != 0) {
            discount = new Circle('r', 'y', "-" + proj.getDiscount() + " €"); // todo : mettre la bonne couleur de fond en fonction de la couleur du film
            discount.setBackground(clrBack);
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
                if( JOptionPane.showConfirmDialog(employeeScreen, "Do you really want to delete this projection ?","Please confirm",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION ){
                    Proj.setAvailibility(false);
                    cine.refreshProjList();
                    choiceUser = 2;
                    employeeScreen.revalidate();

                    BuildMenuScreen();

                    invalidate();
                    validate();
                }
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
     clic on reschedule as employee
    */
    private class ButtonRescheduleListener implements ActionListener {
        private Projection proj;

        public ButtonRescheduleListener(Projection proj) {
            this.proj = proj;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            JButton bn = (JButton)ae.getSource();
            int n=0;
            
            // Displays a dialog to schedule a new projection
            JDialog addProj = new JDialog((JFrame)SwingUtilities.getWindowAncestor(bn),"Schedule projection",true);
            String tMovies[] = new String[cine.getMovieList().size()];
            for( int i=0 ; i<cine.getMovieList().size() ; ++i ){
                tMovies[i] = cine.getMovieList().get(i).getTitle();
                if( tMovies[i].equals(proj.getMovieTitle()) ){
                    n = i;
                }
            }
            JComboBox cb = new JComboBox(tMovies);
            cb.setSelectedIndex(n);
            JTextField txtDate = new JTextField(proj.getProjectionDate());
            JTextField txtHour = new JTextField(proj.getProjectionHour().toString());
            JButton bnOk = new JButton("Ok");
            JButton bnCancel = new JButton("Cancel");
            addProj.setLayout(new FlowLayout());
            addProj.add(new JLabel("Movie"));
            addProj.add(cb);
            addProj.add(new JLabel("Date"));
            addProj.add(txtDate);
            addProj.add(new JLabel("Hour"));
            addProj.add(txtHour);
            addProj.add(bnOk);
            addProj.add(bnCancel);
            addProj.setSize(300,300);
            addProj.pack();

            bnCancel.addActionListener(new dlgRescheduleBnCancel(addProj));
            bnOk.addActionListener(new dlgRescheduleBnOk(addProj, proj,txtDate,txtHour,cb));

            addProj.setVisible(true);
            
        }
        
    }
    
    /*
        Clic Ok on new projection
    */
    private class dlgRescheduleBnOk implements ActionListener {
        private JDialog dlg;
        private JTextField tDate;
        private JTextField tHour;
        private JComboBox ccMovie;
        private Projection proj;
        
        public dlgRescheduleBnOk(JDialog dlg, Projection proj, JTextField tDate, JTextField tHour, JComboBox ccMovie) {
            this.dlg = dlg;
            this.proj = proj;
            this.tHour = tHour;
            this.tDate = tDate;
            this.ccMovie = ccMovie;
        }

                @Override
        public void actionPerformed(ActionEvent ae) {
            boolean bOk = false;
            String sMovieTitle;
            String sDate;
            String sHour;

            // is movie selected ?
            sMovieTitle = ccMovie.getSelectedItem().toString();
            if ( sMovieTitle.equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(dlg), "Please select a movie ( ͡° ͜ʖ ͡°)","Error",JOptionPane.WARNING_MESSAGE);
                ccMovie.requestFocus();
                return;
            }
            
            // is date set and valid ?
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            try {
                sDate = tDate.getText();
                sdf.parse(sDate);
            }catch(ParseException e){
                JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(dlg), "Date is not valid ( ͡° ͜ʖ ͡°)","Error",JOptionPane.WARNING_MESSAGE);
                tDate.requestFocus();
                return;
            }
            
            // is hour set and valid ?
            sdf = new SimpleDateFormat("HH:mm");
            sdf.setLenient(false);
            try {
                sHour = tHour.getText();
                sdf.parse(sHour);
            }catch(ParseException e){
                JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(dlg), "Hour is not valid ( ͡° ͜ʖ ͡°)","Error",JOptionPane.WARNING_MESSAGE);
                tHour.requestFocus();
                return;
            }
            
            // current projection is set unavailable
            try{
                proj.setAvailibility(false);
            } catch (SQLException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            // add new projection
            Projection.addProjection(sMovieTitle, sDate, sHour);
            cine.refreshProjList();
            // close dialog
            dlg.dispose();
        }            
    }
    
    
    /*
        Clic Cancel on new projection
    */
    private class dlgRescheduleBnCancel implements ActionListener {
        private JDialog dlg;
        
        public dlgRescheduleBnCancel(JDialog dlg) {
            this.dlg = dlg;
        }
        @Override
        public void actionPerformed(ActionEvent ae) {
            dlg.dispose();
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
                        MemberCustomer client = cine.getMemberCustomer(cine.getCustLogin());
                        
                        proj.addReservation(client, i);
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
        double extraDiscount = 0;

        // display login buttons if nobody is connected
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
        // customer logged
        if (boolLogin == true) {
            MemberCustomer client = cine.getMemberCustomer(cine.getCustLogin());
            
            JLabel loginOfCustomer = new JLabel("Welcome " + client.getName()+" !");
            loginOfCustomer.setBounds(300, 10, 250, 35);
            loginOfCustomer.setForeground(new Color(0, 0, 0));
            loginOfCustomer.setBackground(Color.LIGHT_GRAY);
            loginOfCustomer.setOpaque(true);

            JLabel custBundleDiscount = new JLabel("Your bundle is '" + client.getBundle()+"' (discount "+client.getDiscount()*100+"%)");
            custBundleDiscount.setBounds(300, 45, 250, 35);
            custBundleDiscount.setForeground(new Color(0, 0, 0));
            custBundleDiscount.setBackground(Color.LIGHT_GRAY);
            custBundleDiscount.setOpaque(true);
            extraDiscount = client.getDiscount();

            JButton buttonLogOut = new JButton("Log out");
            buttonLogOut.setBounds(1470, 10, 100, 35);
            buttonLogOut.addActionListener(new ButtonLogOutListener());

            menuScreen.add(loginOfCustomer);
            menuScreen.add(custBundleDiscount);
            menuScreen.add(buttonLogOut);
            
            // adds a table of all customer reservations so far
            JPanel tabPanel = new JPanel();
            JLabel l1=new JLabel("Purchases history");
            l1.setBounds( 700,100,150,20);
            menuScreen.add(l1);
            tabPanel.setBackground(Color.LIGHT_GRAY);
            tabPanel.setBounds(700, 120, 600, 600);
            // get the data 
            String[] colsNames = new String[]{"Movie","projection date","projection hour","Tickets","Price","purchase time"};
            String[][] tabData = Projection.getAllReservationForCustomer(cine.getCustLogin());
            
            JTable tab = new JTable(tabData,colsNames);
            
            tabPanel.add(tab.getTableHeader());
            tabPanel.add(tab);
            tab.setSize(tabPanel.getWidth(),tabPanel.getHeight());
            tab.getColumnModel().getColumn(0).setPreferredWidth(100);
            tab.getColumnModel().getColumn(1).setPreferredWidth(100);
            tab.getColumnModel().getColumn(2).setPreferredWidth(100);
            tab.getColumnModel().getColumn(3).setPreferredWidth(50);
            tab.getColumnModel().getColumn(4).setPreferredWidth(50);
            tab.getColumnModel().getColumn(5).setPreferredWidth(150);
            JScrollPane scrollPane = new JScrollPane(tab, 
                    ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setBounds(700,120,600,600);
            scrollPane.setSize(tab.getWidth(), tab.getHeight());
            menuScreen.add(scrollPane);
        }

        // display the 3 first available projections
        for (int i = 0; i < 3; ++i) {
            // build the panel
            if (cine.getProjList().size() > i) {
                //  add a panel
                films[i] = new JPanel();
                switch (i) {
                    case 0:
                        films[i].setBackground(Color.ORANGE);
                        break;
                    case 1:
                        films[i].setBackground(Color.LIGHT_GRAY);
                        break;
                    case 2:
                        films[i].setBackground(Color.ORANGE);
                        break;

                }
                // define bounds
                films[i].setBounds(10, 100 + 250 * i, 600, 250);
                BuildFilmPanel(films[i], cine.getProjList().get(i), extraDiscount,films[i].getBackground());
                // add it to the screencine.getProjList().get(i)
                menuScreen.add(films[i]);
            }

        }
        menuScreen.setLayout(null);

        // display background image
        /*ImageIcon imgI = new ImageIcon("C:\\Users\\Tonio\\Desktop\\Projet_Cinéma\\images\\fond.jpg");
        Image img = imgI.getImage();
        //Image newImg = img.getScaledInstance(getWidth(),getHeight() ,java.awt.Image.SCALE_SMOOTH);
        Image newImg = img.getScaledInstance(600,600 ,java.awt.Image.SCALE_SMOOTH);
        imgI = new ImageIcon(newImg);
        JLabel image = new JLabel(imgI);
        menuScreen.add(image);tentative de mettre une image de fond */ 

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
        JLabel loginOfEmployee = new JLabel("Welcome "+cine.getEmpLogin()+" on employee screen !");
        //JButton buttonAddMovie = new JButton("Add movie");
        JButton buttonStatistic = new JButton("View statistics");
        JButton buttonCustomerRecords = new JButton("Customer records");

        buttonLogOut.setBounds(1470, 10, 100, 35);

        loginOfEmployee.setBounds(300, 10, 230, 35);
        loginOfEmployee.setForeground(new Color(0, 0, 0));
        loginOfEmployee.setBackground(Color.white);
        loginOfEmployee.setOpaque(true);
        
        JLabel infoEmployee = new JLabel("Actions will be saved when logout");
        infoEmployee.setBounds(300, 50, 230, 35);
        infoEmployee.setForeground(new Color(0, 0, 0));
        infoEmployee.setBackground(Color.white);
        infoEmployee.setOpaque(true);
        
        //buttonAddMovie.setBounds(1100, 400, 100, 35);
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
        employeeScreen.add(infoEmployee);
        //employeeScreen.add(buttonAddMovie);
        employeeScreen.add(buttonStatistic);
        employeeScreen.add(buttonCustomerRecords);
        for (int i = 0; i < 3; ++i) {
            // build the panel
            if (cine.getProjList().size() > i) {
                //  add a panel
                films[i] = new JPanel();
                switch (i) {
                    case 0:
                        films[i].setBackground(Color.ORANGE);
                        break;
                    case 1:
                        films[i].setBackground(Color.LIGHT_GRAY);
                        break;
                    case 2:
                        films[i].setBackground(Color.ORANGE);
                        break;

                }
                // define bounds
                films[i].setBounds(10, 100 + 250 * i, 600, 250);
                BuildFilmPanel(films[i], cine.getProjList().get(i),0,films[i].getBackground());
                // add it to the screen
                employeeScreen.add(films[i]);
            }

        }
        // adds a pie chart for sales per user
        JPanel tabPanel = new JPanel();
        tabPanel.setBackground(Color.LIGHT_GRAY);
        tabPanel.setBounds(700, 100, 800, 600);

        // get the data 
        JFreeChart chart = ChartFactory.createPieChart("Sales per user", ReservationDB.getAllReservationPerUsers(),true,true,false);

        ChartPanel piePanel = new ChartPanel(chart);
        
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSimpleLabels(true);
        
        PieSectionLabelGenerator pslg = new StandardPieSectionLabelGenerator(
            "{0}: {1} ({2})", new DecimalFormat("0,000.00 $"), new DecimalFormat("0%"));
        plot.setLabelGenerator(pslg);

        piePanel.setSize(tabPanel.getSize());
        piePanel.setPreferredSize(tabPanel.getSize());
        
        tabPanel.add(piePanel);
        employeeScreen.add(tabPanel);

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
        /*
        customerRecordsScreen = new JPanel();

        JButton buttonBack = new JButton("Back");
        JLabel loginOfEmployee = new JLabel("          Login");

        buttonBack.setBounds(1470, 10, 100, 35);
        loginOfEmployee.setBounds(300, 10, 170, 35);
        loginOfEmployee.setForeground(new Color(0, 0, 0));
        loginOfEmployee.setBackground(Color.GRAY);
        loginOfEmployee.setOpaque(true);

        buttonBack.addActionListener(new buttonBackListener());
        */
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
        /*
        customerRecordsScreen.add(buttonBack);

        customerRecordsScreen.add(loginOfEmployee);
        //customerRecordsScreen.add(scrollPane,BorderLayout.CENTER);
        //pack();
        //setSize(WIDTH, HEIGHT);
        //setVisible(true);
        customerRecordsScreen.setLayout(null);
                */
        MemberCustomer.callAfficherJTable();
        invalidate();
        revalidate();

    }

    private class ButtonStatisticListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            StatsFrame f = new StatsFrame(cine);
            
            f.setVisible(true);
            /*
            BuildStatisticScreen();
            setContentPane(statisticScreen);
            invalidate();
            validate();
                    */
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
        JButton accueil = new JButton("Back");
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
                    // wrong id or password
                    JOptionPane.showMessageDialog(null, "Wrong id or password (ง ͡ʘ ͜ʖ ͡ʘ)ง");
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
            if (textFieldPassword.getPassword() != null) {
                password = String.valueOf(textFieldPassword.getPassword());
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

