/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import Controller.Projection;
import static Model.JBDC.*;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author loisp
 */
public class ProjectionDB {

    public void getProjection() 
    {
        
    }

    public void updateProjection(Projection proj)
    {

    }   

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
            PreparedStatement insert = getDbConnection().prepareStatement("SELECT * FROM PROJECTIONS ORDER BY projectionDate");
            
            ResultSet result = insert.executeQuery();
            while (result.next())
            {
                int idProj = result.getInt("idProj");
                String movieProjected = result.getString("title");
                String projectionDate = result.getString("projectionDate");
                Time projectionHour = result.getTime("projectionHour");
                double discount = result.getDouble("discount");
                boolean availability = result.getBoolean("avaibility");
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

    public static void addDBProjection(String idProj, String projectionDate, String projectionHour, String movieProjected, boolean availibility) {

        Connection conn;
        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("INSERT INTO PROJECTIONS VALUES ('" + idProj + "','" + projectionDate + "', '" + projectionHour + "', '" + movieProjected + "'," + availibility + ")");

        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

      public static void deleteDBProjection(String projectionDate, String projectionHour, String movieProjected) {
        Connection conn;
        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("DELETE FROM PROJECTIONS WHERE projectionDate = '" + projectionDate + "' AND projectionHour = '" + projectionHour +  "' AND movieProjected = '" + movieProjected + "'");
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
      
     

}
