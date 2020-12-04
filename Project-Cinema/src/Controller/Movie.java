/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Model.Menu.addDBMovie;
import static Model.Menu.getDbConnection;
import com.mysql.jdbc.Connection;
import java.sql.*;

/**
 *
 * @author charl
 */
public class Movie {

    private String title;
    private String genre;
    private Date releaseDate;
    private int runningTime; //en minutes
    private double ticketPrice;
    
    //default constructor
    public Movie () {}

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void addMovie(String title, String type, String releaseDate, String runningTime, double ticketPrice) {    
    }

    public void deleteMovie() {
    }

    public void setMovie() {
    }
    
    public String getTitle()
    {
        return title;
    }
}
