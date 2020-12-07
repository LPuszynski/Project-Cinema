/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.ArrayList;
import Model.MovieDB;
import static Model.MovieDB.getMovieListDB;
import Model.ProjectionDB;
import java.sql.SQLException;
/**
 *
 * @author loisp
 */
public class Cinema {
    private double profits;
    private ArrayList<Movie> movieList;
    private ArrayList<Projection> projList;
    private double ticketPrice;
    private String empLogin;
    private String custLogin;
    
    public Cinema() throws SQLException
    {
        ticketPrice = 10;
        this.profits = 0;
        this.movieList = new ArrayList<Movie>();
        this.movieList = getMovieListDB();
        this.custLogin = null;
        this.empLogin = null;
        this.projList = ProjectionDB.getAllProjectionsDB(true);
        
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
    
    
    
    
    
   
    }
