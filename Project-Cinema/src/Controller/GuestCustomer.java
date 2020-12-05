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
    
    @Override
    public double getPrice1Ticket( Cinema cinema)
    {
        double price = cinema.getTicketPrice();
        return price;
    }
}
