/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import static Model.JBDC.getDbConnection;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *manipulation des employees en base de données
 * @author loisp
 */
public class EmployeeDB {
    
        //vérifie si l'employee existe dans la base de donnée av son login et son mdp
    public boolean connectEmployee(String login, String password) // méthode a implémenter
    {
        return true;
    }
    
    //verfie mdp et login
    public static boolean checkEmployee(String login, String password) throws SQLException {
        try {
        
            PreparedStatement insert = getDbConnection().prepareStatement("SELECT login FROM EMPLOYEE" + " WHERE login = ? AND password = ?");
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
    
    public double getBenefits()
    {
        return 0;
    }
    
    //tests functions
    public static void initDBEmployee() {

        Connection conn;

        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("CREATE TABLE IF NOT EXISTS `EMPLOYEE` ("
                    + "  `login` varchar(100) NOT NULL,"
                    + "  `password` varchar(100) NOT NULL,"
                    + "  `firstName` varchar(100) NOT NULL,"
                    + "  `lastName` varchar(100) NOT NULL,"
                    + "  PRIMARY KEY (`login`)"
                    + ")");

        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    public static void addDBEmployee(String login, String password, String firstName, String lastName) {

        Connection conn;
        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("INSERT INTO EMPLOYEE VALUES ('" + login + "','" + password + "', '" + firstName + "', '" + lastName + "')");

        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String getPasswordEmpDB(String login) throws SQLException {
        PreparedStatement insert = getDbConnection().prepareStatement("select type from EMPLOYEE" + " where login = ?");
        insert.setString(1, login);

        ResultSet result = insert.executeQuery();

        if (result.first()) {
            String password = result.getString("password");
            return password;
        }

        return null;
    }
    
    public static String getFirstNameEmpDB(String login) throws SQLException {
        PreparedStatement insert = getDbConnection().prepareStatement("select type from EMPLOYEE" + " where login = ?");
        insert.setString(1, login);

        ResultSet result = insert.executeQuery();

        if (result.first()) {
            String fN = result.getString("firstName");
            return fN;
        }

        return null;
    }
    
    public static String getLastNameEmpDB(String login) throws SQLException {
        PreparedStatement insert = getDbConnection().prepareStatement("select type from EMPLOYEE" + " where login = ?");
        insert.setString(1, login);

        ResultSet result = insert.executeQuery();

        if (result.first()) {
            String lN = result.getString("lastName");
            return lN;
        }

        return null;
    }

}