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

/**
 *
 * @author charl
 */
public class Menu {

    public static void main(String[] args) throws SQLException {
        /*
         Date date = new Date(10, 9, 2000);
         date.display();

         Connection conn = null;
         String[] colNames;
         String[][] tableData;

         try{
         //on établit une connexion avec la base de données java

         Class.forName("com.mysql.jdbc.Driver");
         String url = "jdbc:mysql://localhost:3306/projet?useSSL=false";
         String user = "root";
         String password = "" ;

         conn = (Connection) DriverManager.getConnection(url,user, password);

         //on crée un objet Statement.
         //Par son intermédiaire, on pourra exécuter des commandes SQL pour interroger la base de données et obtenir les résultats correspondants.
         Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE ,ResultSet.CONCUR_READ_ONLY);

         // on définit une requête SQL en créant un objet ResultSet à partir de l’objet Statement
         ResultSet resultSet = stmt.executeQuery("Select * from film");

         resultSet.last(); //on va au dernier élément pour savoir la taille
         int nbRows = resultSet.getRow(); // il y a nbRows lignes dans la table
         resultSet.first(); //on remet le pointeur en début de table

         //on récupère les métadonnées de la table
         //Par leur intermédiaire, on pourra obtenir le nombre de colonnes dans la table avec getColumnCount() et le nom des colonnes avec getColumnName() :
         ResultSetMetaData resultMeta = resultSet.getMetaData();

         colNames = new String[resultMeta.getColumnCount()]; //on initialise autant de case dans le tableau qu'il y a de colonnes dans la table

         for(int i=0; i<resultMeta.getColumnCount(); i++)
         {
         //System.out.println(resultMeta.getColumnName(i+1));
         colNames[i] = resultMeta.getColumnLabel(i+1); //ou sinon utiliser getColumnName
         }

         //on pour récupère tous les enregistrements de la table :
         tableData = new String[nbRows][resultMeta.getColumnCount()]; //allouer de la place
         for (int row=0; row<nbRows; row++)
         {
         for(int col=0; col<resultMeta.getColumnCount(); col++)
         {
         tableData[row][col] = resultSet.getString(col+1);
         }
         resultSet.next();
         }


         TableFormatter table = new TableFormatter(tableData, colNames);

         while (resultSet.next())
         {
         System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
         }

         //ferme les objets ouverts avec la méthode close() :
         resultSet.close();
         stmt.close();
         conn.close();

         }
         catch(SQLException | ClassNotFoundException e )
         {
         System.out.println(e.getMessage());
         }
         */
        //Création des tables if not exist
        initDBEmployee();
        initDBMovie();
        initDBCustomer();
        initDBProjection();
        //Mise a zero des tables
        DBDeleteTable("MOVIES");
        DBDeleteTable("EMPLOYEE");
        DBDeleteTable("CUSTOMER");
        //TEsts
        addDBMovie("OuiOui", "horreur", "2020-01-01", "04:04:21", 3);
        addDBMovie("NonNon", "comédiefrançaise", "2018-02-02", "03:03:03", 11);
        addDBMovie("OuaisOuais", "émotion", "2017-04-04", "01:01:21", 78.25);

        addDBEmployee("Chacha", "loulou", "Charlotte", "LAMBERT");
        addDBEmployee("Lolo", "NulALol", "Lois", "PUSZYNSKI");
        addDBEmployee("TOINOU", "justfaker", "Antoine", "CRUVEILHER");

        addDBCustomer("Enfant", "petit", "junior", "Bebe", "Alfred");
        addDBCustomer("PAPAPAPI", "vieux", "senior", "PEPE", "Thierry");

        deleteDBLineHuman("Lolo", "EMPLOYEE");
        deleteDBLineMovie("OuiOui");

        setElementDB("EMPLOYEE", "firstName", "Pierre", "login", "TOINOU");

        selecDataDB("MOVIES");
        selecDataDB("EMPLOYEE");
        selecDataDB("CUSTOMER");
        /*
         //test recuperation des données

         Time runinngT1 = getRunningTime("OuiOui");
         String type1 = getType("OuiOui");
         java.util.Date date1 = getDate("OuiOui");
         double ticketPrice1 = getTicketPrice("OuiOui");
         System.out.println("OUIOUI : running time = " + runinngT1 + " type : "+ type1 + " Date : " + date1 + " ticketPrice = " + ticketPrice1);

         */    }

    public static Time getRunningTime(String movieName) throws SQLException {
        PreparedStatement insert = getDbConnection().prepareStatement("select runningTime from MOVIES" + " where title = ?");
        insert.setString(1, movieName);

        ResultSet result = insert.executeQuery();

        if (result.first()) {
            Time rT = result.getTime("runningTime");
            return rT;
        }

        return null;
    }

