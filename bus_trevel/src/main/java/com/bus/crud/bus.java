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

public class bus {
    Scanner sc=new Scanner(System.in);
     public void create(Connection connection)
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
        catch(InterruptedException e)
        {
            System.out.println(e.getMessage());
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
     }
     public void fetchAll(Connection connection)
     {
        System.out.println("Displaying all buses..");
        try{
            int i=5;
            while(i!=0)
            {
                System.out.println(".");
                Thread.sleep(450);
                i--;
            }
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
                System.out.println(bus_no+"     |     "+date_booked+"    |    "+capacity+"   |   "+start+"     |     "+dest+"    |    "+amt+"   |   "+status+"   |   "+time);
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
     public void fetch(Connection connection)
     {
        System.out.println("Filter acc to:");
        System.out.println("1. Capacity");
        System.out.println("2. Destination");
        System.out.println("3. Departure");
        System.out.println("4. Status");
        System.out.println("5. Amount");
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
            
            String query="Select bus_no,date_booked,starting_point,destination_point,amount,status,time from buses where capacity>=?";
            PreparedStatement pstat=connection.prepareStatement(query);
            pstat.setInt(1, cap);
            ResultSet rSet=pstat.executeQuery();
            while(rSet.next())
            {
                String bus_no=rSet.getString("bus_no");
                Date date_booked=rSet.getDate("date_booked");
                String start=rSet.getString("starting_point");
                String dest=rSet.getString("destination_point");
                double amt=rSet.getDouble("amount");
                String status=rSet.getString("status");
                Time time=rSet.getTime("time");
                System.out.println(bus_no+"     |     "+date_booked+"   |   "+start+"     |     "+dest+"    |    "+amt+"   |   "+status+"   |   "+time);
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
        System.out.println("Destination to?");
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
            String query="Select * from buses where destination_point=?";
            PreparedStatement pstat=connection.prepareStatement(query);
            pstat.setString(1, dest);
            ResultSet rSet=pstat.executeQuery();
            while(rSet.next())
            {
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
            String query="Select * from buses where starting_point=?";
            PreparedStatement pstat=connection.prepareStatement(query);
            pstat.setString(1, start);
            ResultSet rSet=pstat.executeQuery();
            while(rSet.next())
            {
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
            String query="Select * from buses where status=?";
            PreparedStatement pstat=connection.prepareStatement(query);
            pstat.setString(1, stat);
            ResultSet rSet=pstat.executeQuery();
            while(rSet.next())
            {
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
            String query="Select * from buses where amount<=?";
            PreparedStatement pstat=connection.prepareStatement(query);
            pstat.setInt(1, amount);
            ResultSet rSet=pstat.executeQuery();
            while(rSet.next())
            {
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
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     }
     public void del_bus(Connection connection)
     {
        System.out.println("Enter the Bus No. :");
        String bus=sc.next();
        
        try {
            String query="Delete from buses where bus_no=?";
            PreparedStatement pstat=connection.prepareStatement(query);
            pstat.setString(1, bus);
            int rSet=pstat.executeUpdate();
            if(rSet>0)
            {
                System.out.print("Deleting..");
                int i=5;
                while(i!=0)
                {
                    System.out.print("..");
                    Thread.sleep(450);
                    i--;
                }
                System.out.println("Successfully Deleted");
            }
            else
            {
                System.out.println("No such bus");
            }
            
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     }

     public void update_bus(Connection connection)
     {
        System.out.println("What do you want to update ?");
        System.out.println("1. Capacity");
        System.out.println("2. Departure point");
        System.out.println("3. Destination point");
        System.out.println("4. Amount");
        System.out.println("5. Status");
        int choice=sc.nextInt();
        switch(choice)
        {
            case 1:
              update_capacity(connection);
              break;
            case 2:
              update_dept(connection);
              break;
            case 3:
              update_dest(connection);
              break;
            case 4:
              update_amt(connection);
              break;
            case 5:
              update_status(connection);
              break;
            default:
              System.out.println("Wrong choice");
              break;
        }
     }

     public void update_capacity(Connection connection)
     {
        try {
            System.out.println("Bus Id:");
            String bus_id=sc.next();
            System.out.println("Change capacity to :");
            int cap=sc.nextInt();
            String query="Update buses set capacity=? where bus_id=?";
            PreparedStatement pstat=connection.prepareStatement(query);
            pstat.setInt(1,cap);
            pstat.setString(2, bus_id);
            int rowsAffected=pstat.executeUpdate();
            if(rowsAffected>0)
            {
                int i=5;
                
                    System.out.print("Updating");
                while(i!=0)
                {
                    System.out.print("..");
                    Thread.sleep(450);
                    i--;
                }
                System.out.println("Capacity updated successfully");
            }
            else{
                System.out.println("Not updated");
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     }
     public void update_dept(Connection connection)
     {
        try{
            System.out.println("Bus Id:");
            sc.nextLine();
            String bus_id=sc.next();
            sc.nextLine();
            System.out.println("Change Departure point to:");
            String dept=sc.nextLine();
            String query="Update buses set starting_point=? where bus_id=?";
            PreparedStatement pstat=connection.prepareStatement(query);
            pstat.setString(1,dept);
            pstat.setString(2,bus_id);
            int rowsAffected=pstat.executeUpdate();
            if(rowsAffected>0)
            {
                int i=5;
                
                    System.out.print("Updating");
                while(i!=0)
                {
                    System.out.print("..");
                    Thread.sleep(450);
                    i--;
                }
                System.out.println("Departure point updated successfully");
            }
            else{
                System.out.println("Not updated");
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
     }
     public void update_dest(Connection connection)
     {
        try {
            System.out.println("Bus Id:");
            String bus_id=sc.next();
            sc.nextLine();
            System.out.println("Change Destination point to:");

            String dest=sc.nextLine();
            String query="Update buses set destination_point=? where bus_id=?";
            PreparedStatement pstat=connection.prepareStatement(query);
            pstat.setString(1,dest);
            pstat.setString(2,bus_id);
            int rowsAffected=pstat.executeUpdate();
            if(rowsAffected>0)
            {
                int i=5;
                
                    System.out.print("Updating");
                while(i!=0)
                {
                    System.out.print("..");
                    Thread.sleep(450);
                    i--;
                }
                System.out.println("Destination point updated successfully");
            }
            else{
                System.out.println("Not updated");
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     }

     public void update_amt(Connection connection)
     {
        try {
            System.out.println("Bus Id:");
            String bus_id=sc.next();
            System.out.println("Change amount to:");
            double amt=sc.nextDouble();
            String query="Update buses set amount=? where bus_id=?";
            PreparedStatement pstat=connection.prepareStatement(query);
            pstat.setDouble(1,amt);
            pstat.setString(2,bus_id);
            int rowsAffected=pstat.executeUpdate();
            if(rowsAffected>0)
            {
                int i=5;
                
                    System.out.print("Updating");
                while(i!=0)
                {
                    System.out.print("..");
                    Thread.sleep(450);
                    i--;
                }
                System.out.println("Amount updated successfully");
            }
            else{
                System.out.println("Not updated");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     }
     public void update_status(Connection connection)
     {
         try {
        System.out.print("Enter Bus No: ");
        String bus_id = sc.next();

        String getStatusQuery = "SELECT status FROM buses WHERE bus_id = ?";
        PreparedStatement psGet = connection.prepareStatement(getStatusQuery);
        psGet.setString(1, bus_id);
        ResultSet rs = psGet.executeQuery();

        if (!rs.next()) {
            System.out.println("❌ No bus found with number: " + bus_id);
            return;
        }

        String currentStatus = rs.getString("status");
        String newStatus;

        if ("functioning".equalsIgnoreCase(currentStatus)) {
            newStatus = "in_repair";
        } else if ("in_repair".equalsIgnoreCase(currentStatus)) {
            newStatus = "functioning";
        } else {
            System.out.println("Unknown status: " + currentStatus);
            return;
        }

        String updateQuery = "UPDATE buses SET status = ? WHERE bus_id = ?";
        PreparedStatement psUpdate = connection.prepareStatement(updateQuery);
        psUpdate.setString(1, newStatus);
        psUpdate.setString(2, bus_id);

        int rowsUpdated = psUpdate.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Status updated to: " + newStatus);
        } else {
            System.out.println(" Failed to update status.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
     }

     public void choose(Connection connection)
     {
        while(true)
        {
        System.out.println("----------------------------------");
        System.out.println("Welcome to the Bus Handling system");
        System.out.println("----------------------------------");
        System.out.println("Choose an option");
        System.out.println("1. Add a new bus");
        System.out.println("2. Fetch bus details");
        System.out.println("3. Update bus details");
        System.out.println("0. Exit");
        int choice=sc.nextInt();
        switch(choice)
        {
            case 1:
            create(connection);
            break;
            case 2:
            fetch(connection);
            break;
            case 3:
            update_bus(connection);
            break;
            case 0:
            try
            {
                exit();
            }
            catch(InterruptedException e)
            {
                System.out.println(e.getMessage());
            }
            return;
            default:
            System.out.println("Wrong Option");
        }
    }
        
     }
}
