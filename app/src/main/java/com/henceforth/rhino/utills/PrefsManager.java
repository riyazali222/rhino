package com.henceforth.rhino.utills;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsManager {
    private String mPrefsName = "Rhino_prefs";
    private Context mContext;
    private static final String ISLOGINED = "isLogined";
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

    public String getSessionId() {
        return mSharedPreferences.getString("sessionId", "");
    }

    public void setSessionId(String sessionId) {
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

    public Integer getVehicleId() {
        return mSharedPreferences.getInt("vehicleId", 1);
    }

    public void setVehicleId(Integer vehicleId) {
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
}