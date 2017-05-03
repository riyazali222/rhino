package com.henceforth.rhino.utills;

/**
 * Created by HOME on 5/1/2017.
 */

public class VehiclesInfo {
    String year, model, licenceNo, vehicleType, mileage, brand;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public VehiclesInfo(String licenceNo, String mileage, String vehicleType, String brand, String model
            , String year) {
        this.licenceNo = licenceNo;
        this.mileage = mileage;
        this.vehicleType=vehicleType;
        this.brand=brand;
        this.model=model;
        this.year=year;


    }
}
