/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

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

    
    
    
    
    

    

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void playMovie() {
    }

    public void deleteMovie() {
    }

    public void setMovie() {
    }
}
