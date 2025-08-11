package com.bus.crud;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;
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
    {
        try {
            System.out.println("Customer Name:");
            String pass_name=sc.nextLine();
            System.out.println("Age:");
            int age=sc.nextInt();
             sc.nextLine(); 
            System.out.println("Starting point:");
            String s_point=sc.nextLine();
            System.out.println("Destination:");
            String d_point=sc.nextLine();
            System.out.println("For Date(Format yyyy-MM-dd):");
            String date=sc.next();     
            LocalDate lDate=LocalDate.parse(date);
            System.out.println("For Time:");
            String time=sc.next();
            LocalTime lTime=LocalTime.parse(time);
            Time sqlTime = Time.valueOf(lTime);
             Random rand = new Random();
        int bookingId;
        while (true) {
            bookingId = 100000 + rand.nextInt(900000); 
            String checkQuery = "SELECT 1 FROM customer WHERE booking_id = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setInt(1, bookingId);
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) { 
                break;
            }
        }
            String busQuery="Select amount,bus_no from buses where starting_point=? AND destination_point=? AND date_booked=? AND time=?";
            PreparedStatement busStat=connection.prepareStatement(busQuery);
            busStat.setString(1,s_point);
            busStat.setString(2, d_point);
            busStat.setDate(3, java.sql.Date.valueOf(lDate));
            busStat.setTime(4, sqlTime);
            ResultSet busSet=busStat.executeQuery();
            if(busSet.next())
            {
                double amount=busSet.getDouble("amount");
                String bus_no=busSet.getString("bus_no");

                String insertCustomer = "INSERT INTO customer (booking_id, name, age, starting_point, destination_point, date_booked, time_booked, bus_no, amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement custStat = connection.prepareStatement(insertCustomer);
            custStat.setInt(1, bookingId);
            custStat.setString(2, pass_name);
            custStat.setInt(3, age);
            custStat.setString(4, s_point);
            custStat.setString(5, d_point);
            custStat.setDate(6, java.sql.Date.valueOf(lDate));
            custStat.setTime(7, sqlTime);
            custStat.setString(8, bus_no);
            custStat.setDouble(9, amount);

            int rowsInserted = custStat.executeUpdate();
            if (rowsInserted > 0) {
                capacity(connection);
                System.out.println("Ticket booked successfully!");
                System.out.println("Booking ID: " + bookingId);
                System.out.println("Bus No: " + bus_no + " | Amount: " + amount);
            } else {
                System.out.println("Booking failed. Try again.");
            }
        } else {
            System.out.println("No buses available for the given details.");
        }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
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
    public void capacity(Connection connection)
    {}
}
