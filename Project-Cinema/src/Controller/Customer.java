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
    protected String login;
    
    public Customer(String login) 
    {
        this.login = login;
    }
    public Customer()
    {
        
    }
    public String getLogin()
    {
        return login;
    }
    
    
    
    public abstract double getPrice1Ticket();
    public abstract boolean isMember();
   
}
