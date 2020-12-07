/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.JBDC;
import Model.MemberCustomerDB;
import Model.MovieDB;
import Model.ReservationDB;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import View.GUI;

/**
 *
 * @author charl
 */
public class Main {

    public static void main(String[] args) throws SQLException, ParseException {

        Cinema cinema = new Cinema();

        /*Movie.deleteMovie("OuaisOuais",cinema);
        
        
         Movie movie = new Movie("Hello world", "Enfantin", 2020, new Time(02,03,04));
         movie.addMovie( cinema);*/
        /*
         for (int i = 0; i < cinema.getMovieList().size(); i++) {
         cinema.getMovieList().get(i).afficherMovie();
         System.out.println(" ");
         }
         */
        /*
         MemberCustomer cust = new MemberCustomer("exa","dddd", "children","exemple","try");
         cust.addMemberCustomer();
         cust.deleteMemberCustomer();*/
        
        GUI f = new GUI(cinema);      
        f.setVisible(true);

    }

}
