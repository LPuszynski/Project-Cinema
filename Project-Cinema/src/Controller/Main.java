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
public class Main {
    public static void main(String[] args)
    {
        
        
        
        
        MemberCustomer cust = new MemberCustomer();
        Movie mov = new Movie();
       
        System.out.println(cust.getPrice(mov));
    }
}
