package bussiness;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import model.FeastMenu;
import model.FeastOrder;
import tool.Inputter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.InputMismatchException;

public class FeastOrderList implements Serializable {
  
    public ArrayList<FeastOrder> feastorders;
    public boolean isSaved = false;

    public FeastOrderList() {
        this.feastorders = new ArrayList<>();
    }

    public void addOrder(Inputter inputter, CustomerList customerList, FeastMenuList feastMenuList) {
        while (true) {
            String customerCode = inputter.findCustomerCode(customerList);

            feastMenuList.displayFeastMenu();
            String setMenuCode = inputter.findMenuCode(feastMenuList);
            FeastMenu toOrder = feastMenuList.findFeastMenuByCode(setMenuCode);

            String date;
            while (true) {
                date = inputter.inputDate();
                if (checkDupplicate(customerCode, setMenuCode, date)) {
                    System.out.println("This date is already booked for the same set menu. Please enter a different date.");
                } else {
                    break;
                }
            }

            double priceOfSet = Double.parseDouble(toOrder.getPrice());
            String setPrice = feastMenuList.formatPrice(priceOfSet);

            int quantity = inputter.quantity();

            String totalCost = feastMenuList.formatPrice(calculateTotalCost(setMenuCode, quantity, feastMenuList));

            int orderID = 0;
            for (FeastOrder fo : feastorders) {
                if (fo.getOrderID() > orderID) {
                    orderID = fo.getOrderID();
                }
            }
            orderID++;

            FeastOrder newOrder = new FeastOrder(customerCode, date, setMenuCode, setPrice, quantity, orderID, totalCost);
            feastorders.add(newOrder);

            System.out.println("------------------------------------------------------------------------");
            System.out.println("Customer order information  [Order ID: " + newOrder.getOrderID() + "]");
            System.out.println("------------------------------------------------------------------------");
            customerList.showCustomer(customerCode);

            System.out.println("------------------------------------------------------------------------");
            System.out.println("Code of Set Menu: " + newOrder.getSetMenuCode());
            System.out.println("Set menu name   : " + toOrder.getName());
            System.out.println("Event date      : " + newOrder.getDate());
            System.out.println("Number of tables: " + newOrder.getNumberOfTable());
            System.out.println("Price           : " + feastMenuList.formatPrice(Double.parseDouble(toOrder.getPrice())) + " VND");
            System.out.println("Ingredients     : \n" + feastMenuList.formatIngredient(toOrder.getIngredient()));
            System.out.println("------------------------------------------------------------------------");
            System.out.println("Total cost      : " + newOrder.getTotalCost() + " VND");
            System.out.println("------------------------------------------------------------------------");

            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.print("Do you want to place another order (Y/N)?: ");
                String answer = sc.nextLine();
                if (answer.equalsIgnoreCase("Y")) {
                    break;
                } else if (answer.equalsIgnoreCase("N")) {
                    System.out.println("Back to menu.");
                    return;
                } else {
                    System.out.println("Invalid choice. TRY AGAIN!!!");
                }
            }
        }
    }

    public void updateOrderInformation(FeastMenuList feastMenuList) {
        int orderID = 0;
        FeastOrder FO;
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Enter Order ID to update: ");
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("OrderID can not be empty.");
                continue;
            }
            try {
                orderID = Integer.parseInt(input);
                FO = findOrderID(orderID);

                if (FO == null) {
                    System.out.println("This order does not exist. Try Again");
                } else if (isValidDate(FO.getDate())) {
                    System.out.println("Enter new information to update or press 'ENTER' to skip.");
                    break;
                } else {
                    System.out.println("Invalid. Try Again");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer for Order ID!!!");
            }
        }

        //Menu Code
        boolean found = false;
        while (true) {
            System.out.print("Enter new set menu code (PW001 - PW006): ");
            String newMenuCode = sc.nextLine();

            if (newMenuCode.isEmpty()) {
                System.out.println("Keeping old information.");
                break;
            } else {
                for (FeastMenu fm : feastMenuList.getFeastMenus()) {
                    if (newMenuCode.equalsIgnoreCase(fm.getMenuCode())) {
                        FO.setSetMenuCode(newMenuCode);
                        found = true;
                        break;
                    }
                }
            }

            if (found) {
                break;
            } else {
                System.out.println("Invalid menu set. TRY AGAIN!!!");
            }
        }
        //Number of tables
        while (true) {
            try {
                System.out.print("Enter new number of tables: ");
                String input = sc.nextLine();

                if (input.isEmpty()) {
                    System.out.println("Keeping old number of table.");
                    break;
                }

                int newNumberOfTables = Integer.parseInt(input);

                if (newNumberOfTables > 0) {
                    FO.setNumberOfTable(newNumberOfTables);
                    break;
                } else {
                    System.out.println("Number greater than 0. TRY AGAIN.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Must be an integer greater than 0. TRY AGAIN!!!");
            }
        }
        //Event date
        while (true) {
            System.out.print("Enter new event date : ");
            String newDate = sc.nextLine();
            if (newDate.isEmpty()) {
                System.out.println("Keeping old event date.");
                break;
            } else {
                if (isValidDate(newDate)) {
                    FO.setDate(newDate);
                    break;
                } else {
                    System.out.println("Invalid date. Please dd/MM/yyyy. TRY AGAIN!!!");
                }
            }
        }

        //TotalPrice
        double totalPrice = 0;

        for (FeastMenu fm : feastMenuList.getFeastMenus()) {
            if (fm.getMenuCode().equalsIgnoreCase(FO.getSetMenuCode())) {
                totalPrice = FO.getNumberOfTable() * Double.parseDouble(fm.getPrice());
                break;
            }
        }

        FO.setTotalCost(feastMenuList.formatPrice(totalPrice));

        System.out.println("All information related to new set has been updated successfully!");

        while (true) {
            System.out.println("Do you want to continue with another update? (Y/N)");
            String answer = sc.nextLine();
            if (answer.equalsIgnoreCase("y")) {
                updateOrderInformation(feastMenuList);
            } else if (answer.equalsIgnoreCase("n")) {
                break;
            } else {
                System.out.println("Invalid stament, try again.");
            }
        }
    }

    public FeastOrder findOrderID(int id) {
        for (FeastOrder fo : feastorders) {
            if (id == fo.getOrderID()) {
                return fo;
            }
        }
        return null;
    }

    public boolean isValidDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dateToCheck = LocalDate.parse(date, formatter);

            if (dateToCheck.isBefore(LocalDate.now())) {
                return false;

            } else if (dateToCheck.isEqual(LocalDate.now())) {
                return false;
            }
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public void showOrderList() {
        if (feastorders.isEmpty()) {
            System.out.println("List is Empty.");
        } else {
            Collections.sort(feastorders);

            System.out.println("Orders information:");
            System.out.println("-------------------------------------------------------------------------------------");
            System.out.println("| ID    | Event date   | Customer ID | Set Menu | Price      | Tables  | Cost       |");
            System.out.println("-------------------------------------------------------------------------------------");
            for (FeastOrder feastorder : feastorders) {
                System.out.println(feastorder);
            }
            System.out.println("-------------------------------------------------------------------------------------");
        }
    }

    public double calculateTotalCost(String setMenuCode, int quantity, FeastMenuList feastMenuList) {
        for (FeastMenu fm : feastMenuList.getFeastMenus()) {
            if (fm.getMenuCode().equalsIgnoreCase(setMenuCode)) {
                return Double.parseDouble(fm.getPrice()) * quantity;
            }
        }
        return 0;
    }

    public boolean checkDupplicate(String customerCode, String setMenu, String date) {
        for (FeastOrder fo : feastorders) {
            if (customerCode.equalsIgnoreCase(fo.getCustomerCode())
                    && setMenu.equalsIgnoreCase(fo.getSetMenuCode())
                    && date.equalsIgnoreCase(fo.getDate())) {
                return true;
            }
        }
        return false;
    }

    public void writeToFile() {
        String filePath = "src/data/feast_order_services.dat";
        try (FileOutputStream fos = new FileOutputStream(filePath);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(feastorders);
            System.out.println("All registrations are saved to 'feast_order_services.dat' successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readFromFile() {
        String filePath = "src/data/feast_order_services.dat";
        try (FileInputStream fis = new FileInputStream(filePath);
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            feastorders = (ArrayList<FeastOrder>) ois.readObject();
            System.out.println("Load data from 'feast_order_services.dat' successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("Can not find 'feast_order_services.dat'.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveData() {
        writeToFile();
        isSaved = true;
        System.out.println("Order data has been successfully saved to 'feast_order_services.dat'");
    }

}
