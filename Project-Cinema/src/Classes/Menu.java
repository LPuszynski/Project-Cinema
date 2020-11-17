/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.sql.*;

/**
 *
 * @author charl
 */
public class Menu {
<<<<<<< Updated upstream

    public static void main(String[] args) {
        Date date = new Date(10, 9, 2000);
        date.display();
=======
    
    public static void main(String[] args) throws SQLException
    {
        //https://www.youtube.com/watch?v=54mszeznwtc
        Connection conn = null;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jbdc:mysql://localhost:3306/test";
            String user = "root";
            String password = "" ;      
            
            /*conn = DriveManager.getConnection(url,user, password);
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE ,ResultSet.CONCUR_READ_ONLY);
            
            ResultSet resultSet = stmt.executeQuery("Select * from movies");
            while (resultSet.next())
            {
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
            }*/
        }
        catch(Exception e )
        {
            System.out.println("pb connection database");
        }
>>>>>>> Stashed changes
        
    }
    
}
