package tool;

import java.util.Scanner;
import menu.Menu;
import model.Customer;
import bussiness.CustomerList;
import bussiness.FeastMenuList;
import bussiness.FeastOrderList;
import java.time.DateTimeException;
import model.FeastMenu;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;

public class Inputter {

    private Scanner sc;

    public Inputter() {
        this.sc = new Scanner(System.in);
    }
    Menu menu = new Menu();

    public boolean isValid(String input, String pattern) {
        return input.matches(pattern);
    }

    

    public String inputID(CustomerList customerList) {
        Scanner sc = new Scanner(System.in);
        String customerID;

        while (true) {
            System.out.print("Enter Customer ID: ");
            customerID = sc.nextLine();
            if (!isValid(customerID, Acceptable.ID_VALID)) {
                System.out.println("Invalid Customer ID format! Please try again.");
                continue;
            }

            if (checkExistID(customerList, customerID)) {
                System.out.println("This customer has already registered ! Please enter a unique ID. ");
            } else {
                return customerID;
            }
        }
    }

    public String inputName() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter name: ");
            String name = sc.nextLine();

            if (isValid(name, Acceptable.NAME_VALID)) {
                return name;
            } else {
                System.out.println("Customer name invalid. Try Again.");
            }
        }
    }

    public String inputPhoneNumber(CustomerList customerList) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter phone number: ");
            String phoneNumber = sc.nextLine();
            if (!isValid(phoneNumber, Acceptable.PHONE_VALID)) {
                System.out.println("Number phone invalid! Try Again.");
                continue;
            }
            if (isValidPhoneNumber(phoneNumber, customerList)) {
                System.out.println("Phone number already exist. Please enter other phone number.");
            }

        }
    }

    public String inputEmail() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Enter email: ");
            String email = sc.nextLine();
            if (isValid(email, Acceptable.EMAIL_VALID)) {
                return email;
            } else {
                System.out.println("Email Invalid. Try Again.");
            }
        }
    }

//CASE 5:     
    public String findCustomerCode(CustomerList customerList) {
        Scanner sc = new Scanner(System.in);
        String findCode = "";
        while (true) {
            System.out.print("Enter customer code: ");
            String code = sc.nextLine();

            for (Customer ct : customerList.getCustomers()) {
                {
                    if (code.equalsIgnoreCase(ct.getCustomerCode())) {
                        findCode = code.toUpperCase();
                        return findCode;
                    }
                }
                if (findCode.isEmpty()) {
                    System.out.println("Invalid code, try again.");
                }
            }
        }
    }

    public String findMenuCode(FeastMenuList feastMenuList) {
        Scanner sc = new Scanner(System.in);
        String setCode = "";
        while (true) {
            System.out.print("Enter set menu (PW001 - PW006): ");
            String toCheck = sc.nextLine();
            for (FeastMenu fm : feastMenuList.feastMenus) {
                if (toCheck.equalsIgnoreCase(fm.getMenuCode())) {
                    setCode = fm.getMenuCode();
                    return setCode;
                }
            }

            if (setCode.isEmpty()) {
                System.out.println("Invalid code, try again.");
            }
        }
    }

    public int quantity() {
        Scanner sc = new Scanner(System.in);
        int table = 0;
        while (true) {
            try {
                System.out.print("Enter the number of tables: ");
                table = sc.nextInt();
                sc.nextLine();
                if (table > 0) {
                    break;
                } else {
                    System.out.println("The number of tables must be more than 0.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter the number. TRY AGIAN.");
                sc.nextLine();
            }
        }
        return table;
    }

    public String inputDate() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Enter the date of the event: ");
                String date = sc.nextLine();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate inputDate = LocalDate.parse(date, formatter);

                if (inputDate.isAfter(LocalDate.now())) {
                    return date;
                } else if (inputDate.isBefore(LocalDate.now())) {
                    System.out.println("The date must be after today, try again.");
                } else {
                    System.out.println("The date is invalid, try again.");
                }
            } catch (DateTimeException e) {
                System.out.println("Invalid date format, try again.");
            }
        }
    }

    public String getAnswer() {
        String message = "";
        Scanner sc = new Scanner(System.in);
        message = sc.nextLine();
        return message;
    }
// CASE 8 DISPLAY
    public void displayFile(CustomerList customerList, FeastOrderList feastOrderList) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            menu.displaycase();
            try {
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        customerList.showCustomerList(customerList.getCustomers());
                        break;
                    case 2:
                        feastOrderList.showOrderList();
                        break;
                    case 3:
                        checkListEmpty(customerList, feastOrderList);
                        break;
                    default:
                        System.out.println("Invalid choice, try agian!!!");
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please enter your choice must be 1 or 2.");
                sc.nextLine();
            }
        }
    }

   
    
    public boolean isValidPhoneNumber(String phoneNumber, CustomerList customerList) {
        for (Customer ct : customerList.getCustomers()) {
            if (ct.getPhoneNumber().equalsIgnoreCase(phoneNumber)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkExistID(CustomerList customerList, String customerID) {
        for (Customer customer : customerList.getCustomers()) {
            if (customer.getCustomerCode().equalsIgnoreCase(customerID)) {
                return true;
            }
        }
        return false;
    }
    
     private void checkListEmpty(CustomerList customerList, FeastOrderList feastOrderList) {
        boolean isEmpty = true;
        if (customerList.getCustomers().isEmpty()) {
            System.out.println("Does not have any customer information.");
            isEmpty = false;
        }
        if (feastOrderList.feastorders.isEmpty()) {
            System.out.println("List is Empty");
            isEmpty = false;
        }
    }

}
