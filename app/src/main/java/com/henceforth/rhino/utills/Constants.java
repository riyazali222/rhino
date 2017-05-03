package com.henceforth.rhino.utills;


import android.os.Environment;

import com.google.firebase.iid.FirebaseInstanceId;
import com.henceforth.rhino.fcm.MyFirebaseInstanceIDService;

public class Constants {
    public static final String BASE_URL = "http://35.154.168.124/";
//    public static final String fcm_id="sdflje343kjrfksj";
    public static final String fcm_id= FirebaseInstanceId.getInstance().getToken();
    public static final String device_type = "1";
    public static final int ENABLE_LOCATION=1;
    public static final int ENABLE_LOCATION_PERMISSION=2;
    public static final String PUSH_TOKEN = "Push_token";
    public static final String USER_DATA = "user_data";
    public static final int GALLERY_REQUEST = 1;
    public static final int CAMERA_REQUEST = 1888;
    public static final String LOCAL_FILE_PREFIX = "file://";
    public static final String LOCAL_STORAGE_BASE_PATH_FOR_USER_PHOTOS =
            Environment.getExternalStorageDirectory() + "/Chillax" + "/User/Photos/";
    public static final String REMINDERS = "reminders";
    public static final String PROFILE_DATA = "profile_data";
    public static final String CATEGORIES = "categories";

}
