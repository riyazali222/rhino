package com.henceforth.rhino.utills;


import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocationGetter extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    //GPS Tracker
    public static String INTENT_GET_LOCATION = "location";
    public static String INTENT_EXTRA_LOCATION_LATITUDE = "latitude";
    public static String INTENT_EXTRA_LOCATION_LONGITUDE = "longitude";

    private Context mContext;
    private static final long INTERVAL = 1000 * 1;
    private static final long FASTEST_INTERVAL = 500 * 1;
    private GoogleApiClient googleApiClient;
    private Location location;
    private FusedLocationProviderApi fusedLocationProviderApi = LocationServices.FusedLocationApi;
    private LocationRequest locationRequest;

    private final String TAG = "MyAwesomeApp";

    public LocationGetter(Context context) {
        this.mContext = context;
        initialiseItems();
    }

    private void initialiseItems() {

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        googleApiClient = new GoogleApiClient.Builder(mContext)
                .addApi(LocationServices.API).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        if (googleApiClient != null) {
            try {
                googleApiClient.connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "GPSTrackerNew destroyed!");
        // Disconnecting the client invalidates it.
        if (googleApiClient.isConnected())
            googleApiClient.disconnect();
        super.onDestroy();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public IBinder onBind(Intent arg0) {

        return null;
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "GoogleApiClient connection connected");

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ApplicationGlobal.isGettingLocation = false;
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location currentLocation = fusedLocationProviderApi
                .getLastLocation(googleApiClient);
        if (currentLocation != null) {
            location = currentLocation;
            ApplicationGlobal.myLat = location.getLatitude();
            ApplicationGlobal.myLng = location.getLongitude();
            ApplicationGlobal.isGettingLocation = false;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "GoogleApiClient connection has been suspend");
        mContext.sendBroadcast(new Intent(INTENT_GET_LOCATION)
                .putExtra(INTENT_EXTRA_LOCATION_LATITUDE, 0)
                .putExtra(INTENT_EXTRA_LOCATION_LONGITUDE, 0));
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "GoogleApiClient connection has failed");
        mContext.sendBroadcast(new Intent(INTENT_GET_LOCATION)
                .putExtra(INTENT_EXTRA_LOCATION_LATITUDE, 0)
                .putExtra(INTENT_EXTRA_LOCATION_LONGITUDE, 0));
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            if (null == this.location
                    || location.getAccuracy() < this.location.getAccuracy()) {
                this.location = location;
                ApplicationGlobal.myLat = location.getLatitude();
                ApplicationGlobal.myLng = location.getLongitude();
                LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
                ApplicationGlobal.isGettingLocation = false;

            }
        }
    }

}