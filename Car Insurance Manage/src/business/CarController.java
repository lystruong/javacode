package business;

import Model.Car;
import tools.Acceptable;
import Tools.Inputter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CarController implements Acceptable {

    private Inputter inputter = new Inputter();
    private List<Car> carList = new ArrayList<>();

    public List<Car> getCarList() {
        return carList;
    }

//CASE 1: ADD NEW CAR
    public void addNewCar() {
        while (true) {

            String licensePlate = inputLicensePlate();
            String owner = inputCarOwner();
            String phoneNumber = inputPhoneNumber();
            String brand = inputCarBrand();
            long value = inputVehicleValue();
            Date registrationDate = inputRegistrationDate();
            int seats = inputNumberOfSeats();
            String registrationPlace = getRegistrationPlace(licensePlate);

            Car newCar = new Car(licensePlate, owner, phoneNumber, brand, value, registrationDate, registrationPlace, seats);
            carList.add(newCar);

            System.out.println("New car added successfully:");
            displayVehicleDetails(newCar);

            System.out.print("Do you want to add new car information ? (Y/N): ");
            String response = inputter.getAnswer().trim();
            if (!response.equalsIgnoreCase("y")) {
                break;
            }
        }

    }

    // enter license plate
    private String inputLicensePlate() {
        while (true) {
            System.out.print("Enter License Plate: ");
            String licensePlate = inputter.getAnswer();

            if (!inputter.isValid(licensePlate, LICENSE_PLATE_VALID)) {
                System.out.println("Invalid license plate format. It must start with 50-59, followed by a district character and a digit, then 5 digits. Try again.");
                continue;
            }

            if (isLicensePalateExists(licensePlate)) {
                System.out.println("License Plate already exists. Please try again!!1");
            } else {
                return licensePlate;
            }
        }
    }

    //enter car owner
    private String inputCarOwner() {
        while (true) {
            System.out.print("Enter Car Owner: ");
            String owner = inputter.getAnswer();
            if (inputter.isValid(owner, OWNER_NAME_VALID)) {
                return owner;
            } else {
                System.out.println("Invalid owner name. Try again.");
            }
        }
    }

    //enter phone number
    private String inputPhoneNumber() {
        while (true) {
            System.out.print("Enter Phone Number: ");
            String phoneNumber = inputter.getAnswer();
            if (inputter.isValid(phoneNumber, PHONE_VALID)) {
                return phoneNumber;
            } else {
                System.out.println("Invalid phone number format. Try again.");
            }
        }
    }

    private String inputCarBrand() {
        while (true) {
            System.out.print("Enter Car Brand: ");
            String brand = inputter.getAnswer();
            if (inputter.isValid(brand, CAR_BRAND_VALID)) {
                return brand;
            } else {
                System.out.println("Invalid car brand. Try again.");
            }
        }
    }

    //enter vehicle value
    private long inputVehicleValue() {
        while (true) {
            System.out.print("Enter Vehicle Value: ");
            String valueStr = inputter.getAnswer();
            if (inputter.isValid(valueStr, VEHICLE_VALUE_VALID)) {
                return Long.parseLong(valueStr);
            } else {
                System.out.println("Invalid vehicle value. Try again.");
            }
        }
    }

    //enter registration date
    private Date inputRegistrationDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        Date today = new Date();

        while (true) {
            System.out.print("Enter Registration Date (DD/MM/YYYY): ");
            String dateStr = inputter.getAnswer();

            if (dateStr.trim().isEmpty()) {
                System.out.println("Registration date cannot be empty. Try again.");
                continue;
            }

            try {
                Date registrationDate = dateFormat.parse(dateStr);
                if (registrationDate.after(today)) {
                    System.out.println("Registration date cannot be in the future. Try again.");
                } else {
                    return registrationDate;
                }
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use DD/MM/YYYY format. Try again.");
            }
        }
    }

    //enter number of seat
    private int inputNumberOfSeats() {
        while (true) {
            System.out.print("Enter Number of Seats (4-36): ");
            String seatsStr = inputter.getAnswer();
            if (inputter.isValid(seatsStr, NUMBER_OF_SEATS_VALID)) {
                return Integer.parseInt(seatsStr);
            } else {
                System.out.println("Invalid number of seats. Try again.");
            }
        }
    }

    // Registration Place base on third chart
    private String getRegistrationPlace(String licensePlate) {
        char districtCode = licensePlate.charAt(2);

        switch (districtCode) {
            case 'T':
                return "District 1";
            case 'B':
                return "District 2";
            case 'F':
                return "District 3";
            case 'C':
                return "District 4";
            case 'H':
                return "District 5";
            case 'K':
                return "District 6";
            case 'L':
                return "Districe 8";
            case 'G':
                return "District 12";
            case 'D':
                return "Tân Phú";
            case 'P':
                return "Tân Bình";
            case 'E':
                return "Phú Nhuận";
            case 'N':
                return "Bình Chánh";
            case 'Y':
                return "Củ Chi";
            case 'Z':
                return "Nhà Bè";
            case 'X':
                return "Thủ Đức";
            case 'S':
                return "Bình Thạnh";
            case 'V':
                return "Gò Vấp";
            default:
                return "Unknown District";
        }
    }

