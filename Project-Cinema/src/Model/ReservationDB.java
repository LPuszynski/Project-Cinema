/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Reservation;
import static Model.JBDC.getDbConnection;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                    + "  PRIMARY KEY (`idProj`,`login`)"
                    + ")");
        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

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

    public static void setElementReservationDB(int newValeur, String typeKey, String myKey, String type2Key, String my2Key) {
        Connection conn;

        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("UPDATE RESERVATION"
                    + "  set quantity = '" + newValeur + "' WHERE " + typeKey + " = '" + myKey + "' AND " + type2Key + " = '" + my2Key + "'");

        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addDBReservation(int idProj, String login, int nbOfTickets, double price) {

        Connection conn;
        try {
            conn = (Connection) getDbConnection();
            Statement essai = conn.createStatement();
            essai.execute("INSERT INTO RESERVATION (idproj, login,quantity, price) VALUES ('" + idProj + "','" + login + "', '" + nbOfTickets + "', '" + price + "')");

        } catch (SQLException ex) {
            Logger.getLogger(JBDC.class.getName()).log(Level.SEVERE, null, ex);
        }
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
