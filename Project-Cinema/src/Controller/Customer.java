/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author charl
 */
public abstract class  Customer {
    
    public abstract double getPrice(Movie movie);
    
    public double getTotalPrice() //à implémenter (multiplier getPrice par le nombre de tickets achetés
    {
        return 0;
    }
}
