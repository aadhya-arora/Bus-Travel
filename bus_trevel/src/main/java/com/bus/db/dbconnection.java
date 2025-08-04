package com.bus.db;
import java.sql.*;
public class dbconnection {
    public static final url="jdbc:mysql://localhost:3306/bus_travelling";
    public static final username="root";
    public static final password="aadhya";
    static{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
            return;
        }
    }
    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(url, username, password);
    }
}
