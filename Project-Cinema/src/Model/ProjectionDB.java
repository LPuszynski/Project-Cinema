/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import Controller.Movie;
import Controller.Projection;
import static Model.JBDC.*;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *manipule les projections en db
 * @author loisp
 */
public class ProjectionDB {

    public void getProjection() 
    {
        
    }

    public void updateProjection(Projection proj)
    {

    }   
    //test function
    public static void initDBProjections() {

        Connection conn;

        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("CREATE TABLE IF NOT EXISTS `PROJECTIONS` ("
                    + "  `idProj` varchar(100) NOT NULL,"
                    + "  `movieProjected` varchar(100) NOT NULL,"
                    //+ "  `` varchar(100) NOT NULL,"
                    + "  `projectionDate` varchar(100) NOT NULL,"
                    + "  `projectionHour` time NOT NULL,"
                    //+ "  `numberOfSeats` int NOT NULL,"
                    + "  `availability` int NOT NULL,"       
                    + "  PRIMARY KEY (`idProj`)"
                    + ")");
        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ArrayList<Projection> getAllProjectionsDB(boolean AvailOnly)
    {
        ArrayList<Projection> projList = new ArrayList<Projection>();
        try{
            PreparedStatement insert = getDbConnection().prepareStatement("SELECT * FROM PROJECTIONS ORDER BY projectionDate desc, ProjectionHour");
            
            ResultSet result = insert.executeQuery();
            while (result.next())
            {
                int idProj = result.getInt("idProj");
                String movieProjected = result.getString("title");
                String projectionDate = result.getString("projectionDate");
                Time projectionHour = result.getTime("projectionHour");
                double discount = result.getDouble("discount");
                boolean availability = result.getBoolean("availability");
                if( (availability && AvailOnly) || !AvailOnly ){
                    Projection proj = new Projection(idProj, projectionDate,projectionHour , movieProjected, discount, availability);
                    projList.add(proj);
                }
            }
        }catch(SQLException ex){
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return projList;
        
    }

    public static int addDBProjection(String projectionDate, String projectionHour, String movieProjected, int discount, boolean availibility) {

        Connection conn;
        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("INSERT INTO PROJECTIONS (projectionDate,projectionHour,title,discount, availability) VALUES ('" + projectionDate + "', '" + projectionHour + "', '" + movieProjected + "',"+discount+"," + availibility + ")");
            
            // get idProj generated before
            PreparedStatement insert = getDbConnection().prepareStatement("SELECT idProj FROM PROJECTIONS where projectionDate=? AND projectionHour=? AND title=?");
            insert.setString(1, projectionDate);
            insert.setString(2, projectionHour);
            insert.setString(3, movieProjected);
            
            ResultSet result = insert.executeQuery();
            if( result.next() )
            {
                return result.getInt("idProj");
            }
            
            conn.close();

        } catch (SQLException ex) {
           Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);

        }
        return 0;
    }

      public static void deleteDBProjection(int idProj) {
        Connection conn;
        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("DELETE FROM PROJECTIONS WHERE idProj = '" + idProj  + "'");
        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
       public static int GetDBNumberOfOccupedPlaces(String projectionDate, String projectionHour, String movieProjected) {
        Connection conn;
        int placesOccuped=0;
        ResultSet rs;
        try {
            conn = (Connection) getDbConnection();
            
            Statement essai = conn.createStatement();
            
            rs = essai.executeQuery("SELECT COUNT(*) from PROJECTIONS WHERE projectionDate = '" + projectionDate + "' AND projectionHour = '" + projectionHour +  "' AND movieProjected = '" + movieProjected + "'");
            placesOccuped = rs.getInt("COUNT(*)");
        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }  
        return placesOccuped;
    }
      
    public static void setDiscountDB(int idProj, double newDiscount) throws SQLException {
        Connection conn;

        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("UPDATE PROJECTIONS set discount = '" + newDiscount + "' WHERE idProj = '" + idProj + "'");

        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void setAvailabilityDB(int idProj, boolean newAvailability) throws SQLException {
        Connection conn;

        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("UPDATE PROJECTIONS set availability = " + newAvailability + " WHERE idProj = '" + idProj + "'");

        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
