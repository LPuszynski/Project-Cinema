/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.JBDC;
import Model.MemberCustomerDB;
import static Model.MemberCustomerDB.getCustomerListDB;
import java.util.ArrayList;
import static Model.MovieDB.getMovieListDB;
import Model.ProjectionDB;
import static Model.ProjectionDB.addDBProjection;
import Model.ReservationDB;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * cette classe sert a creer l'univers du projet
 * @author loisp
 */
public class Cinema {
    private double profits;
    private ArrayList<Movie> movieList;
    private ArrayList<Projection> projList;
    private ArrayList<Customer> customerList;
    private double ticketPrice;
    private String empLogin;
    private String custLogin;
    
    public Cinema() throws SQLException
    {
        //attributes
        ticketPrice = 10;
        this.profits = 0;
        this.movieList = new ArrayList<Movie>();
        this.movieList = getMovieListDB();
        this.customerList = getCustomerListDB();
        this.custLogin = null;
        this.empLogin = null;
        this.projList = ProjectionDB.getAllProjectionsDB(true);
        
        // Uncomment to generate random data for the last 3 months
        //generateData(); 
    }
    
    
    // all the getters
    public Movie getMovieInfos(String movieName)
    {
        for( int i=0 ; i<movieList.size() ; ++i ){
            if( movieList.get(i).getTitle().equals(movieName)){
                return movieList.get(i);
            }
        }
        return null;
    }
    public ArrayList<Movie> getMovieList()
    {
        return movieList;
    }
    
    public double getTicketPrice()
    {
        return ticketPrice;
    }
    
    public String getEmpLogin()
    {
        return empLogin;
    }

    public String getCustLogin() {
        return custLogin;
    }

    public void setCustLogin(String custLogin) {
        this.custLogin = custLogin;
    }

    public void setEmpLogin(String empLogin) {
        this.empLogin = empLogin;
    }

    public ArrayList<Projection> getProjList() {
        return projList;
    }
    
    public void refreshProjList(){
        this.projList = ProjectionDB.getAllProjectionsDB(true);
    }
    
    
    public MemberCustomer getMemberCustomer(String custName){
        try{
            return MemberCustomerDB.getMemberCustomerDB(custName);
            
        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /*
        Generates random data for the 6 movies for the last 3 months
            one projection of each movie every day
            random purchases of customers for each projection
    */
    public void generateData()
    {
        Date dt = new Date();
        Calendar start = Calendar.getInstance();
        start.setTime(dt);
        start.add(Calendar.MONTH, -3);
        Calendar end = Calendar.getInstance();
        end.setTime(dt);
        
        // iterates through dates
        for( Date d=start.getTime() ; start.before(end) ; start.add(Calendar.DATE, 1), d=start.getTime() ){
            String strDate = new SimpleDateFormat("yyyy-MM-dd").format(d);
            // generates a projection for each movie
            for( int i=0 ; i<movieList.size() ; ++i ){
                int iNewProj = addDBProjection(strDate, "10:00",movieList.get(i).getTitle() , 0, false);
                if( iNewProj!=0){
                    Random rg = new Random();
                    int ticg = rg.nextInt(20)+1;
                    // add random purchases for guest - full price
                    if( ReservationDB.addDBReservation(iNewProj,"guest",ticg,ticg*10) );
                    // add random purchases for each registered customer with discount
                    for( int j=0 ; j<customerList.size() ; ++j ){
                        Random r = new Random();
                        int tic = r.nextInt(10) + 1;
                        if( tic == 1 ){
                            tic = r.nextInt(4) + 1;
                            ReservationDB.addDBReservation(iNewProj,customerList.get(j).login,tic,tic*10*(1-customerList.get(i).discount));
                        }
                    }
                    // pause between each projection
                    try{
                        Thread.sleep(1000);
                    } catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

}