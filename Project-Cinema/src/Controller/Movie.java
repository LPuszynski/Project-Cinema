/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.JBDC;
import static Model.JBDC.getDbConnection;
import com.mysql.jdbc.Connection;
import java.sql.*;

/**
 *
 * @author charl
 */
public class Movie {

    private String title;
    private String genre;
    private String releaseDate ;
    private Time runningTime; //en minutes
    private double ticketPrice;
    
    //default constructor
    public Movie () {}
    
    public Movie(String title, String type, String releaseDate, Time runningTime, double ticketPrice) 
    {    
        this.title = title;
        this.genre = type ;
        
        this.releaseDate = releaseDate;
        
        this.runningTime = runningTime;
        
        this.ticketPrice = ticketPrice;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }
    public String getTitle() {
        return title;
    }
    public String getReleaseDate() {
        return releaseDate;
    }
    public Time getRunningTime() {
        return runningTime;
    }
    public String getGenre() {
        return genre;
    }

    
    public static void deleteMovie(String movieName, Cinema cinema) {
        for(int i=0;i<cinema.getMovieList().size(); i++)
        {
            if (cinema.getMovieList().get(i).getTitle().equals(movieName))
            {
                cinema.getMovieList().remove(i);
            }
        }
    }
    
    
    public void playMovie(){}

    public void setMovie(){}
    
    
    public void addMovie(Cinema cinema)
    {
        boolean alrealdyExists = false;
        for (int i = 0; i< cinema.getMovieList().size();i++)
        {
            if (cinema.getMovieList().get(i).getTitle().equals(this.title))
            {
                alrealdyExists = true;
            }
        }
        if (alrealdyExists == false)
        {
            cinema.getMovieList().add(this);
        }
        else
        {
            System.out.println("Be carefull : This movie alrealy exists\n");
        }
    }
    
    
    public void afficherMovie()
    {
        System.out.println("Title : " + title);
        System.out.println("Type : " + genre);
        System.out.println("Release date : " + releaseDate);
        System.out.println("Running time : " + runningTime);
        System.out.println("Ticket price : " + ticketPrice);
    }

    
    
}