    public static double getTicketPrice(String movieName) throws SQLException {
        PreparedStatement insert = getDbConnection().prepareStatement("select ticketPrice from MOVIES" + " where title = ?");
        insert.setString(1, movieName);

        ResultSet result = insert.executeQuery();

        if (result.first()) {
            double tP = result.getDouble("ticketPrice");
            return tP;
        }
        return 0.0; // un double ne peut pas etre null donc on ne peut pas return null
    }

    public static String getType(String movieName) throws SQLException {
        PreparedStatement insert = getDbConnection().prepareStatement("select type from MOVIES" + " where title = ?");
        insert.setString(1, movieName);

        ResultSet result = insert.executeQuery();

        if (result.first()) {
            String type = result.getString("type");
            return type;
        }

        return null;
    }

    public static java.util.Date getDate(String movieName) throws SQLException {
        PreparedStatement insert = getDbConnection().prepareStatement("select releaseDate from MOVIES" + " where title = ?");
        insert.setString(1, movieName);

        ResultSet result = insert.executeQuery();

        if (result.first()) {
            java.util.Date date;
            date = result.getDate("releaseDate");
            return date;
        }

        return null;
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
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void initDBProjection() {

        Connection conn;

        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("CREATE TABLE IF NOT EXISTS `PROJECTIONS` ("
                    + "  `movieProjected` varchar(100) NOT NULL,"
                    //+ "  `` varchar(100) NOT NULL,"
                    + "  `projectionDate` date NOT NULL,"
                    + "  `projectionHour` time NOT NULL,"
                    + "  `numberOfSeats` int NOT NULL,"
                    + "  `numberOfFreeSeats` int NOT NULL,"
                    
                    + "  PRIMARY KEY (`movieProjected`,`projectionDate`,`projectionHour`)"
                    + ")");
        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addDBProjection(String projectionDate, String projectionHour, int numberOfSeats, int numberOfFreeSeats, String movieProjected) {

        Connection conn;
        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("INSERT INTO PROJECTIONS VALUES ('" + projectionDate + "','" + projectionHour + "', '" + numberOfSeats + "', '" + numberOfFreeSeats + "'," + movieProjected + ")");

        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void initDBMovie() {

        Connection conn;

        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("CREATE TABLE IF NOT EXISTS `MOVIES` ("
                    + "  `title` varchar(100) NOT NULL,"
                    + "  `type` varchar(100) NOT NULL,"
                    + "  `releaseDate` date NOT NULL,"
                    + "  `runningTime` time NOT NULL,"
                    + "  `ticketPrice` double NOT NULL,"
                    + "  PRIMARY KEY (`title`)"
                    + ")");
        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addDBMovie(String title, String type, String releaseDate, String runningTime, double ticketPrice) {

        Connection conn;
        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("INSERT INTO MOVIES VALUES ('" + title + "','" + type + "', '" + releaseDate + "', '" + runningTime + "'," + ticketPrice + ")");

        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addDBEmployee(String login, String password, String firstName, String lastName) {

        Connection conn;
        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("INSERT INTO EMPLOYEE VALUES ('" + login + "','" + password + "', '" + firstName + "', '" + lastName + "')");

        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addDBCustomer(String login, String password, String bundle, String firstName, String lastName) {

        Connection conn;
        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("INSERT INTO CUSTOMER VALUES ('" + login + "','" + password + "', '" + bundle + "', '" + firstName + "', '" + lastName + "')");
        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void deleteDBLineHuman(String login, String table) {
        Connection conn;
        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("DELETE FROM " + table + " WHERE login = '" + login + "'");
        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void deleteDBLineMovie(String title) {
        Connection conn;
        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("DELETE FROM MOVIES WHERE title = '" + title + "'");
        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
    
    

    public static void deleteDBProjection(String projectionDate, String projectionHour, String movieProjected) {
        Connection conn;
        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("DELETE FROM PROJECTIONS WHERE projectionDate = '" + projectionDate + "' AND projectionHour = '" + projectionHour +  "' AND movieProjected = '" + movieProjected + "'");
        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void selecDataDB(String tableName) throws SQLException {
        Connection conn;

        //partie de code inspiré de https://www.developpez.net/forums/d704245/java/general-java/persistance-donnees/jdbc/recuperer-donnees-d-bd-java/
        try {

            conn = (Connection) getDbConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from " + tableName);

            while (rs.next()) {
                if (tableName.equalsIgnoreCase("MOVIES")) {
                    System.out.println(rs.getString("title") + rs.getString("type") + rs.getDate("releaseDate") + rs.getTime("runningTime") + rs.getDouble("ticketPrice"));//l'ordre est important 
                } else if (tableName.equalsIgnoreCase("CUSTOMER")) {
                    System.out.println(rs.getString("login") + rs.getString("password") + rs.getString("bundle") + rs.getString("firstName") + rs.getString("lastName"));//l'ordre est important 
                } else if (tableName.equalsIgnoreCase("EMPLOYEE")) {
                    System.out.println(rs.getString("login") + rs.getString("password") + rs.getString("firstName") + rs.getString("lastName"));//l'ordre est important 
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
