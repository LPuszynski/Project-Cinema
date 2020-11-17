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
    private String monthInLetters;

    public Date(long day, long month, long year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }
    
    public void monthInLetter()
    {
        if (month == 1)
        {
            monthInLetters = "January";
        }
        if (month == 2)
        {
            monthInLetters = "February";
        }
        if (month == 3)
        {
            monthInLetters = "March";
        }
        if (month == 4)
        {
            monthInLetters = "April";
        }
        if (month == 5)
        {
            monthInLetters = "May";
        }
        if (month == 6)
        {
            monthInLetters = "June";
        }
        if (month == 7)
        {
            monthInLetters = "July";
        }
        if (month == 8)
        {
            monthInLetters = "August";
        }
        if (month == 9)
        {
            monthInLetters = "September";
        }
        if (month == 10)
        {
            monthInLetters = "October";
        }
        if (month == 11)
        {
            monthInLetters = "November";
        }
        if (month == 12)
        {
            monthInLetters = "December";
        }
    }
    
   //faire sspm qui donne la date avec mars au lieu de 03
    public void display()
    {
        System.out.println(month + "/" + day + "/" + year);
    }
}
