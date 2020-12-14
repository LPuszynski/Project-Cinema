/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//SOURCES :
//https://www.mediaforma.com/java-acces-a-la-base-de-donnees-dans-netbeans/
//Lectures by Mme Paranjape
package Model;

import java.sql.*;
import com.mysql.jdbc.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import Controller.MemberCustomer;
import Controller.Projection;
import View.TableFormatter;
import java.util.ArrayList;

/**
 *manupilation globale de base de donnée
 * @author charl
 */
public class JBDC {

    public static void main(String[] args) throws SQLException {

        
         //Date date = new Date(10, 9, 2000);
         //date.display();

         

        //Création des tables if not exist
        /*EmployeeDB.initDBEmployee();
        MovieDB.initDBMovie();
        MemberCustomerDB.initDBCustomer();
        ProjectionDB.initDBProjections();
        //Mise a zero des tables
        DBDeleteTable("MOVIES");
        DBDeleteTable("EMPLOYEE");
        DBDeleteTable("CUSTOMER");
        DBDeleteTable("PROJECTIONS");
        //TEsts
        MovieDB.addDBMovie("OuiOui", "horreur", 2000, "04:04:21");
        MovieDB.addDBMovie("NonNon", "comédiefrançaise", 2001, "03:03:03");
        MovieDB.addDBMovie("OuaisOuais", "émotion",2002 , "01:01:21");

        EmployeeDB.addDBEmployee("Chacha", "loulou", "Charlotte", "LAMBERT");
        EmployeeDB.addDBEmployee("Lolo", "NulALol", "Lois", "PUSZYNSKI");
        EmployeeDB.addDBEmployee("TOINOU", "justfaker", "Antoine", "CRUVEILHER");

        MemberCustomerDB.addDBCustomer("Enfant", "petit", "junior", "Bebe", "Alfred");
        MemberCustomerDB.addDBCustomer("PAPAPAPI", "vieux", "senior", "PEPE", "Thierry");

        //ne marche pas
        addDBProjection("2029-12-02","00:00:11", 100, 99, "OuiOui");
        addDBProjection("2000-01-01","17:04:01", 29, 20, "NonNon");
        addDBProjection("2001-11-30","03:20:12", 50, 40, "OuaisOuais");
        deleteDBLineHuman("Lolo", "EMPLOYEE");
        MovieDB.deleteDBLineMovie("OuiOui");

        setElementDB("EMPLOYEE", "firstName", "Pierre", "login", "TOINOU");

        MemberCustomer test = MemberCustomerDB.getMemberCustomerDB("Enfant");

        selectDataDB("MOVIES");
        selectDataDB("EMPLOYEE");
        selectDataDB("CUSTOMER");*/

        /*
         //test recuperation des données

         Time runinngT1 = getRunningTime("OuiOui");
         String type1 = getType("OuiOui");
         java.util.Date date1 = getDate("OuiOui");
         double ticketPrice1 = getTicketPrice("OuiOui");
         System.out.println("OUIOUI : running time = " + runinngT1 + " type : "+ type1 + " Date : " + date1 + " ticketPrice = " + ticketPrice1);

         */
        
       /*ArrayList<Projection> projList = new ArrayList<Projection>();
       projList = ProjectionDB.getAllProjectionsDB(true);
       for (int i= 0; i<projList.size();i++)
       {
           projList.get(i).afficherProjection();
       }*/
    }

    public static java.sql.Connection getDbConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/projet?useSSL=false";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    public static void DBDeleteTable(String tableName) {

        Connection conn;

        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("DELETE FROM " + tableName);
        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void deleteDBLineHuman(String login, String table) {
        Connection conn;
        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("DELETE FROM " + table + " WHERE login = '" + login + "'");
        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setElementDB(String table, String nomColumn, String newValeur, String typeKey, String myKey) {
        Connection conn;

        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("UPDATE " + table
                    + "  set " + nomColumn + " = '" + newValeur + "' WHERE " + typeKey + " = '" + myKey + "'");

        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void selectDataDB(String tableName) throws SQLException {
        Connection conn;

        //partie de code inspiré de https://www.developpez.net/forums/d704245/java/general-java/persistance-donnees/jdbc/recuperer-donnees-d-bd-java/
        try {

            conn = (Connection) getDbConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from " + tableName);

            while (rs.next()) {
                if (tableName.equalsIgnoreCase("MOVIES")) {
                    System.out.println(rs.getString("title") + rs.getString("type") + rs.getString("releaseDate") + rs.getTime("runningTime"));//l'ordre est important
                } else if (tableName.equalsIgnoreCase("CUSTOMER")) {
                    System.out.println(rs.getString("login") + rs.getString("password") + rs.getString("bundle") + rs.getString("firstName") + rs.getString("lastName"));//l'ordre est important
                } else if (tableName.equalsIgnoreCase("EMPLOYEE")) {
                    System.out.println(rs.getString("login") + rs.getString("password") + rs.getString("firstName") + rs.getString("lastName"));//l'ordre est important
                } else if (tableName.equalsIgnoreCase("PROJECTIONS")) {
                    System.out.println(rs.getString("idProj")+ rs.getString("movieProjected") + rs.getString("projectionDate") + rs.getString("projectionHour") + rs.getInt("numberOfSeats") );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
