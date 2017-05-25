package com.henceforth.rhino.utills;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.henceforth.rhino.webServices.AddVehicles;
import com.henceforth.rhino.webServices.ProfileData;

import java.util.ArrayList;

public class PrefsManager {
    private String mPrefsName = "Rhino_prefs";
    private Context mContext;
    private static final String ISLOGINED = "isLogined";
    private static final String PREFS_VEHICLE_INFO_LIST = "Vehicle Info list";
    private static final String PREFS_PROFILE_INFO_LIST = "Profile Info list";
    private SharedPreferences mSharedPreferences;
    public static SharedPreferences.Editor mEditor;

    public PrefsManager(Context context) {
        this.mContext = context;
        mSharedPreferences = context.getSharedPreferences(mPrefsName, context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }


    public boolean getIsLogined() {
        return mSharedPreferences.getBoolean(ISLOGINED, false);
    }

    //SessionId or Access Token are same

    public String getSessionID() {
        return mSharedPreferences.getString("sessionId", "");
    }

    public void setSessionID(String sessionId) {
        mEditor.putBoolean(ISLOGINED, true);
        mEditor.putString("sessionId", sessionId);
        mEditor.apply();
    }

    public int getNotificationId() {
        return mSharedPreferences.getInt("notiId", 1);
    }

    public void setNotificationId(int notiId) {
        mEditor.putInt("notiId", notiId);
        mEditor.apply();
    }

    public String getVehicleName() {
        return mSharedPreferences.getString("vehicleName", "");
    }

    public void setVehicleName(String vehicleName) {
        mEditor.putString("vehicleName", vehicleName);
        mEditor.apply();
    }

    public String getProfile() {
        return mSharedPreferences.getString("profile", "");
    }

    public void setProfile(String profile) {
        mEditor.putString("profile", profile);
        mEditor.apply();
    }


    public void logout() {
        mEditor.clear();
        ApplicationGlobal.vehicleName = "";
        ApplicationGlobal.sessionId = "";

    }

    public String getDeviceId() {
        return mSharedPreferences.getString("deviceId", "");
    }

    public void setDeviceId(String deviceId) {
        mEditor.putString("deviceId", deviceId);
        mEditor.apply();
    }

    public Integer getVehicleBrandId() {
        return mSharedPreferences.getInt("vehicleId", 1);
    }

    public void setVehicleBrandId(Integer vehicleId) {
        mEditor.putInt("vehicleId", vehicleId);
        mEditor.apply();
    }

    public String getYear() {
        return mSharedPreferences.getString("year", "");
    }

    public void setYear(String year) {
        mEditor.putString("year", year);
        mEditor.apply();
    }

    public String getStatus() {
        return mSharedPreferences.getString("save", "");
    }

    public void setStatus(String status) {
        mEditor.putString("save", status);
        mEditor.apply();
    }
    public void saveVehicleInfoList(ArrayList<AddVehicles> locationsList) {
        if (locationsList != null) {
            mEditor.putString(PREFS_VEHICLE_INFO_LIST, new Gson().toJson(locationsList));
            mEditor.apply();
        }
    }
    public ArrayList<AddVehicles> getVehicleInfoList() {
        if (mSharedPreferences.contains(PREFS_VEHICLE_INFO_LIST)) {
            return new Gson().fromJson(mSharedPreferences.getString(PREFS_VEHICLE_INFO_LIST, ""),
                    new TypeToken<ArrayList<AddVehicles>>() {
                    }.getType());
        } else return new ArrayList<>();
    }
    public String getUserId() {
        return mSharedPreferences.getString("user_id", "");
    }

    public void setUserId(String id) {
        mEditor.putString("user_id", id);
        mEditor.apply();
    }
    public String getUserImage() {
        return mSharedPreferences.getString("user_id", "");
    }


    public void saveUserInfoList(ArrayList<ProfileData> locationsList) {
        if (locationsList != null) {
            mEditor.putString(PREFS_PROFILE_INFO_LIST, new Gson().toJson(locationsList));
            mEditor.apply();
        }
    }
    public ArrayList<ProfileData> getUserInfoList() {
        if (mSharedPreferences.contains(PREFS_PROFILE_INFO_LIST)) {
            return new Gson().fromJson(mSharedPreferences.getString(PREFS_PROFILE_INFO_LIST, ""),
                    new TypeToken<ArrayList<AddVehicles>>() {
                    }.getType());
        } else return new ArrayList<>();
    }
    public String getLicenceNoDetails() {
        return mSharedPreferences.getString("details", "");
    }

    public void setLicenceNoDetails(String details) {
        mEditor.putString("details", details);
        mEditor.apply();
    }
    public String getBrandName() {
        return mSharedPreferences.getString("brand", "");
    }

    public void setBrandName(String brand) {
        mEditor.putString("brand", brand);
        mEditor.apply();
    }
    public String getInfo() {
        return mSharedPreferences.getString("VehicleInfo", "");
    }

    public void setInfo(String VehicleInfo) {
        mEditor.putString("VehicleInfo", VehicleInfo);
        mEditor.apply();
    }
    public Integer getServiceTypeCode() {
        return mSharedPreferences.getInt("service_type", 1);
    }

    public void setServiceTypeCode(Integer service_type) {
        mEditor.putInt("service_type", service_type);
        mEditor.apply();
    }
    public String getServiceTypeName() {
        return mSharedPreferences.getString("service_name", "");
    }

    public void setServiceTypeName(String service_name) {
        mEditor.putString("service_name", service_name);
        mEditor.apply();
    }

    public String getPhoneNo() {
        return mSharedPreferences.getString("phone_no", "");
    }

    public void setPhoneNo(String phone_no) {
        mEditor.putString("phone_no", phone_no);
        mEditor.apply();
    }
}