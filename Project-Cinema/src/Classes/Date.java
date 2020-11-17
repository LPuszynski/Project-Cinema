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
public class Date {
    private int day;
    private int month;
    private int year;
    
   //faire sspm qui donne la date avec mars au lieu de 03
    String display()
    {
        return (month + " " + day + "," + year);
    }
}
