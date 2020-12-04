/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import Controller.MemberCustomer;

import static Model.JBDC.getDbConnection;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author loisp
 */
public class MemberCustomerDB {

    //vérifie si le member existe dans la base de donnée av son login et son mdp
    public boolean connectUser(String login, String password) // méthode a implémenter
    {
        return true;
    }

    public void addMemberToDB() // à implémenter ajoute un member a la BDD
    {

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
    
    public static MemberCustomer getMemberCustomer(String login) throws SQLException
    {
        try{
            PreparedStatement insert = getDbConnection().prepareStatement("SELECT * FROM CUSTOMER WHERE login = ?");
            insert.setString(1, login);
            ResultSet result = insert.executeQuery();
            if (result.next())
            {
                String password = result.getString("password");
                String pass = result.getString("bundle");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                MemberCustomer membertmp = new MemberCustomer(login, password, pass, firstName, lastName);
                return membertmp;
            }
            else 
            {
                return null;
            }
        }catch(SQLException ex){
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static void initDBCustomer() {

        Connection conn;

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

}
