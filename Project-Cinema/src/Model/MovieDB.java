/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import static Model.Menu.getDbConnection;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
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

public class MovieDB {
    
    public void addMovie()
    {
        
    }
    
    public void suppMovie()
    {
        
    }
    
    public void updateMovie()
    {
        
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

   
}
