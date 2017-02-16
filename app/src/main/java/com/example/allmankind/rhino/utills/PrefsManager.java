package com.example.allmankind.rhino.utills;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsManager {
    private String mPrefsName = "audmov_prefs";
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    public static SharedPreferences.Editor mEditor;

    public PrefsManager(Context context) {
        this.mContext = context;
        mSharedPreferences = context.getSharedPreferences(mPrefsName, context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    //SessionId or Access Token are same

    public String getSessionId() {
        return mSharedPreferences.getString("sessionId", "");
    }

    public void setSessionId(String sessionId) {
        mEditor.putString("sessionId", sessionId);
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
        ApplicationGlobal.sessionId = "";
        setProfile("");
    }


}