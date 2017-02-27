package com.henceforth.rhino.utills;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.henceforth.rhino.R;
import com.henceforth.rhino.activities.LoginActivity;
import com.henceforth.rhino.webServices.apis.RestClient;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by All Mankind on 2/16/2017.
 */

public class CommonMethods {
    private static Dialog progressBarDialog;
    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }


    public static boolean isNetworkConnected(Context activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }

    public static void logout(Context context) {
        ApplicationGlobal.prefsManager.logout();
        showToast(context, "Session Expired! Please Login Again");
        //Intent intent = new Intent(context, SplashActivity.class);
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void showErrorMessage(Context context, ResponseBody responseBody)
            throws IOException


    {

        Converter<ResponseBody, Error> errorConverter = RestClient.getRetrofitInstance()
                .responseBodyConverter(Error.class, new Annotation[0]);
        Error error = errorConverter.convert(responseBody);
        if (error.error.equals("forbidden")) {
            logout(context);
        } else
            CommonMethods.showToast(context, error.error_description);
    }
    public static boolean isLocationEnabled(Context context) {
        LocationManager locationManager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

    }

    public static void displayLocationSettingsRequest(final Activity activity) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(activity)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i("location", "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i("location", "Location settings are not satisfied. Show the user a " +
                                "dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(activity, Constants.ENABLE_LOCATION);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i("location", "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i("location", "Location settings are inadequate, and cannot be fixed " +
                                "here. Dialog not created.");
                        break;
                }
            }
        });
    }

    public static void showProgressDialog(Context context) {
        if (progressBarDialog == null) {
            progressBarDialog = new Dialog(context);
            progressBarDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            progressBarDialog.setContentView(R.layout.dialog_progress_bar);
            progressBarDialog.getWindow().setBackgroundDrawable
                    (new ColorDrawable(Color.TRANSPARENT));
            progressBarDialog.setCancelable(false);
            progressBarDialog.show();
        }
    }


    public static void dismissProgressDialog() {
        if (progressBarDialog != null) {
            if (progressBarDialog.isShowing())
                progressBarDialog.dismiss();
            progressBarDialog = null;
        }
    }

    public static void showInternetNotConnectedToast(Context context) {
        Toast.makeText(context, "Internet Not Connected", Toast.LENGTH_SHORT).show();
    }

    public static void showErrorToast(Context context) {
        Toast.makeText(context, "Some Error Occured, Please Try Later", Toast.LENGTH_SHORT).show();
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
