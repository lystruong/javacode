package model;

import java.io.Serializable;
import java.util.Date;

public class InsuranceStatement implements Serializable {

    private static final long serialVersionUID = 1L;

    private String insuranceId;
    private String licensePlate;
    private Date establishedDate;
    private int insurancePeriod;
    private long insuranceFees;
    private String insuranceOwner;

    public InsuranceStatement() {
    }

    public InsuranceStatement(String insuranceId, String licensePlate, Date establishedDate,
            int insurancePeriod, long insuranceFees, String insuranceOwner) {
        this.insuranceId = insuranceId;
        this.licensePlate = licensePlate;
        this.establishedDate = establishedDate;
        this.insurancePeriod = insurancePeriod;
        this.insuranceFees = insuranceFees;
        this.insuranceOwner = insuranceOwner;
    }

    // Getters and Setters
    public String getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(String insuranceId) {
        this.insuranceId = insuranceId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Date getEstablishedDate() {
        return establishedDate;
    }

    public void setEstablishedDate(Date establishedDate) {
        this.establishedDate = establishedDate;
    }

    public int getInsurancePeriod() {
        return insurancePeriod;
    }

    public void setInsurancePeriod(int insurancePeriod) {
        this.insurancePeriod = insurancePeriod;
    }

    public long getInsuranceFees() {
        return insuranceFees;
    }

    public void setInsuranceFees(long insuranceFees) {
        this.insuranceFees = insuranceFees;
    }

    public String getInsuranceOwner() {
        return insuranceOwner;
    }

    public void setInsuranceOwner(String insuranceOwner) {
        this.insuranceOwner = insuranceOwner;
    }

    @Override
    public String toString() {
        return String.format("Insurance{id='%s', licensePlate='%s', period=%d, fees=%d}",
                insuranceId, licensePlate, insurancePeriod, insuranceFees);
    }
}
