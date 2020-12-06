/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.JBDC;
import static Model.JBDC.getDbConnection;
import Model.MovieDB;
import com.mysql.jdbc.Connection;
import java.sql.*;

/**
 *
 * @author charl
 */
public class Movie {

    private String title;
    private String type;
    private int releaseDate ;
    private Time runningTime; //en minutes
    
    //default constructor
    public Movie () {}
    
    public Movie(String title, String type, int releaseDate, Time runningTime, double ticketPrice) 
    {    
        this.title = title;
        this.type = type ;
        
        this.releaseDate = releaseDate;
        
        this.runningTime = runningTime;
        
    }

    public String getTitle() {
        return title;
    }
    public int getReleaseDate() {
        return releaseDate;
    }
    public Time getRunningTime() {
        return runningTime;
    }
    public String getType() {
        return type;
    }

    
    public static void deleteMovie(String movieName, Cinema cinema) {
        //delete in the bdd
        MovieDB.deleteDBLineMovie(movieName);
        
        //delete in this arrayList
        for(int i=0;i<cinema.getMovieList().size(); i++)
        {
            if (cinema.getMovieList().get(i).getTitle().equals(movieName))
            {
                cinema.getMovieList().remove(i);
            }
        }
    }
    

    
    
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
            MovieDB.addDBMovie(title, type, releaseDate, runningTime.toString()) ;
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
        System.out.println("Type : " + type);
        System.out.println("Release date : " + releaseDate);
        System.out.println("Running time : " + runningTime);
    }

    
    
}
