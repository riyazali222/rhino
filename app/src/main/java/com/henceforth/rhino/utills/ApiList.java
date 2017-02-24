package com.henceforth.rhino.utills;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ApiList {
    private String id;
    private String updated_at;
    private String created_at;
    private String vehicle_name;


    @SerializedName("error_description")
    @Expose
    private String error_description;

    public String geterror_description() {
        return error_description;
    }

    @SerializedName("message")
    @Expose
    private String message;

    public String getmessage() {
        return message;
    }


    @SerializedName("access_token")
    @Expose
    private String access_token;

    public String getAccessToken() {
        return access_token;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getVehicle_name() {
        return vehicle_name;
    }

    public void setVehicle_name(String vehicle_name) {
        this.vehicle_name = vehicle_name;
    }

    @Override
    public String toString() {
        return "ApiList [id = " + id + ", updated_at = " + updated_at + ", created_at = " + created_at + ", vehicle_name = " + vehicle_name + "]";
    }

}
