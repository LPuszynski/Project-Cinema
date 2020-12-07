/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.JBDC;
import Model.ReservationDB;
import static Model.ReservationDB.setElementReservationDB;
import java.sql.SQLException;

/**
 *
 * @author charl
 */
public class Reservation {
    private Projection projection;
    private Customer customer;
    private int nbOfTickets;
    
    public Reservation(Projection proj, Customer cust, int nbSeats)
    {
        projection = proj;
        customer = cust;
        nbOfTickets = nbSeats;
    }        
    
    /*public void setNbOfTickets(int nb)
    {
        nbOfTickets = nb;
        setElementReservationDB( nbOfTickets, "idProj", projection.getIdProj(),"login", customer.getLogin());
    }*/
    
    
    public int getNbOfTicketsRes()
    {
        return nbOfTickets;
    }
    
    public double getTotalPriceRes(Projection proj)
    {
        double price1ticket = customer.getPrice1Ticket();
        
        double totalPrice = price1ticket - proj.getDiscount();
        totalPrice *= nbOfTickets;
        
        return totalPrice;
    }
    
    
}
