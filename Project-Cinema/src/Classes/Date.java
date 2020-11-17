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
    private long day;
    private long month;
    private long year;

    public Date(long day, long month, long year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }
    
   //faire sspm qui donne la date avec mars au lieu de 03
    public display()
    {
        System.out.println(month + " " + day + "," + year);
    }
}
