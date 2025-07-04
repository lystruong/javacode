package Model;

import java.io.Serializable;
import java.util.Date;

public class Car implements Serializable {

    private String licensePlate;
    private String owner;
    private String phoneNumber;
    private String brand;
    private long value;
    private Date registrationDate;
    private String registrationPlace;
    private int seats;

    public Car() {
    }

    public Car(String licensePlate, String owner, String phoneNumber, String brand,
            long value, Date registrationDate, String registrationPlace, int seats) {
        this.licensePlate = licensePlate;
        this.owner = owner;
        this.phoneNumber = phoneNumber;
        this.brand = brand;
        this.value = value;
        this.registrationDate = registrationDate;
        this.registrationPlace = registrationPlace;
        this.seats = seats;
    }

    // Getters and Setters
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getRegistrationPlace() {
        return registrationPlace;
    }

    public void setRegistrationPlace(String registrationPlace) {
        this.registrationPlace = registrationPlace;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return String.format("Car{licensePlate='%s', owner='%s', brand='%s', seats=%d}",
                licensePlate, owner, brand, seats);
    }
}
