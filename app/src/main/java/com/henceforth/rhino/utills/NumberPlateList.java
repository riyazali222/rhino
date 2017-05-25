package com.henceforth.rhino.utills;

import android.os.Parcel;
import android.os.Parcelable;

import com.henceforth.rhino.webServices.AddVehicles;

/**
 * Created by HOME on 5/6/2017.
 */

public class NumberPlateList  implements Parcelable {
    private String NumberPlate = "";
    private String VIN = "";
    private String MemId = "";
    private String mileage = "";
    private String VehicleType = "";
    private String Brand = "";
    private String Model = "";
    private String year = "";

    public String getNumberPlate() {
        return NumberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        NumberPlate = numberPlate;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public String getMemId() {
        return MemId;
    }

    public void setMemId(String memId) {
        MemId = memId;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getVehicleType() {
        return VehicleType;
    }

    public void setVehicleType(String vehicleType) {
        VehicleType = vehicleType;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public NumberPlateList(String numberPlate, String VIN, String memId, String mileage, String vehicleType, String brand, String model, String year) {
        NumberPlate = numberPlate;
        this.VIN = VIN;
        MemId = memId;
        this.mileage = mileage;
        VehicleType = vehicleType;
        Brand = brand;
        Model = model;

        this.year = year;
    }



    protected NumberPlateList(Parcel in) {
        NumberPlate = in.readString();
        VIN = in.readString();
        MemId = in.readString();
        mileage = in.readString();
        VehicleType = in.readString();
        Brand = in.readString();
        Model = in.readString();
        year = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(NumberPlate);
        dest.writeString(VIN);
        dest.writeString(MemId);
        dest.writeString(mileage);
        dest.writeString(VehicleType);
        dest.writeString(Brand);
        dest.writeString(Model);
        dest.writeString(year);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<AddVehicles> CREATOR = new Parcelable.Creator<AddVehicles>() {
        @Override
        public AddVehicles createFromParcel(Parcel in) {
            return new AddVehicles(in);
        }

        @Override
        public AddVehicles[] newArray(int size) {
            return new AddVehicles[size];
        }
    };
}