package com.bus.crud;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
public class customer {
    Scanner sc=new Scanner(System.in);
    public void choose(Connection connection)
    {
        System.out.println("-----------------");
        System.out.println("Welcome Customer!");
        System.out.println("-----------------");
        System.out.println("1. Book a ticket");
        System.out.println("2. Show all available buses");
        System.out.println("3. Filter buses");
        System.out.println("4. Cancel a ticket");
        System.out.println("5. View all reservations");
        System.out.println("6. View a specific reservation");
        System.out.println("0. Exit");
        int choice=sc.nextInt();
        switch(choice)
        {
            case 1:
                book(connection);
                break;
            case 2:
                viewAll(connection);
                break;
            case 3:
                filter(connection);
                break;
            case 4:
                cancel(connection);
                break;
            case 5:
                booked(connection);
                break;
            case 6:
                viewBooked(connection);
                break;
            case 0:
                return;
            default:
                System.out.println("Wrong choice");
                break;
        }
    }
    public void book(Connection connection)
    {}
    public void viewAll(Connection connection)
    {}
    public void filter(Connection connection)
    {}
    public void cancel(Connection connection)
    {}
    public void booked(Connection connection)
    {}
    public void viewBooked(Connection connection)
    {} 
}
