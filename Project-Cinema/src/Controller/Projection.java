/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import Model.ProjectionDB;

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
    //private int numberOfFreeSeats;
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
        return reservationList.size();
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
   
}
