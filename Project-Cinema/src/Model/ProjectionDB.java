/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import Controller.Projection;
import static Model.JBDC.*;
import com.mysql.jdbc.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author loisp
 */
public class ProjectionDB {

    public void getProjection() // à completer et renvoyer une seance en particulier
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
                    + "  `movieProjected` varchar(100) NOT NULL,"
                    //+ "  `` varchar(100) NOT NULL,"
                    + "  `projectionDate` date NOT NULL,"
                    + "  `projectionHour` time NOT NULL,"
                    + "  `numberOfSeats` int NOT NULL,"
                    + "  `numberOfFreeSeats` int NOT NULL,"

                    + "  PRIMARY KEY (`movieProjected`,`projectionDate`,`projectionHour`)"
                    + ")");
        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addDBProjection(String projectionDate, String projectionHour, int numberOfSeats, int numberOfFreeSeats, String movieProjected) {

        Connection conn;
        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("INSERT INTO PROJECTIONS VALUES ('" + projectionDate + "','" + projectionHour + "', '" + numberOfSeats + "', '" + numberOfFreeSeats + "'," + movieProjected + ")");

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
