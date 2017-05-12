package com.henceforth.rhino.webServices.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HOME on 5/10/2017.
 */

public class AddedVehicle implements Parcelable {
    String plateno="", mid="", brand="", year="", vin="", typeofvehicle="", model="", mileage="";

    public String getPlateno() {
        return plateno;
    }

    public void setPlateno(String plateno) {
        this.plateno = plateno;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getTypeofvehicle() {
        return typeofvehicle;
    }

    public void setTypeofvehicle(String typeofvehicle) {
        this.typeofvehicle = typeofvehicle;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public AddedVehicle(String plateno, String mid, String brand, String year, String vin,
                        String typeofvehicle, String model, String mileage) {
        this.plateno = plateno;
        this.mid = mid;
        this.brand = brand;
        this.year = year;
        this.vin = vin;
        this.typeofvehicle = typeofvehicle;
        this.model = model;
        this.mileage = mileage;

    }

    protected AddedVehicle(Parcel in) {
        plateno = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(plateno);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<AddedVehicle> CREATOR = new Parcelable.Creator<AddedVehicle>() {
        @Override
        public AddedVehicle createFromParcel(Parcel in) {
            return new AddedVehicle(in);
        }

        @Override
        public AddedVehicle[] newArray(int size) {
            return new AddedVehicle[size];
        }
    };
}