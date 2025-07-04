package Tools;

import Model.Car;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import static tools.Acceptable.CAR_BRAND_VALID;
import static tools.Acceptable.LICENSE_PLATE_VALID;
import static tools.Acceptable.NUMBER_OF_SEATS_VALID;
import static tools.Acceptable.OWNER_NAME_VALID;
import static tools.Acceptable.PHONE_VALID;
import static tools.Acceptable.VEHICLE_VALUE_VALID;

public class Inputter {

    private Scanner scanner = new Scanner(System.in);
    private Scanner sc;

    public Inputter() {
        this.sc = new Scanner(System.in);
    }

    public boolean isValid(String input, String pattern) {
        return input.matches(pattern);
    }

    // Nhập một chuỗi từ người dùng
    public String getAnswer() {
        return sc.nextLine().trim();
    }

    public int getChoice() {
        while (true) {
            System.out.print("Select a function (1-9): "); 
           String input = getAnswer(); // Sử dụng getAnswer để nhập dữ liệu
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 9) {
                    return choice; // Lựa chọn hợp lệ
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 9.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 9.");
            }
        }
    }

    public boolean confirmExit() {
        System.out.print("Are you sure want to exit (Y/N): ");
        String response = scanner.next();
        return response.equalsIgnoreCase("yes");
    }

    // License Plate Exists: 
}
