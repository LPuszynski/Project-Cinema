/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.JBDC;

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
    
    public void setNbOfTickets(int nb)
    {
        nbOfTickets = nb;
        //setElementDB();
    }
    
    
    public int getNbOfTicketsRes()
    {
        return nbOfTickets;
    }
    
    public double getTotalPriceRes(Cinema cinema)
    {
        double price1ticket = customer.getPrice1Ticket(cinema);
        double totalPrice = price1ticket*nbOfTickets;
        
        return totalPrice;
    }
}
