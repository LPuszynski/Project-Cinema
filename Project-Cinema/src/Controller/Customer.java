/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import Model.*;


/**
 *
 * @author charl
 */
public abstract class Customer {
    
    public Customer() 
    {
        //addDBCustomer(login,  password,  bundle,  firstName, lastName);
    }
    
    public abstract double getPrice(Movie movie);
    
    public double getTotalPrice(Movie movie, int nbTickets) //à implémenter (multiplier getPrice par le nombre de tickets achetés
    {
        double totalPrice = this.getPrice(movie)*nbTickets;
        return totalPrice;
    }
}
