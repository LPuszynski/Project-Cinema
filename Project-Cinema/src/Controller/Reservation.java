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
 *une réservation lie un client a une projection
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
