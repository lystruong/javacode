
import java.util.Scanner;

public class Menu {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Menu menu = new Menu();
        int choice = 0;

        do {
            System.out.println("--------------- MENIU ---------------");
            System.out.println("1. Add A Student");
            System.out.println("2. Update Student Information");
            System.out.println("3. Display All Students");
            System.out.println("4. Delete Student Information");
            System.out.println("5. Search Participants By Name");
            System.out.println("6. Filter Data By Campus");
            System.out.println("7. Statistics of Registration Numbers by Location");
            System.out.println("8. Save Data");
            System.out.println("9.Exit");

            try {
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        System.out.print("Enter Student ID: ");
                        String id = sc.nextLine();
                        System.out.print("Enter Student Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Student Phone: ");
                        String phone = sc.nextLine();
                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();
                        System.out.print("Enter Mountain Code: ");
                        String mountainCode = sc.nextLine();
                        System.out.print("Enter Fee: ");
                        double tutionFee = sc.nextDouble();

                        Student student = new Student(id, name, phone, email, mountainCode, tutionFee);
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:    
                    case 9:
                        System.out.print("Program exiting!!!");
                        break;
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Error! Please enter number to choice.");
                sc.next();
            }

        } while (choice != 9);

    }
}
