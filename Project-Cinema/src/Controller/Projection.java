/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
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
    private String idProj;
    private String projectionDate;
    private Time projectionHour;
    private int numberOfSeats;
    private String movieTitle;
    private ArrayList<Reservation> reservationList;
    private double discount;
    private boolean availibility;
    
    public Projection(){}

    public Projection(String idProj,String projectionDate, Time projectionHour, String movieTitle, double discount) {
        this.idProj = idProj;
        this.projectionDate = projectionDate;
        this.projectionHour = projectionHour;
        this.numberOfSeats = 30;
        this.movieTitle = movieTitle;
        reservationList = new ArrayList<Reservation>();
        availibility = true; // à la creation d'une classe, il y a de places disponibles
    }
    
    
    public int getNbFreeSeats()
    {
        return (numberOfSeats-getNbSeatsReserved());
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
   public String getIdProj()
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
   
   public void addReservation(Customer cust, int nbTickets) throws SQLException
   {
       Reservation resa = new Reservation(this, cust, nbTickets);
       reservationList.add(resa);
       ReservationDB.addDBReservation(this.getIdProj(), cust.getLogin(), resa.getNbOfTicketsRes(), resa.getTotalPriceRes(this));
   }
   
}
