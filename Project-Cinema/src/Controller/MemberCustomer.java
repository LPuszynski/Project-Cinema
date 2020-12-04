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
public class MemberCustomer extends Customer {
    private String login;
    private String password;
    private String bundle; ///forfait : children, regular, senior
    private String firstName;
    private String lastName;
    private double discount;

    public MemberCustomer() {
    }

    public MemberCustomer(String login, String password, String bundle, String firstName, String lastName) {
        this.login = login;
        this.password = password;
        this.bundle = bundle;
        this.firstName = firstName;
        this.lastName = lastName;
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
    
    
    
    @Override
    public double getPrice(Movie movie){
        return movie.getTicketPrice()-movie.getTicketPrice()*discount;
    }
    
    
    
}
