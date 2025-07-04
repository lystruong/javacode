package business;

import Model.Car;
import Tools.Inputter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.InsuranceStatement;
import tools.Acceptable;

/**
 *
 * @author truon
 */
public class InsuranceController {

    private Inputter inputter = new Inputter();
    private List<InsuranceStatement> insuranceStatements = new ArrayList<>();
    private CarController carController;

    public InsuranceController(CarController carController) {
        this.carController = carController;
    }

// CASE 1: ADD NEW INSURANCE STATEMENT
    public void addInsuranceStatement() {
        while (true) {
            String insuranceId = inputInsuranceId();
            String licensePlate = inputLicensePlate();
            Date establishedDate = inputEstablishedDate();
            int insurancePeriod = inputInsurancePeriod();
            String insuranceOwner = inputInsuranceOwner();

            long insuranceFees = calculateInsuranceFees(licensePlate, insurancePeriod);

            InsuranceStatement newInsurance = new InsuranceStatement(insuranceId, licensePlate, establishedDate, insurancePeriod, insuranceFees, insuranceOwner);
            insuranceStatements.add(newInsurance);

            System.out.println("New insurance statement added successfully:");
            displayInsuranceDetails(newInsurance);

            System.out.print("Do you want to add another insurance statement? (Y/N): ");
            String response = inputter.getAnswer().trim();
            if (!response.equalsIgnoreCase("y")) {
                break;
            }
        }
    }

    // Enter Insurance ID
    private String inputInsuranceId() {
        while (true) {
            System.out.print("Enter the Insurance ID: ");
            String insuranceId = inputter.getAnswer().trim();

            if (!inputter.isValid(insuranceId, Acceptable.INSURANCE_ID_VALID)) {
                System.out.println("Invalid Insurance. Please enter 4 digit number. TRy again.");
                continue;
            }

            if (isInsuranceIdExists(insuranceId)) {
                System.out.println("Insurance ID already exists. Please try again.");
            } else {
                return insuranceId;
            }
        }
    }
    //Enter license Plate

    private String inputLicensePlate() {
        while (true) {
            System.out.print("Enter the license plate: ");
            String licensePlate = inputter.getAnswer().trim();

            if (!carController.isLicensePalateExists(licensePlate)) {
                System.out.println("This license plate does not exist. Please register the vehicle first.");
                continue;
            }

            return licensePlate;
        }
    }

    //Enter Established Date
    private Date inputEstablishedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false); // Đặt chế độ nghiêm ngặt để kiểm tra định dạng

        while (true) {
            System.out.print("Enter Established Date (dd/MM/yyyy): ");
            String establishedDateStr = inputter.getAnswer().trim();

            // Kiểm tra định dạng của ngày
            if (!inputter.isValid(establishedDateStr, Acceptable.INSURANCE_ESTABLISHED_DATE_VALID)) {
                System.out.println("Invalid established date format. Please try again.");
                continue;
            }

            try {
                // Chuyển đổi chuỗi thành đối tượng Date
                Date establishedDate = dateFormat.parse(establishedDateStr);
                return establishedDate; // Trả về đối tượng Date
            } catch (ParseException e) {
                System.out.println("Invalid date. Please ensure it is in the format dd/MM/yyyy.");
            }
        }
    }

    //Enter insurance period
    private int inputInsurancePeriod() {
        while (true) {
            System.out.print("Enter the Insurance Period (12, 24 or 36): ");
            String period = inputter.getAnswer().trim();

            if (!period.matches(Acceptable.INSURANCE_PERIOD_VALID)) {
                System.out.println("Invalid insurance periosd. Please try again.");
                continue;
            }
            return Integer.parseInt(period);
        }
    }

    //Enter insurance owner
    private String inputInsuranceOwner() {
        while (true) {
            System.out.print("Enter the insurance owner: ");
            String insuranceOwner = inputter.getAnswer().trim();

            if (!inputter.isValid(insuranceOwner, Acceptable.INSURANCE_OWNER_VALID)) {
                System.out.println("Invalid Insurance Owner Name Valid. Please try again.");
                continue;

            }
            return insuranceOwner;
        }
    }

    // Calculate 
    private long calculateInsuranceFees(String licensePlate, int insurancePeriod) {
        long fee = getVehicleValue(licensePlate);

        switch (insurancePeriod) {
            case 12:
                return (long) (0.25 * fee);
            case 24:
                return (long) (0.20 * fee);
            case 36:
                return (long) (0.15 * fee);
            default:
                return 0;
        }
    }

    public void listInsuranceStatements() {
        // Logic để liệt kê hợp đồng bảo hiểm
    }

    public void reportUninsuredCars() {
        // Logic để báo cáo xe chưa có bảo hiểm
    }

    public void saveData() {
        // Logic để lưu dữ liệu
    }

    public boolean isSaved() {
        // Kiểm tra xem có thay đổi chưa được lưu không
        return false;
    }

    public void displayInsuranceDetails(InsuranceStatement insurance) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Insurance Details: ");
        System.out.println("-----------------------------------------------------");
        System.out.printf("Insurance ID      : %s%n", insurance.getInsuranceId());
        System.out.printf("License Plate     : %s%n", insurance.getLicensePlate());
        System.out.printf("Established Date  : %s%n", dateFormat.format(insurance.getEstablishedDate()));
        System.out.printf("Insurance Period   : %d months%n", insurance.getInsurancePeriod());
        System.out.printf("Insurance Fees    : %,d%n", insurance.getInsuranceFees());
        System.out.printf("Insurance Owner   : %s%n", insurance.getInsuranceOwner());
        System.out.println("-----------------------------------------------------");
    }

    private boolean isInsuranceIdExists(String insuranceID) {
        for (InsuranceStatement statement : insuranceStatements) {
            if (statement.getInsuranceId().equals(insuranceID)) {
                return true;
            }
        }
        return false;
    }

    private long getVehicleValue(String licensePlate) {
        for (Car car : carController.getCarList()) {
            if (car.getLicensePlate().equalsIgnoreCase(licensePlate)) {
                return car.getValue();
            }
        }
        return 0;
    }
}
