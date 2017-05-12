package com.henceforth.rhino.utills;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HOME on 5/4/2017.
 */

public class AddVehicles implements Parcelable {
    private Integer user_id;
    private String license_plate_no;
    private String vehicle_identification_number;
    private String membership_id;
    private Integer vehicle_mileage;
    private String type_of_vehicle;
    private Integer vehicle_make_id;
    private String vehicle_model;
    private Integer vehicle_year;
    private Integer user_vehicle_id;
    private  String BrandName;

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getLicense_plate_no() {
        return license_plate_no;
    }

    public void setLicense_plate_no(String license_plate_no) {
        this.license_plate_no = license_plate_no;
    }

    public String getVehicle_identification_number() {
        return vehicle_identification_number;
    }

    public void setVehicle_identification_number(String vehicle_identification_number) {
        this.vehicle_identification_number = vehicle_identification_number;
    }

    public String getMembership_id() {
        return membership_id;
    }

    public void setMembership_id(String membership_id) {
        this.membership_id = membership_id;
    }

    public Integer getVehicle_mileage() {
        return vehicle_mileage;
    }

    public void setVehicle_mileage(Integer vehicle_mileage) {
        this.vehicle_mileage = vehicle_mileage;
    }

    public String getType_of_vehicle() {
        return type_of_vehicle;
    }

    public void setType_of_vehicle(String type_of_vehicle) {
        this.type_of_vehicle = type_of_vehicle;
    }

    public Integer getVehicle_make_id() {
        return vehicle_make_id;
    }

    public void setVehicle_make_id(Integer vehicle_make_id) {
        this.vehicle_make_id = vehicle_make_id;
    }

    public String getVehicle_model() {
        return vehicle_model;
    }

    public void setVehicle_model(String vehicle_model) {
        this.vehicle_model = vehicle_model;
    }

    public Integer getVehicle_year() {
        return vehicle_year;
    }

    public void setVehicle_year(Integer vehicle_year) {
        this.vehicle_year = vehicle_year;
    }

    public Integer getUser_vehicle_id() {
        return user_vehicle_id;
    }

    public void setUser_vehicle_id(Integer user_vehicle_id) {
        this.user_vehicle_id = user_vehicle_id;
    }


    public AddVehicles(Parcel in) {
        user_id = in.readByte() == 0x00 ? null : in.readInt();
        license_plate_no = in.readString();
        vehicle_identification_number = in.readString();
        membership_id = in.readString();
        vehicle_mileage = in.readByte() == 0x00 ? null : in.readInt();
        type_of_vehicle = in.readString();
        vehicle_make_id = in.readByte() == 0x00 ? null : in.readInt();
        vehicle_model = in.readString();
        vehicle_year = in.readByte() == 0x00 ? null : in.readInt();
        user_vehicle_id = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public AddVehicles(Integer user_id, String license_plate_no, String vehicle_identification_number,
                       String membership_id, Integer vehicle_mileage, String type_of_vehicle,
                       Integer vehicle_make_id, String vehicle_model, Integer vehicle_year,
                       Integer user_vehicle_id,String BrandName) {
        this.user_id = user_id;
        this.license_plate_no = license_plate_no;
        this.vehicle_identification_number = vehicle_identification_number;
        this.membership_id = membership_id;
        this.vehicle_mileage = vehicle_mileage;
        this.type_of_vehicle = type_of_vehicle;
        this.vehicle_make_id = vehicle_make_id;
        this.vehicle_model = vehicle_model;
        this.vehicle_year = vehicle_year;
        this.user_vehicle_id = user_vehicle_id;
        this.BrandName=BrandName;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (user_id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(user_id);
        }
        dest.writeString(license_plate_no);
        dest.writeString(vehicle_identification_number);
        dest.writeString(membership_id);
        if (vehicle_mileage == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(vehicle_mileage);
        }
        dest.writeString(type_of_vehicle);
        if (vehicle_make_id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(vehicle_make_id);
        }
        dest.writeString(vehicle_model);

        if (vehicle_year == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(vehicle_year);
        }
        if (user_vehicle_id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(user_vehicle_id);
        }
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