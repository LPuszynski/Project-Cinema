 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.JBDC;
import Model.MemberCustomerDB;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author charl
 */
public class MemberCustomer extends Customer {
    private String login;
    private String password;
    private String bundle; ///forfait : children, regular, senior
    private String firstName;
    private String lastName;
    private double discount;
    private ArrayList<Reservation> ReservationList;

    public MemberCustomer() {
    }

    public MemberCustomer(String login, String password, String bundle, String firstName, String lastName) {
        this.login = login;
        this.password = password;
        this.bundle = bundle;
        this.firstName = firstName;
        this.lastName = lastName; 
        ReservationList = new ArrayList<Reservation>();
        this.setDiscount();
    }

    
    public void setDiscount()
    {
        if(bundle.equals("children"))
        {
            discount = 0.2;
        }
        
        else if (bundle.equals("regular"))
        {
            discount = 0.15;
        }
        
        else if (bundle.equals("senior"))
        {
            discount = 0.1;
        }
    }
    
    public void addMemberCustomer() throws SQLException
    {
        //ckeck if the customer alrealdy exists in the DB
        boolean alreadyExists = false;
        
        ArrayList<String> allLogins = new ArrayList<String> ();
        allLogins = MemberCustomerDB.getAllLogin();
        
        for (int i = 0;i<allLogins.size();i++)
        {
            if (allLogins.get(i).equals(login))
            {
                alreadyExists = true;
            }
        }
        
        if (alreadyExists)
        {
            System.out.println("This customer cant be added because the login already exists. Please change your login ");
        }
        else{
            MemberCustomerDB.addDBCustomer(login, password, bundle, firstName, lastName);
        }
        
    }
    
    public void deleteMemberCustomer()
    {
        JBDC.deleteDBLineHuman(login,"CUSTOMER");
    }
    
    
    @Override
    public double getPrice1Ticket(Cinema cinema){
        return cinema.getTicketPrice()-cinema.getTicketPrice()*discount;
    }
    
    public void afficherMemberCustomer()
    {
        System.out.println("login : " + login);
        System.out.println("password : " + password);
        System.out.println("bundle : " + bundle);
        System.out.println("firstName : " + firstName);
        System.out.println("lastName : " + lastName);
        System.out.println("discount : " + discount);
        System.out.println("");
    }
    
}
