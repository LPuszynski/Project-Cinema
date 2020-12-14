/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Customer;
import Controller.MemberCustomer;

import static Model.JBDC.getDbConnection;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Controller.MemberCustomer;
import static Model.JBDC.getDbConnection;
import View.TableFormatter;
import java.sql.ResultSetMetaData;

/**
 *manipule les customers en db
 * @author loisp
 */
public class MemberCustomerDB {

    //vérifie si le member existe dans la base de donnée av son login et son mdp
    public boolean connectUser(String login, String password) // méthode a implémenter
    {
        return true;
    }

    //fonction qui vérifie si le membre existe vraiment et qui renvoie un boolean
    public static boolean checkMember(String login, String password) throws SQLException {
        try {

            PreparedStatement insert = getDbConnection().prepareStatement("SELECT login FROM CUSTOMER" + " WHERE login = ? AND password = ?");
            insert.setString(1, login);
            insert.setString(2, password);

            ResultSet result = insert.executeQuery();

            if (result.first()) {
                return true;
            } else {
                return false;

            }
        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static int nbMemberCustomersInDB() {
        Connection conn;
        int nbOfCustomers = 0;
        ResultSet rs;
        try {
            conn = (Connection) getDbConnection();

            Statement essai = conn.createStatement();
            rs = essai.executeQuery("SELECT * from CUSTOMER");
            rs.last();
            nbOfCustomers = rs.getRow();

        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nbOfCustomers;
    }

    public static ArrayList<MemberCustomer> getAllMemberCustomerDB() throws SQLException {
        //we calculate how many customers there are in the database
        int nbOfCustomers = nbMemberCustomersInDB();
        ArrayList<MemberCustomer> memberCustList = new ArrayList<MemberCustomer>();
        ArrayList<String> logins = new ArrayList<String>();
        logins = getAllLogin();

        for (int i = 0; i < nbOfCustomers; i++) {
            MemberCustomer member = new MemberCustomer();
            member = getMemberCustomerDB(logins.get(i));
            memberCustList.add(member);
        }
        return memberCustList;
    }

    public static ArrayList<String> getAllLogin() throws SQLException {
        PreparedStatement insert = getDbConnection().prepareStatement("select login from CUSTOMER");
        ResultSet result = insert.executeQuery();

        ArrayList<String> logins = new ArrayList<String>();

        while (result.next()) {
            String login = result.getString("login");
            logins.add(login);
        }

        return logins;

    }

    public static MemberCustomer getMemberCustomerDB(String login) throws SQLException {
        try {
            PreparedStatement insert = getDbConnection().prepareStatement("SELECT * FROM CUSTOMER WHERE login = ?");
            insert.setString(1, login);
            ResultSet result = insert.executeQuery();
            if (result.next()) {
                String password = result.getString("password");
                String pass = result.getString("bundle");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                MemberCustomer membertmp = new MemberCustomer(login, password, pass, firstName, lastName);
                return membertmp;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static void initDBCustomer() {

        Connection conn;
        //test
        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("CREATE TABLE IF NOT EXISTS `CUSTOMER` ("
                    + "  `login` varchar(100) NOT NULL,"
                    + "  `password` varchar(100) NOT NULL,"
                    + "  `bundle` varchar(100) NOT NULL,"
                    + "  `firstName` varchar(100) NOT NULL,"
                    + "  `lastName` varchar(100) NOT NULL,"
                    + "  PRIMARY KEY (`login`)"
                    + ")");
        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addDBCustomer(String login, String password, String bundle, String firstName, String lastName) {

        Connection conn;

        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("INSERT INTO CUSTOMER VALUES ('" + login + "','" + password + "', '" + bundle + "', '" + firstName + "', '" + lastName + "')");
        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*public static void main(String[] args) throws SQLException {
        ArrayList<MemberCustomer> memberCustomerList = new ArrayList<MemberCustomer>();
        memberCustomerList = getAllMemberCustomerDB();

        for (int i = 0; i < nbMemberCustomersInDB(); i++) {
            memberCustomerList.get(i).afficherMemberCustomer();
        }
    }utile lors de test*/

    public static void afficherJTable() {
        Connection conn = null;
        String[] colNames;
        String[][] tableData;

        try {
            

            conn = (Connection) getDbConnection();

            
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet resultSet = stmt.executeQuery("SELECT c.login as Login, c.firstname as 'First name', c.lastname as 'Last name', c.bundle as Bundle, round(sum(r.price),2) as Purchases FROM `customer` as c, reservation as r WHERE c.login=r.login group by c.login UNION SELECT 'TOTAL' as Login, '---' as 'First name', '---' as 'Last name', '---' as Bundle, round(sum(r.price),2) as Purchases FROM `customer` as c, reservation as r WHERE c.login=r.login");

            resultSet.last(); 
            int nbRows = resultSet.getRow(); 
            resultSet.first(); 

            
            ResultSetMetaData resultMeta = resultSet.getMetaData();

            colNames = new String[resultMeta.getColumnCount()]; 

            for (int i = 0; i < resultMeta.getColumnCount(); i++) {
                
                colNames[i] = resultMeta.getColumnLabel(i + 1); 
            }

            
            tableData = new String[nbRows][resultMeta.getColumnCount()]; 
            for (int row = 0; row < nbRows; row++) {
                for (int col = 0; col < resultMeta.getColumnCount(); col++) {
                    tableData[row][col] = resultSet.getString(col + 1);
                }
                resultSet.next();
            }

            TableFormatter table = new TableFormatter(tableData, colNames);

            
            resultSet.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
    }

    public static ArrayList<Customer> getCustomerListDB() throws SQLException {
        Connection conn;
        ArrayList <Customer> customerList = new ArrayList<Customer>();
        
        try {
            conn = (Connection) getDbConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from CUSTOMER" );
            
            while (rs.next()) {
                String login = rs.getString("login");
                String password = rs.getString("password");
                String bundle = rs.getString("bundle");
                String firstname =rs.getString("firstname");
                String lastname =rs.getString("lastname");

                MemberCustomer cust = new MemberCustomer(login,password,bundle,firstname,lastname);
                customerList.add(cust);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        return customerList;
    }
    

}
