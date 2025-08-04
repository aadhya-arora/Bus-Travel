package com.bus.crud;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class bus {
    Scanner sc=new Scanner(System.in);
     public void create(Connect connection)
     {
        try{
            System.out.println("Bus No. :");
            String bus_no=sc.next();
            System.out.println("Date (Format yyyy-MM-dd):");
            String date=sc.next();     
            LocalDate lDate=LocalDate.parse(date);
            Date sqlDate=Date.valueOf(lDate);
            System.out.println("Starting point :");
            String s_point=sc.next();
            System.out.println("Destination :");
            String d_point=sc.next();
            System.out.println("Amount :");
            double amt=sc.nextDouble();
            System.out.println("Time (Format 14:00) :");
            String time=sc.next();
            LocalTime lTime=LocalTime.parse(time);
            Time sqlTime = Time.valueOf(lTime);
            String query="Insert into buses (bus_no,date_booked,starting_point,destination_point,amount,time) values(?,?,?,?,?,?)";
            PreparedStatement pstat=connection.prepareStatement(query);
            pstat.setString(1, bus_no);
            pstat.setDate(2, sqlDate);      
            pstat.setString(3, s_point);
            pstat.setString(4, d_point);
            pstat.setDouble(5, amt);
            pstat.setTime(6, sqlTime);
            int rowsAffected=pstat.executeUpdate();
            if(rowsAffected>0)
            {
                int i=5;
                System.out.print("Creating..");
                while(i!=0)
                {
                    System.out.print(".");
                    Thread.sleep(450);
                    i--;
                }
                System.out.println("Created!");
            }
            else{
                System.out.println("There was an issue");
            }

        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
     }
}
