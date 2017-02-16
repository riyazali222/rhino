package com.example.allmankind.rhino.utills;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.allmankind.rhino.activities.LoginActivity;
import com.example.allmankind.rhino.webServices.apis.RestClient;

import java.io.IOException;
import java.lang.*;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by All Mankind on 2/16/2017.
 */

public class CommonMethods {
    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void logout(Context context) {
        ApplicationGlobal.prefsManager.logout();
        showToast(context, "Session Expired! Please Login Again");
        //Intent intent = new Intent(context, SplashActivity.class);
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void showErrorMessage(Context context, ResponseBody responseBody)
            throws IOException


    {

        Converter<ResponseBody, Error> errorConverter = RestClient.getRetrofitInstance()
                .responseBodyConverter(java.lang.Error.class, new Annotation[0]);
        Error error = errorConverter.convert(responseBody);
        if (error.error.equals("forbidden")) {
            logout(context);
        } else
            CommonMethods.showToast(context, error.error_description);
    }

}
