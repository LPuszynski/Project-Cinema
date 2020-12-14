/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import Model.EmployeeDB;

import java.sql.SQLException;

/**
 *classe pour un employé du cinéma
 * @author charl
 * 
 */
public class Employee {
    private String login ;
    private String password;
    private String firstName;
    private String lastName;
    
    public Employee(String login, String password, String fN, String lN)
    {
        this.login = login;
        this.password = password;
        this.firstName = fN;
        this.lastName = lN;
    }

    public static boolean callCheckEmployee(String login, String password) throws SQLException {
        return EmployeeDB.checkEmployee(login, password);
    }
    
}
