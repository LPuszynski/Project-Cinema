/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//SOURCES : 
//https://www.mediaforma.com/java-acces-a-la-base-de-donnees-dans-netbeans/
//Lectures by Mme Paranjape

package Classes;

import java.sql.*;
import com.mysql.jdbc.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author charl
 */
public class Menu {

    public static void main(String[] args) {
        /*
        Date date = new Date(10, 9, 2000);
        date.display();
        
        Connection conn = null;
        String[] colNames;
        String[][] tableData;
        
        try{
            //on établit une connexion avec la base de données java
                    
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/projet?useSSL=false";
            String user = "root";
            String password = "" ;
            
            conn = (Connection) DriverManager.getConnection(url,user, password);
           
            //on crée un objet Statement. 
            //Par son intermédiaire, on pourra exécuter des commandes SQL pour interroger la base de données et obtenir les résultats correspondants.
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE ,ResultSet.CONCUR_READ_ONLY);
            
            // on définit une requête SQL en créant un objet ResultSet à partir de l’objet Statement
            ResultSet resultSet = stmt.executeQuery("Select * from film");
            
            resultSet.last(); //on va au dernier élément pour savoir la taille
            int nbRows = resultSet.getRow(); // il y a nbRows lignes dans la table
            resultSet.first(); //on remet le pointeur en début de table
            
            //on récupère les métadonnées de la table
            //Par leur intermédiaire, on pourra obtenir le nombre de colonnes dans la table avec getColumnCount() et le nom des colonnes avec getColumnName() :
            ResultSetMetaData resultMeta = resultSet.getMetaData();
            
            colNames = new String[resultMeta.getColumnCount()]; //on initialise autant de case dans le tableau qu'il y a de colonnes dans la table
            
            for(int i=0; i<resultMeta.getColumnCount(); i++)
            {
                //System.out.println(resultMeta.getColumnName(i+1));
                colNames[i] = resultMeta.getColumnLabel(i+1); //ou sinon utiliser getColumnName
            }

            //on pour récupère tous les enregistrements de la table :
            tableData = new String[nbRows][resultMeta.getColumnCount()]; //allouer de la place
            for (int row=0; row<nbRows; row++)
            {
                for(int col=0; col<resultMeta.getColumnCount(); col++)
                {
                    tableData[row][col] = resultSet.getString(col+1);
                }
                resultSet.next();
            }
            
            
            TableFormatter table = new TableFormatter(tableData, colNames);
            
            while (resultSet.next())
            {
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
            }
            
             //ferme les objets ouverts avec la méthode close() :
            resultSet.close();
            stmt.close();
            conn.close();
            
        }
        catch(SQLException | ClassNotFoundException e )
        {
            System.out.println(e.getMessage());
        }
        */
        initDB();
        
        
    }
    
    public static void initDB()
    {
        String url = "jdbc:mysql://localhost:3306/projet?useSSL=false";
            String user = "root";
            String password = "" ;
        Connection conn;
        
        try {
            conn = (Connection) DriverManager.getConnection(url,user, password);
            Statement essai = conn.createStatement();
            essai.execute("CREATE TABLE IF NOT EXISTS `doublegangdripkodak` (" +
"  `title` varchar(100) NOT NULL," +
"  `type` varchar(100) NOT NULL," +
"  `releaseDate` date NOT NULL," +
"  `runningTime` time NOT NULL," +
"  `ticketPrice` double NOT NULL," +
"  PRIMARY KEY (`title`,`type`,`releaseDate`,`runningTime`,`ticketPrice`)" +
")");
        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
