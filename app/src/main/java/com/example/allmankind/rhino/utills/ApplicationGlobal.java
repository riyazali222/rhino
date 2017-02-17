package com.example.allmankind.rhino.utills;

import android.app.Application;
import android.util.DisplayMetrics;

import java.util.ArrayList;


public class ApplicationGlobal extends Application {

    public static int screenWidth = 160;
    public static String sessionId;
    public static String vehicleName;
    public static ArrayList<String> cities = new ArrayList<String>();
    public static PrefsManager prefsManager;
    //public static Profile profile;
    public static double myLat = 0, myLng = 0;
    public static boolean isGettingLocation = false;

    @Override
    public void onCreate() {
        super.onCreate();
        //Fresco.initialize(this);
        screenWidth = getScreenWidth();
        prefsManager = new PrefsManager(this);
        sessionId = prefsManager.getSessionId();
        vehicleName=prefsManager.getVehicleName();
//        if (!sessionId.isEmpty()) {
//            profile = new Gson().fromJson(prefsManager.getProfile(), Profile.class);
//        }
        //initialise font lib
        /*CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );*/
        /*if (CommonMethods.isLocationEnabled(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    ActivityCompat.checkSelfPermission
                            (this,
                                    Manifest.permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {

            } else {
                isGettingLocation = true;
                new LocationGetter(this);
            }

        }*/
    }

    public int getScreenWidth() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }


}