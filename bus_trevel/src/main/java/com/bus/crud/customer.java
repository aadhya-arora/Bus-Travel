package com.bus.crud;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;
public class customer {
    Scanner sc=new Scanner(System.in);
    
    public void choose(Connection connection)
    {
        while(true)
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
}
    public void book(Connection connection)
    {
        try {
            System.out.println("Customer Name:");
            sc.nextLine();
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
            String busQuery="Select amount,bus_no from buses where starting_point=? AND destination_point=? AND date_booked=?";
            PreparedStatement busStat=connection.prepareStatement(busQuery);
            busStat.setString(1,s_point);
            busStat.setString(2, d_point);
            busStat.setDate(3, java.sql.Date.valueOf(lDate));
           ResultSet busSet=busStat.executeQuery();
            if(busSet.next())
            {
                double amount=busSet.getDouble("amount");
                String bus_no=busSet.getString("bus_no");

                String insertCustomer = "INSERT INTO customer (booking_id, passenger_name, age, starting_point, destination_point, date_booked, bus_no, amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement custStat = connection.prepareStatement(insertCustomer);
            custStat.setInt(1, bookingId);
            custStat.setString(2, pass_name);
            custStat.setInt(3, age);
            custStat.setString(4, s_point);
            custStat.setString(5, d_point);
            custStat.setDate(6, java.sql.Date.valueOf(lDate));
            
            custStat.setString(7, bus_no);
            custStat.setDouble(8, amount);
            if(capacity(connection,bus_no))
            {
                int rowsInserted = custStat.executeUpdate();
                if (rowsInserted > 0) {
                
                System.out.println("Ticket booked successfully!");
                System.out.println("Booking ID: " + bookingId);
                System.out.println("Bus No: " + bus_no + " | Amount: " + amount);
            } 
            }
            else {
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
    {
        System.out.print("Displaying all buses..");
        try{
            int i=5;
            while(i!=0)
            {
                System.out.print(".");
                Thread.sleep(450);
                i--;
            }
            System.out.println();
            String query="Select bus_no,date_booked,capacity,starting_point,destination_point,amount,status,time from buses";
            PreparedStatement pstat=connection.prepareStatement(query);
            ResultSet rSet=pstat.executeQuery();
            while(rSet.next())
            {
                String bus_no=rSet.getString("bus_no");
                Date date_booked=rSet.getDate("date_booked");
                int capacity=rSet.getInt("capacity");
                String start=rSet.getString("starting_point");
                String dest=rSet.getString("destination_point");
                double amt=rSet.getDouble("amount");
                String status=rSet.getString("status");
                Time time=rSet.getTime("time");
                System.out.println(bus_no+"     |     "+date_booked+"    |    "+capacity+"   |   "+start+" to "+dest+"       |    "+amt+"   |   "+status+"   |   "+time);
            }
        }
        catch(InterruptedException e)
        {
            System.out.println(e.getMessage());
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void filter(Connection connection)
    {
        System.out.println("Filter acc to:");
        System.out.println("1. Capacity");
        System.out.println("2. Destination");
        System.out.println("3. Departure");
        System.out.println("4. Status");
        System.out.println("5. Amount");
        System.out.println("0. Exit");
        int choice=sc.nextInt();
        sc.nextLine();
        switch(choice)
        {
           
            case 1:
            fetch_capacity(connection);
            break;
            case 2:
            fetch_destination(connection);
            break;
            case 3:
            fetch_departure(connection);
            break;
            case 4:
            fetch_status(connection);
            break;
            case 5:
            fetch_amount(connection);
            break;
            case 0:
            return;
            default:
            System.out.println("Wrong choice");
            break;
        }
    }
     public void fetch_capacity(Connection connection)
     {
        
        System.out.println("Capacity ?");
        int cap=sc.nextInt();
        System.out.print("Filtering acc to capacity..");
        try{
            int i=5;
            while(i!=0)
            {
                System.out.print("..");
                Thread.sleep(450);
                i--;
            }
            System.out.println();
            String query="Select bus_no,date_booked,starting_point,destination_point,amount,status,time from buses where capacity>=?";
            PreparedStatement pstat=connection.prepareStatement(query);
            pstat.setInt(1, cap);
            ResultSet rSet=pstat.executeQuery();
              boolean found = false; 
            while(rSet.next())
            {
                found=true;
                String bus_no=rSet.getString("bus_no");
                Date date_booked=rSet.getDate("date_booked");
                String start=rSet.getString("starting_point");
                String dest=rSet.getString("destination_point");
                double amt=rSet.getDouble("amount");
                String status=rSet.getString("status");
                Time time=rSet.getTime("time");
                System.out.println(bus_no+"     |     "+date_booked+"   |   "+start+"     |     "+dest+"    |    "+amt+"   |   "+status+"   |   "+time);
            }
              if (!found) {
            System.out.println("No such bus found");
        }
        }
        catch(InterruptedException e)
        {
            System.out.println(e.getMessage());
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
     }
     public void fetch_destination(Connection connection) {
        System.out.println("Destination ?");
        String dest=sc.nextLine();
        System.out.print("Filtering acc to destination..");
        try {
             int i=5;
            while(i!=0)
            {
                System.out.print("..");
                Thread.sleep(450);
                i--;
            }
            System.out.println();
            String query="Select * from buses where destination_point=?";
            PreparedStatement pstat=connection.prepareStatement(query);
            pstat.setString(1, dest);
            ResultSet rSet=pstat.executeQuery();
            boolean found=false;
            while(rSet.next())
            {
                found=true;
                String bus_no=rSet.getString("bus_no");
                Date date_booked=rSet.getDate("date_booked");
                String start=rSet.getString("starting_point");
                String dest2=rSet.getString("destination_point");
                double amt=rSet.getDouble("amount");
                String status=rSet.getString("status");
                Time time=rSet.getTime("time");
                int cap=rSet.getInt("capacity");
                System.out.println(bus_no+"     |     "+date_booked+"   |   "+start+"     |     "+dest2+"    |    "+cap+"    |    "+amt+"   |   "+status+"   |   "+time);
            }
              if (!found) {
            System.out.println("No such bus found");
        }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     }
     public void fetch_departure(Connection connection) {
        System.out.println("Departure from?");
        String start=sc.nextLine();
        System.out.print("Filtering acc to departure point..");
        try {
             int i=5;
            while(i!=0)
            {
                System.out.print("..");
                Thread.sleep(450);
                i--;
            }
            System.out.println();
            String query="Select * from buses where starting_point=?";
            PreparedStatement pstat=connection.prepareStatement(query);
            pstat.setString(1, start);
            ResultSet rSet=pstat.executeQuery();
            boolean found=false;
            while(rSet.next())
            {
                found=true;
                String bus_no=rSet.getString("bus_no");
                Date date_booked=rSet.getDate("date_booked");
                String start2=rSet.getString("starting_point");
                String dest=rSet.getString("destination_point");
                double amt=rSet.getDouble("amount");
                String status=rSet.getString("status");
                Time time=rSet.getTime("time");
                int cap=rSet.getInt("capacity");
                System.out.println(bus_no+"     |     "+date_booked+"   |   "+start2+"     |     "+dest+"    |    "+cap+"    |    "+amt+"   |   "+status+"   |   "+time);
            }
              if (!found) {
            System.out.println("No such bus found");
        }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     }
     public void fetch_status(Connection connection) {
        System.out.println("Status (functioning or in repair)?");
        String stat=sc.nextLine();
        System.out.print("Filtering acc to status..");
        try {
             int i=5;
            while(i!=0)
            {
                System.out.print("..");
                Thread.sleep(450);
                i--;
            }
            System.out.println();
            String query="Select * from buses where status=?";
            PreparedStatement pstat=connection.prepareStatement(query);
            pstat.setString(1, stat);
            ResultSet rSet=pstat.executeQuery();
            boolean found=false;
            while(rSet.next())
            {
                found=true;
                String bus_no=rSet.getString("bus_no");
                Date date_booked=rSet.getDate("date_booked");
                String start=rSet.getString("starting_point");
                String dest=rSet.getString("destination_point");
                double amt=rSet.getDouble("amount");
                String status=rSet.getString("status");
                Time time=rSet.getTime("time");
                int cap=rSet.getInt("capacity");
                System.out.println(bus_no+"     |     "+date_booked+"   |   "+start+"     |     "+dest+"    |    "+cap+"    |    "+amt+"   |   "+status+"   |   "+time);
            }
              if (!found) {
            System.out.println("No such bus found");
        }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

     }
     public void fetch_amount(Connection connection) {
        System.out.println("Range ?");
        int amount=sc.nextInt();
        System.out.print("Filtering acc to amount..");
        try {
             int i=5;
            while(i!=0)
            {
                System.out.print("..");
                Thread.sleep(450);
                i--;
            }
            System.out.println();
            String query="Select * from buses where amount<=?";
            PreparedStatement pstat=connection.prepareStatement(query);
            pstat.setInt(1, amount);
            ResultSet rSet=pstat.executeQuery();
            boolean found=false;
            while(rSet.next())
            {
                found=true;
                String bus_no=rSet.getString("bus_no");
                Date date_booked=rSet.getDate("date_booked");
                String start=rSet.getString("starting_point");
                String dest=rSet.getString("destination_point");
                double amt=rSet.getDouble("amount");
                String status=rSet.getString("status");
                Time time=rSet.getTime("time");
                int cap=rSet.getInt("capacity");
                System.out.println(bus_no+"     |     "+date_booked+"   |   "+start+"     |     "+dest+"    |    "+cap+"    |    "+amt+"   |   "+status+"   |   "+time);
            }
              if (!found) {
            System.out.println("No such bus found");
        }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     }
    public void cancel(Connection connection)
    {
        System.out.println("Booking id for the reservation to cancel:");
        int booking_id=sc.nextInt();
        try{
            String fetchBusQuery = "SELECT bus_no FROM customer WHERE booking_id = ?";
            PreparedStatement fetchStmt = connection.prepareStatement(fetchBusQuery);
            fetchStmt.setInt(1, booking_id);
            ResultSet rs = fetchStmt.executeQuery();
            String bus_no = null;
            if (rs.next()) {
                bus_no = rs.getString("bus_no");
            } else {
            System.out.println("No such booking found.");
            return;
            }
            String query="Delete from customer where booking_id=?";
            PreparedStatement pstat=connection.prepareStatement(query);
            pstat.setInt(1, booking_id);
            int rSet=pstat.executeUpdate();
            if(rSet>0)
            {
                capacity_increase(connection,bus_no);
                System.out.print("Deleting..");
                int i=5;
                while(i!=0)
                {
                    System.out.print("..");
                    Thread.sleep(450);
                    i--;
                    System.out.println();
                }
                System.out.println("Your reservation successfully cancelled");
            }
            else{
                System.out.println("No such bus found");
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void booked(Connection connection)
    {
        try {
            System.out.println("Passenger Name:");
            sc.nextLine();
            String pass_name=sc.nextLine();
            String query="Select * from customer where passenger_name=?";
            PreparedStatement pstat=connection.prepareStatement(query);
            pstat.setString(1, pass_name);
            ResultSet rSet=pstat.executeQuery();
            while(rSet.next())
            {
                int cust_id=rSet.getInt("customer_id");
                Date date_booked=rSet.getDate("date_booked");
                double amt=rSet.getDouble("amount");
                String start=rSet.getString("starting_point");
                String dest=rSet.getString("destination_point");
                String bus_no=rSet.getString("bus_no");
                int booking_id=rSet.getInt("booking_id");
                System.out.println(cust_id+"     |     "+date_booked+"   |   "+amt+"     |     "+start+"    |    "+dest+"    |    "+bus_no+"   |   "+booking_id+"   |");
            
            }
        } catch (Exception e) {
           System.out.println(e.getMessage());
        }
    }
    public void viewBooked(Connection connection)
    {
        try {
            System.out.println("Booking Id:");
            int book_id=sc.nextInt();
            String query="Select * from customer where booking_id=?";
            PreparedStatement pstat=connection.prepareStatement(query);
            pstat.setInt(1, book_id);
            ResultSet rSet=pstat.executeQuery();
            while(rSet.next())
            {
                int cust_id=rSet.getInt("customer_id");
                String pass=rSet.getString("passenger_name");
                Date date_booked=rSet.getDate("date_booked");
                double amt=rSet.getDouble("amount");
                String start=rSet.getString("starting_point");
                String dest=rSet.getString("destination_point");
                String bus_no=rSet.getString("bus_no");
                System.out.println(cust_id+"     |     "+pass+"     |     "+date_booked+"   |   "+amt+"     |     "+start+"    |    "+dest+"    |    "+bus_no+"   |   "+book_id+"   |");
            
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    } 
    public boolean capacity(Connection connection,String bus_no)
    {
        try {
            String query="Update buses set capacity=capacity-1 where bus_no=? AND capacity > 0";
            PreparedStatement pstat=connection.prepareStatement(query);
            pstat.setString(1, bus_no);
            int rowsAffected=pstat.executeUpdate();
            if(rowsAffected>0)
            {
                return true;
            }
            else
            {
                return false;
            }
        } catch (Exception e) {
           System.out.println(e.getMessage());
        }
        return false;
    }
    public void capacity_increase(Connection connection,String bus_no)
    {
        try {
             String query="Update buses set capacity=capacity+1 where bus_no=? AND capacity < 70";
            PreparedStatement pstat=connection.prepareStatement(query);
            pstat.setString(1, bus_no);
            pstat.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
