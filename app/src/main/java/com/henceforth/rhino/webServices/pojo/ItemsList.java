package com.henceforth.rhino.webServices.pojo;


import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class ItemsList {

    private boolean selected=false;
    public boolean getSelected() {
        return selected;
    }

    public boolean setSelected(Boolean selected) {
        return this.selected = selected;
    }
    public ItemsList(String vehicle_name) {
        this.vehicle_name = vehicle_name;
    }

    private Integer id;
    private String created_at;
    private String updated_at;
    private String year;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    @SerializedName("id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @SerializedName("make")
    private String vehicle_name;
    public String getVehicle_name() {
        return vehicle_name;
    }
    public String getYear() {
        return year;
    }

    public void setVehicle_name(String vehicle_name) {
        this.vehicle_name = vehicle_name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
