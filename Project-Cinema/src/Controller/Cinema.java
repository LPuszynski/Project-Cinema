/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.ArrayList;
import Model.MovieDB;
import static Model.MovieDB.getMovieListDB;
import java.sql.SQLException;
/**
 *
 * @author loisp
 */
public class Cinema {
    private double profits;
    private ArrayList<Movie> movieList;
    private double ticketPrice;
    
    public Cinema() throws SQLException
    {
        ticketPrice = 10;
        this.profits = 0;
        this.movieList = new ArrayList<Movie>();
        this.movieList = getMovieListDB();
    }
    
    public ArrayList<Movie> getMovieList()
    {
        return movieList;
    }
    
    public double getTicketPrice()
    {
        return ticketPrice;
    }
    
    
   
    }
