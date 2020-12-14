/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import static Model.JBDC.getDbConnection;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;

/**
 *
 * @author charl
 */
public class ReservationDB {

    public static void initDBReservation() {

        Connection conn;

        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("CREATE TABLE IF NOT EXISTS `RESERVATION` ("
                    + "  `idProj` int NOT NULL,"
                    + "  `login` varchar(100) NOT NULL,"
                    + "  `price` double NOT NULL,"
                    + "  `quantity` int NOT NULL,"
                    + "  `quand`datetime NOT NULL DEFAULT CURRENT_STAMP "
                    + "  PRIMARY KEY (`idProj`,`login`)"
                    + ")");
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /*
        Get number of reservation per login
    */
    public static int getQuantityDB(String login, int idProj) throws SQLException {
        PreparedStatement insert = getDbConnection().prepareStatement("select quantity from RESERVATION" + " where login = ? + and idProj = ?");
        insert.setString(1, login);
        insert.setInt(2, idProj);

        ResultSet result = insert.executeQuery();

        if (result.first()) {
            int q = result.getInt("quantity");
            return q;
        }
        return 0;
    }

    /*
        Get number of all reservations for a specific projection
    */
    public static int getAllQuantityDB(int idProj) throws SQLException {
        PreparedStatement insert = getDbConnection().prepareStatement("select Sum(quantity) AS n from RESERVATION" + " where idProj = ?");
        insert.setInt(1, idProj);

        ResultSet result = insert.executeQuery();

        if (result.first()) {
            int q = result.getInt("n");
            return q;
        }
        return 0;
    }

    /*
        Get all reservation baught by a customer
    */
    public static String[][] getAllReservationForCustomerDB(String custName) throws SQLException 
    {

        try {
            int nbRows;
            String[][] tabData;

            PreparedStatement insert = getDbConnection().prepareStatement("SELECT p.title, p.projectiondate, p.projectionHour, r.quantity, r.price, r.quand FROM reservation r, projections p WHERE p.idproj=r.idproj AND login=? ORDER BY projectionDate, projectionHour",ResultSet.TYPE_SCROLL_INSENSITIVE);
            insert.setString(1, custName);
            ResultSet result = insert.executeQuery();
            ResultSetMetaData resultMeta = result.getMetaData();
            
            // gets the data
            result.last();
            nbRows = result.getRow();
            result.first();
            
            tabData = new String[nbRows][resultMeta.getColumnCount()];
            
            for( int row=0 ; row<nbRows ; row++ ){
                for( int col=0 ; col<resultMeta.getColumnCount() ; col++ ){
                    tabData[row][col]=result.getString(col+1);
                }
                result.next();
            }
            
            return tabData;

        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /*
        Get all purchases for a specific movie
    */
    public static XYSeries getAllReservationForMovieDB(String movieTitle)
    {
        XYSeries xys = new XYSeries(movieTitle);
        //https://stackoverrun.com/fr/q/9284060
        try {
            PreparedStatement insert = getDbConnection().prepareStatement(
                "select date_format(jours.jour,\"%Y%m%d\") as Day, "
                +"(select sum(r.price) from reservation r, projections p where r.idproj=p.idProj and p.projectionDate=jours.jour and p.title=?) as ? "
                +"from (select a.Date as jour from ("
                +"select curdate() - INTERVAL (a.a + (10 * b.a) + (100 * c.a) + (1000 * d.a) ) DAY as Date "
                +"from (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as a "
                +"cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as b "
                +"cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as c "
                +"cross join (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) as d "
                +") as a where a.Date between date_sub(CURRENT_DATE,INTERVAL 3 MONTH) and CURRENT_DATE) as jours "
                +"order by jours.jour",ResultSet.TYPE_SCROLL_INSENSITIVE);
            insert.setString(1, movieTitle);
            insert.setString(2, movieTitle);
            ResultSet result = insert.executeQuery();

            result.first();
            
            do{
                xys.add(result.getInt(1),result.getInt(2));
            }while( result.next() );
            
        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return xys;
    }
    
    /*
        Get all purchases per user
    */
    public static DefaultPieDataset getAllReservationPerUsers()
    {
        DefaultPieDataset ds = new DefaultPieDataset();
        
        try {
            PreparedStatement insert = getDbConnection().prepareStatement(
                "select login, sum(price) as purchase from reservation group by login order by sum(price) desc");
            ResultSet result = insert.executeQuery();

            result.first();
            
            do{
                ds.setValue(result.getString(1),result.getInt(2));
            }while( result.next() );
            
        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ds;
    }

    public static void setElementReservationDB(int newValeur, String typeKey, String myKey, String type2Key, String my2Key) {
        Connection conn;

        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("UPDATE RESERVATION"
                    + "  set quantity = '" + newValeur + "' WHERE " + typeKey + " = '" + myKey + "' AND " + type2Key + " = '" + my2Key + "'");
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean addDBReservation(int idProj, String login, int nbOfTickets, double price) {

        Connection conn;
        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("INSERT INTO RESERVATION (idproj, login,quantity, price) VALUES ('" + idProj + "','" + login + "', '" + nbOfTickets + "', '" + price + "')");
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public static void deleteDBReservation(String login, int idProj) {
        Connection conn;
        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("DELETE FROM RESERVATION WHERE login = '" + login + "' AND idProj = '" + idProj + "'");
        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
