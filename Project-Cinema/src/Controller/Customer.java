/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import Model.*;


/**
 *classe mère de client
 * @author charl
 */
public abstract class Customer {
    protected String login;
    protected double discount;    
    
    public Customer(String login) 
    {
        this.login = login;
        this.discount = 0;
    }
    
    public Customer()
    {
        
    }
    //getters
    public String getLogin()
    {
        return login;
    }

    public double getDiscount() {
        return discount;
    }
    
    
    public abstract double getPrice1Ticket();
    public abstract boolean isMember();
   
}