//CASE 2: Find Car By License Plate
    public void findCarByLicensePlate() {
        while (true) {
            System.out.print("Enter License Plate to Search: ");
            String licensePlate = inputter.getAnswer().trim();

            if (!inputter.isValid(licensePlate, LICENSE_PLATE_VALID)) {
                System.out.println("Invalid license format. Please try again.");
                continue;
            }

            Car foundCar = null;
            for (Car car : carList) {
                if (car.getLicensePlate().equalsIgnoreCase(licensePlate)) {
                    foundCar = car;
                    break;
                }
            }
            if (foundCar != null) {
                displayVehicleDetails(foundCar);
            } else {
                System.out.println("No one matches the search criterial!");
            }

            System.out.print("Do you want to search for another car ? (Y/N): ");
            String response = inputter.getAnswer().trim();
            if (!response.equalsIgnoreCase("y")) {
                break;
            }

        }
    }
//CASE 3: UPDATE INFORMATION CAR

    public void updateCarInfo() {
        while (true) {

            System.out.print("Enter the Car's License Plate to update: ");
            String licensePlate = inputter.getAnswer().trim();

            Car carToUpdate = null;
            for (Car car : carList) {
                if (car.getLicensePlate().equalsIgnoreCase(licensePlate)) {
                    carToUpdate = car;
                    break;
                }
            }
            if (carToUpdate == null) {
                System.out.println("This vehicle does not exist");
                return;
            }

            System.out.println("The car detail");
            displayVehicleDetails(carToUpdate);

            System.out.println("Note: Can enter new information or press Enter to keep the current");
            System.out.print("Enter the new Car Owner: ");
            String newOwner = inputter.getAnswer();
            if (!newOwner.isEmpty()) {
                carToUpdate.setOwner(newOwner);
                System.out.println("Keep the old car owner.");
            }

            System.out.print("Enter the new Phone Number: ");
            String newPhone = inputter.getAnswer();
            if (!newPhone.isEmpty()) {
                carToUpdate.setPhoneNumber(newPhone);
                System.out.println("Keep the old phone number.");
            }

            System.out.print("Enter the new Car Brand: ");
            String newBrand = inputter.getAnswer();
            if (!newBrand.isEmpty()) {
                carToUpdate.setBrand(newBrand);
                System.out.println("Keep the old car brand.");
            }

            System.out.print("Enter new Number of Seats (or press Enter to keep current: " + carToUpdate.getSeats() + "): ");
            String newSeatsStr = inputter.getAnswer();
            if (!newSeatsStr.isEmpty()) {
                int newSeats = Integer.parseInt(newSeatsStr);
                carToUpdate.setSeats(newSeats);
            }
            System.out.println("The car information updated successfully");
            displayVehicleDetails(carToUpdate);

            System.out.print("Do you want to update another car ? (Y/N): ");
            String response = inputter.getAnswer().trim();
            if (!response.equalsIgnoreCase("y")) {
                break;
            }
        }
    }
//CASE 4: DELETE CAR INFORMATION

    public void deleteCarInfo() {
        while (true) {
            System.out.print("Enter the licnese plate to delete: ");
            String licensePlate = inputter.getAnswer().trim();

            if (!inputter.isValid(licensePlate, Acceptable.LICENSE_PLATE_VALID)) {
                System.out.println("Invalid license format. Please try again.");
                continue;
            }
            Car carToDelete = null;
            for (Car car : carList) {
                if (car.getLicensePlate().equalsIgnoreCase(licensePlate)) {
                    carToDelete = car;
                    break;
                }
            }
            if (carToDelete == null) {
                System.out.println("This vehicle has not registered yet.");
            }else{
                displayVehicleDetails(carToDelete);
                System.out.print("Are you sure you want to delete this registration? (Y/N): ");
                String response = inputter.getAnswer().trim();
                if(response.equalsIgnoreCase("y")){
                    carList.remove(carToDelete);
                    System.out.println("\"The vehicle information has been successfully deleted.");
                    
                }else{
                    System.out.print("Deletion canceled.");
                }
            }
            System.out.print("Do you want to delete another car (Y/N): ");
            String ynResponse =inputter.getAnswer().trim();
            if(!ynResponse.equalsIgnoreCase("y")){
                break;
            }

        }
    }

    public void saveData() {
    }

    public boolean isSaved() {
        return true;
    }

    public boolean isLicensePalateExists(String licensePlate) {
        for (Car car : carList) {
            if (car.getLicensePlate().equalsIgnoreCase(licensePlate)) {
                return true;
            }
        }
        return false;
    }

    public void displayVehicleDetails(Car car) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Vehicle Details: ");
        System.out.println("-----------------------------------------------------");
        System.out.printf("License Plate     : %s%n", car.getLicensePlate());
        System.out.printf("Owner             : %s%n", car.getOwner());
        System.out.printf("Phone Number      : %s%n", car.getPhoneNumber());
        System.out.printf("Car Brand         : %s%n", car.getBrand());
        System.out.printf("Value of Vehicle  : %,d%n", car.getValue());
        System.out.printf("Number of Seats    : %d%n", car.getSeats());
        System.out.printf("Registration Date  : %s%n", dateFormat.format(car.getRegistrationDate()));
        System.out.printf("Registration Place  : %s%n", car.getRegistrationPlace());
        System.out.println("-----------------------------------------------------");
    }

    private Date parseDate(String dateStr) {
        return new Date();
    }


}
