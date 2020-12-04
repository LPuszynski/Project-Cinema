/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import Model.ProjectionDB;

import java.sql.Time;

/**
 *
 * @author loisp
 */
public class Projection {
    private String projectionDate;
    private String projectionHour;
    private int numberOfSeats;
    private int numberOfFreeSeats;
    private Movie movieProjected;
    
    public Projection(){}

    public Projection(String projectionDate, String projectionHour, int numberOfSeats, int numberOfFreeSeats, Movie movieProjected) {
        this.projectionDate = projectionDate;
        this.projectionHour = projectionHour;
        this.numberOfSeats = numberOfSeats;
        this.numberOfFreeSeats = numberOfFreeSeats;
        this.movieProjected = movieProjected;
    }
    
    
    
    public void setNumberOfFreeSeats()
    {
        numberOfFreeSeats = numberOfSeats - ProjectionDB.GetDBNumberOfOccupedPlaces(projectionDate, projectionHour, movieProjected.getTitle());
    }
   
}
