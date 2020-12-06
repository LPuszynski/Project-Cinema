/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.*;
import Controller.DateConverter;
import Controller.Movie;
import static Model.JBDC.DBDeleteTable;
import static Model.JBDC.getDbConnection;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author loisp
 */
public class MovieDB {
    
    /*
    //pas necessaire je pense
    public void addMovie() {

    }

    public void suppMovie() {
        
    }
    */

    public static ArrayList<Movie> getMovieListDB() throws SQLException {
        Connection conn;
        ArrayList <Movie> movieList = new ArrayList<Movie>();
        
        try {
            conn = (Connection) getDbConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from MOVIES" );
            
            while (rs.next()) {
                String title = rs.getString("title");
                String type = rs.getString("type");
                int rDate = rs.getInt("releaseDate");
                Time rTime =  rs.getTime("runningTime");

                Movie movie = new Movie (title,type,rDate,rTime);
                movieList.add(movie);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return movieList;
    }
    

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

    public static String getDate(String movieName) throws SQLException {
        PreparedStatement insert = getDbConnection().prepareStatement("select releaseDate from MOVIES" + " where title = ?");
        insert.setString(1, movieName);

        ResultSet result = insert.executeQuery();

        if (result.first()) {
            String date= result.getString("releaseDate");
            return date;
        }

        return null;
    }
    
    //apres avoir modifier la liste de moovie, on met à jour la base de données avec les films qui sont dans l'arrayList
    //au debut du main on cree un cinema qui aura toutes les infos, il n'y aura qu'un seul cinema créé, ce sera lui qu'il faudra envoyer en parametre
    public static void updateMovieBDD(Cinema cinema) throws SQLException
    {
        DBDeleteTable("MOVIES");
        ArrayList <Movie> movieList = new ArrayList<Movie>();
        movieList = cinema.getMovieList();
        
        for (int i= 0; i<movieList.size();i++)
        {
            addDBMovie(movieList.get(i).getTitle(), movieList.get(i).getType(), movieList.get(i).getReleaseDate(), movieList.get(i).getRunningTime().toString());
        }
    }
    

    
    /*
    //to test :
    public static void main(String[] args) throws SQLException {
        ArrayList <Movie> movieList = new ArrayList<Movie>();
        movieList = getMovieDB();
        
        
        for (int i=0;i<movieList.size(); i++)
        {
            movieList.get(i).afficherMovie();
        }
    }*/

   public static void initDBMovie() {

        Connection conn;

        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("CREATE TABLE IF NOT EXISTS `MOVIES` ("
                    + "  `title` varchar(100) NOT NULL,"
                    + "  `type` varchar(100) NOT NULL,"
                    + "  `releaseDate` int NOT NULL,"
                    + "  `runningTime` time NOT NULL,"
                    + "  PRIMARY KEY (`title`)"
                    + ")");
        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   public static void addDBMovie(String title, String type, int releaseDate, String runningTime) {

        Connection conn;
        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("INSERT INTO MOVIES VALUES ('" + title + "','" + type + "', '" + releaseDate + "', '" + runningTime + "')");

        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public static void deleteDBLineMovie(String title) {
        Connection conn;
        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("DELETE FROM MOVIES WHERE title = '" + title + "'");
        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
