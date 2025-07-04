package Main;

import business.CarController;
import business.InsuranceController;
import Tools.Inputter;

public class Main {

    public static void main(String[] args) {
        Inputter input = new Inputter();
        CarController carController = new CarController();
        InsuranceController insuranceController = new InsuranceController(carController);

        while (true) {
            displayMainMenu();
            int choice = input.getChoice();

            try {
                switch (choice) {
                    case 1:
                        carController.addNewCar();
                        break;
                    case 2:
                        carController.findCarByLicensePlate();
                        break;
                    case 3:
                        carController.updateCarInfo();
                        break;
                    case 4:
                        carController.deleteCarInfo();
                        break;
                    case 5:
                        insuranceController.addInsuranceStatement();
                        break;
                    case 6:
                        insuranceController.listInsuranceStatements();
                        break;
                    case 7:
                        insuranceController.reportUninsuredCars();
                        break;
                    case 8:
                        carController.saveData();
                        insuranceController.saveData();
                        System.out.println("All data has been saved successfully!");
                        break;
                    case 9:
                        if (!carController.isSaved() || !insuranceController.isSaved()) {
                            if (input.confirmExit()) {
                                System.out.println("Exiting the program.");
                                return;
                            }
                        } else {
                            System.out.println("Exiting the program.");
                            return;
                        }
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                // Clear the scanner buffer
                input.getChoice(); // Consume the invalid input
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n=== CAR INSURANCE MANAGEMENT ===");
        System.out.println("1. Add a new car");
        System.out.println("2. Find a car by license plate");
        System.out.println("3. Update car information");
        System.out.println("4. Delete car information");
        System.out.println("5. Add an insurance statement");
        System.out.println("6. List insurance statements");
        System.out.println("7. Report uninsured cars");
        System.out.println("8. Save data");
        System.out.println("9. Exit");
    }
}