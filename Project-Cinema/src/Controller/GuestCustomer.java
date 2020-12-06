/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Model.MovieDB.getTicketPrice;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author charl
 */
public class GuestCustomer extends Customer {
    
    public GuestCustomer()
    {
        super("guest");
    }
    
    @Override
    public double getPrice1Ticket() 
    {
        try {
            Cinema cinema = new Cinema();
            double price = cinema.getTicketPrice();
            return price;
        } catch (SQLException ex) {
            Logger.getLogger(GuestCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    @Override
    public boolean isMember()
    {
        return false;
    }
}
