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
    private Movie movieProjected;
    private ArrayList<Reservation> reservationList;
    
    public Projection(){}

    public Projection(String idProj,String projectionDate, Time projectionHour, int numberOfSeats, Movie movieProjected) {
        this.idProj = idProj;
        this.projectionDate = projectionDate;
        this.projectionHour = projectionHour;
        this.numberOfSeats = numberOfSeats;
        //this.numberOfFreeSeats = numberOfFreeSeats;
        this.movieProjected = movieProjected;
        reservationList = new ArrayList<>();
    }
    
    
    
    public void setNumberOfFreeSeats()
    {
        //numberOfFreeSeats = numberOfSeats - ProjectionDB.GetDBNumberOfOccupedPlaces(projectionDate, projectionHour, movieProjected.getTitle());
    }
   
}
