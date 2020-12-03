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
    private String pass; ///forfait : children, regular, senior
    private double discount;

    public MemberCustomer(String login, String password, String pass) {
        this.login = login;
        this.password = password;
        this.pass = pass;
    }
    
    public void setDiscount()
    {
        if(pass.equals("children"))
        {
            discount = 0.2;
        }
        
        else if (pass.equals("regular"))
        {
            discount = 0.15;
        }
        
        else if (pass.equals("senior"))
        {
            discount = 0.1;
        }
    }
    
    
    
    @Override
    public double getPrice(Movie movie){
        return movie.getTicketPrice()-movie.getTicketPrice()*discount;
    }
    
}
