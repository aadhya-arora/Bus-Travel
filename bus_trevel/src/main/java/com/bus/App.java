package com.bus;

import java.util.*;
import com.bus.db.dbconnection;
import java.sql.Connection;
import com.bus.crud.bus;
import com.bus.crud.customer;
public class App 
{
    public static void main( String[] args )
    {
        Scanner sc=new Scanner(System.in);
        try {
            Connection con=dbconnection.getConnection();
            bus busService=new bus();
            customer cusService=new customer();
            while(true)
            {
                System.out.println("-------------------------");
                System.out.println("Welcome to Tour & Travels");
                System.out.println("-------------------------");
                System.out.println("******Who are you?*******");
                System.out.println("1. Employee");
                System.out.println("2. Customer");
                System.out.println("0. Exit");
                int choice=sc.nextInt();
                switch (choice) {
                    case 1:
                        busService.choose(con);
                        break;
                    case 2:
                        cusService.choose(con);
                    case 0:
                        System.out.print("Exiting");
                        int i = 5;
                        while (i-- > 0)
                        {
                            System.out.print(".");
                            Thread.sleep(450);
                        }
                        System.out.println(" Goodbye!");
                        System.exit(0);
                        break;
                    default:
                    System.out.println("Wrong choice");
                        break;
                }

            }
            
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        sc.close();
    }
}
