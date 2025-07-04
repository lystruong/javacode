package main;

import java.util.Scanner;
import menu.Menu;
import bussiness.CustomerList;
import bussiness.FeastMenuList;
import bussiness.FeastOrderList;
import java.util.InputMismatchException;
import tool.Inputter;

public class Main {

    public static void main(String[] args) {
        CustomerList customerList = new CustomerList();
        FeastMenuList feastMenuList = new FeastMenuList();
        FeastOrderList feastOrderList = new FeastOrderList();
        Inputter inputter = new Inputter();
        Menu menu = new Menu();

        customerList.readFromFile();
        feastOrderList.readFromFile();
        feastMenuList.readFromFile();

        Scanner sc = new Scanner(System.in);
        int choice = 0;

        do {

            try {
                menu.function();
                System.out.print("Enter your choice (1-8): ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        customerList.addCustomer(inputter);
                        break;
                    case 2:
                        customerList.updateCustomer(inputter);
                        break;
                    case 3:
                        customerList.findCustomerByName();
                        break;
                    case 4:
                        feastMenuList.showAll();
                        break;
                    case 5:
                        feastOrderList.addOrder(inputter, customerList, feastMenuList);
                        break;
                    case 6:
                        feastOrderList.updateOrderInformation(feastMenuList);
                        break;
                    case 7:
                        if (!customerList.getCustomers().isEmpty()) {
                            customerList.saveData();
                        }
                        if (!feastOrderList.feastorders.isEmpty()) {
                            feastOrderList.saveData();
                        }
                        break;
                    case 8:
                        inputter.displayFile(customerList, feastOrderList);
                        break;
                    case 9:
                        System.out.println("Exiting the program");
                        System.exit(0);
                    default:
                        System.out.println("PLEASE ENTER 1-8! TRY AGAIN!!!");
                }
            } catch (InputMismatchException e) {
                System.out.println("INVALID OPTION, PLEASE ENTER AN INTERGER (1-8)!!!");
                sc.nextLine();
            }
        } while (true);
    }
}
