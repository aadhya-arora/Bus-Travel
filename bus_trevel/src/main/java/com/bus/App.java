package com.bus;

import com.bus.db.dbconnection;
import java.sql.Connection;
import com.bus.crud.bus;
public class App 
{
    public static void main( String[] args )
    {
        try {
            Connection con=dbconnection.getConnection();
            bus busService=new bus();
            busService.choose(con);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
