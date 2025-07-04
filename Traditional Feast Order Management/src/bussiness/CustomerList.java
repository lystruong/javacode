package bussiness;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import model.Customer;
import tool.Acceptable;
import tool.Inputter;

public class CustomerList {

    public ArrayList<Customer> customers = new ArrayList<>();

    private boolean isSaved = false;

    public ArrayList<Customer> getCustomers() {
        return customers;
    }
//CASE1: ADD
    public void addCustomer(Inputter inputter) {
        while (true) {
            String id = inputter.inputID(this);
            String name = formatName(inputter.inputName());
            String phoneNumber = inputter.inputPhoneNumber(this);
            String email = inputter.inputEmail();

            customers.add(new Customer(id, name, phoneNumber, email));
            isSaved = false;

            System.out.println("This customer has been registered.");

            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.print("Do you want add new customer? (Y/N): ");
                String answer = sc.nextLine();
                if (answer.equalsIgnoreCase("n")) {
                    System.out.println("*************Return to the MENU************* ");
                    return;
                } else if (answer.equalsIgnoreCase("y")) {
                    break;
                } else {
                    System.out.println("Invalid choice! TRY AGAIN!");
                }
            }
        }
    }
//CASE2: UPDATE

    public void updateCustomer(Inputter inputter) {
        Customer findCus;
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Enter customer code to find: ");
            String findName = sc.nextLine();
            findCus = findCustomerByCode(findName);

            if (findCus == null) {
                System.out.println("This customer does not exist.");
            } else {
                System.out.println("This customer does not exist. TRY AGAIN!!1.");
                System.out.println("----------------------------------");
                System.out.println("| Customers are found:          |");
                System.out.println("----------------------------------");
                showCustomer(findName);
                System.out.println("----------------------------------");
                System.out.println("Enter new information to update or press 'ENTER' to skip.");
                break;
            }
        }

        //update name
        while (true) {
            System.out.print("Enter new name: ");
            String newName = sc.nextLine();
            if (newName.isEmpty()) {
                System.out.println("Keep the old name");
                break;
            } else if (inputter.isValid(newName, Acceptable.NAME_VALID)) {
                findCus.setName(formatName(newName));
                break;
            } else {
                System.out.println("New name invalid.");
            }
        }

        //update phone
        while (true) {
            System.out.print("Enter new phone number: ");
            String newPhoneNumber = sc.nextLine();
            if (newPhoneNumber.isEmpty()) {
                System.out.println("Keep the old phone number");
                break;
            } else if (inputter.isValid(newPhoneNumber, Acceptable.PHONE_VALID)) {
                findCus.setPhoneNumber(newPhoneNumber);
                break;
            } else {
                System.out.println("New phone number invalid.");
            }
        }

        //update email
        while (true) {
            System.out.print("Enter new email: ");
            String newEmail = sc.nextLine();
            if (newEmail.isEmpty()) {
                System.out.println("Keep the old email");
                break;
            } else if (inputter.isValid(newEmail, Acceptable.EMAIL_VALID)) {
                findCus.setEmail(newEmail);
                break;
            } else {
                System.out.println("New mail invalid.");
            }
        }

        System.out.println("This customer has been successfully updated!");

        while (true) {
            System.out.print("Do you want to update other customer's information (Y/N) : ");
            String answer = sc.nextLine();
            if (answer.equalsIgnoreCase("n")) {
                System.out.println("Back to menu");
                break;
            } else if (answer.equalsIgnoreCase("y")) {
                updateCustomer(inputter);
                break;
            } else {
                System.out.println("Your choice is invalid. TRY AGAIN.");
            }
        }
        isSaved = false;
        System.out.println("Successfully updated!!!");
    }

    public Customer findCustomerByCode(String id) {
        for (Customer customer : customers) {
            if (customer.getCustomerCode().equalsIgnoreCase(id)) {
                return customer;
            }
        }
        return null;
    }
//CASE 3: FIND CUSTOMER BY NAME

    public void findCustomerByName() {
        Scanner sc = new Scanner(System.in);
        ArrayList<Customer> search = new ArrayList<>();
        System.out.print("Enter customer name to find: ");
        String name = sc.nextLine().toLowerCase();
        for (Customer customer : customers) {
            if (customer.getName().toLowerCase().contains(name)) {
                search.add(customer);
            }
        }

        if (search.isEmpty()) {
            System.out.println("No one matches criteria.");
        } else {
            System.out.println("Matching Customers: " + name);
            showCustomerList(search);
        }
        System.out.println("---------------RETURN TO THE MENU---------------");
    }
    
    

    public String formatName(String name) {
        name = name.trim().replaceAll("\\s+", " ");

        String[] parts = name.split("\\s+");
        if (parts.length > 1) {
            String lastName = parts[parts.length - 1];
            String firstName = String.join(" ", Arrays.copyOf(parts, parts.length - 1));
            return lastName;
        }
        return name;
    }

    public void showCustomer(String id) {
        Customer show = findCustomerByCode(id);
        System.out.println("Code         : " + show.getCustomerCode());
        System.out.println("Customer name: " + show.getName());
        System.out.println("Phone number : " + show.getPhoneNumber());
        System.out.println("Email        : " + show.getEmail());
    }

    public void showCustomerList(ArrayList<Customer> list) {
        if (customers.isEmpty()) {
            System.out.println("Does not have any customer information.");
        }
        Collections.sort(list);
        System.out.println("Customers information:");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("|  Code  | Customer Name         | Phone        | Email               |");
        System.out.println("-----------------------------------------------------------------------");
        for (Customer cs : list) {
            System.out.println(cs);
        }
        System.out.println("-----------------------------------------------------------------------");
    }

    public void writeToFile() {
        String filePath = "src/data/customers.dat";
        try (FileOutputStream fos = new FileOutputStream(filePath);
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(customers);
            System.out.println("All registrations are saved to 'customers.dat' successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromFile() {
        String filePath = "src/data/customers.dat";
        try (FileInputStream fis = new FileInputStream(filePath);
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            customers = (ArrayList<Customer>) ois.readObject();
            System.out.println("Load data from 'customers.dat' successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("Can not find 'customers.dat'.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveData() {
        writeToFile();
        isSaved = true;
        System.out.println("Customer data has been successfully saved to 'customers.dat'");
    }
}
