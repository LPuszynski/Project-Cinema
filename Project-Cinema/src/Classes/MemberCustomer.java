/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author charl
 */
public class MemberCustomer extends Customer {
    private String login;
    private String password;
    private String pass; ///forfait : children, regular, senior
    
    @Override
    public double getPrice(){return 0;}
    
}
