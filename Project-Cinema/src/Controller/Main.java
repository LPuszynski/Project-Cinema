/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.MovieDB;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author charl
 */
public class Main {

    public static void main(String[] args) throws SQLException, ParseException {

        Cinema cinema = new Cinema();
        
        Movie.deleteMovie("OuaisOuais",cinema);
        
        
        Movie movie = new Movie("Hello world", "Enfantin", "2020-06-21", new Time(02,03,04), 3);
        movie.addMovie( cinema);
        
        
        for (int i = 0; i < cinema.getMovieList().size(); i++) {
            cinema.getMovieList().get(i).afficherMovie();
            System.out.println(" ");
            
        }
        
        MovieDB.updateMovieBDD(cinema);
        
        /*
        
         MemberCustomer cust = new MemberCustomer();
         Movie mov = new Movie();
       
         System.out.println(cust.getPrice(mov));*/
    }
}