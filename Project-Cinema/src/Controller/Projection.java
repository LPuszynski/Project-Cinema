/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import Model.JBDC;
import Model.ProjectionDB;
import Model.ReservationDB;
import java.sql.SQLException;

import java.sql.Time;
import java.util.ArrayList;

/**
 *
 * @author loisp
 */
public class Projection {
    private int idProj;
    private String projectionDate;
    private Time projectionHour;
    private int numberOfSeats;
    private String movieTitle;
    private ArrayList<Reservation> reservationList;
    private double discount;
    private boolean availibility;

    public Projection(){}

    public Projection(int idProj,String projectionDate, Time projectionHour, String movieTitle, double discount, boolean ava) {
        this.idProj = idProj;
        this.projectionDate = projectionDate;
        this.projectionHour = projectionHour;
        this.numberOfSeats = 30;
        this.movieTitle = movieTitle;
        this.discount = discount;
        reservationList = new ArrayList<Reservation>();
        availibility = ava; 
    }
    
    
    public int getNbFreeSeats()
    {
        return (numberOfSeats-getNbSeatsReserved());
    }
    
    public int getAllReservationForProj()
    {
        try {
            return ReservationDB.getAllQuantityDB(idProj);
        } catch (SQLException e) {

        }
        return 0;
    }
    
    public static String[][] getAllReservationForCustomer(String custName)
    {
        try {
            return ReservationDB.getAllReservationForCustomerDB(custName);
        } catch (SQLException e) {

        }
        return null;
    }
    public int getNbSeatsReserved()
    {
        int nbSeatReserved = 0;
        for (int i = 0 ; i< reservationList.size(); i++)
        {
            nbSeatReserved += reservationList.get(i).getNbOfTicketsRes();
        }
        return nbSeatReserved; 
    }
    
    public boolean getAvailability()
    {
        if (getNbSeatsReserved() < numberOfSeats)
        {
            return true ;
        }
        else 
        {
            return false;
        }
    }
    /*public void setNumberOfFreeSeats()
    {
        numberOfFreeSeats = numberOfSeats - ProjectionDB.GetDBNumberOfOccupedPlaces(projectionDate, projectionHour, movieProjected.getTitle());
    }*/
   public int getIdProj()
   {
       return idProj;
   }
   
   public double getDiscount()
   {
       return discount;
   }
   
   public String getMovieTitle()
   {
       return movieTitle;
   }
   
   public String getProjectionDate()
   {
       return projectionDate;
   }
   
   public Time getProjectionHour()
   {
       return projectionHour;
   }
   
   public int getNumberOfSeats()
   {
       return numberOfSeats;
   }
   
   public void addReservation(Customer client, int nbTickets) throws SQLException
   {
       Reservation resa = new Reservation(this, client, nbTickets);
       reservationList.add(resa);
       ReservationDB.addDBReservation(this.getIdProj(), client.getLogin(), resa.getNbOfTicketsRes(), resa.getTotalPriceRes(this)*(1-client.getDiscount()));
   }
   
   public void afficherProjection()
    {
        System.out.println("idProj : " + idProj);
        System.out.println("movieTitle : " + movieTitle);
        System.out.println("projectionDate : " + projectionDate);
        System.out.println("projectionHour : " + projectionHour);
        System.out.println("availibility : " + availibility);
        System.out.println("discount : " + discount);
        System.out.println("numberOfSeats : " + numberOfSeats);
        System.out.println("");
    }
   
    public void setDiscount(double newD) throws SQLException
    {
       discount = newD;
       ProjectionDB.setDiscountDB( idProj,  newD);
    }

    public void setAvailibility(boolean newAvailibility)  throws SQLException
    {
        this.availibility = newAvailibility;
        ProjectionDB.setAvailabilityDB(idProj, newAvailibility);
    }
    
    public static void addProjection(String movieTitle, String jour, String heure)
    {
        ProjectionDB.addDBProjection(jour, heure, movieTitle, 0, true);
    }
}
