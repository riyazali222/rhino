package com.henceforth.rhino.activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import com.henceforth.rhino.R;
import com.henceforth.rhino.utills.ApiList;
import com.henceforth.rhino.utills.ApplicationGlobal;
import com.henceforth.rhino.utills.CommonMethods;
import com.henceforth.rhino.utills.Constants;
import com.henceforth.rhino.utills.ForgotPasswordApi;
import com.henceforth.rhino.utills.LocationGetter;
import com.henceforth.rhino.webServices.apis.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etEmailId, etPassword;
    TextView textView1, tvForgetPass;
    public static String unique_id;
    String device_type, device_id, fcm_id;
    LocationManager locationManager;
    double longitudeBest, latitudeBest;
    double longitudeGPS, latitudeGPS;
    double longitudeNetwork, latitudeNetwork;
    TextView longitudeValueBest, latitudeValueBest;
    TextView longitudeValueGPS, latitudeValueGPS;
    TextView longitudeValueNetwork, latitudeValueNetwork;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!ApplicationGlobal.sessionId.isEmpty()) {
            Intent i = new Intent(LoginActivity.this, SwipeTabActivity.class);
            startActivity(i);
            finish();
        } else {
            setContentView(R.layout.activity_login);

            findViewById(R.id.btnLogin).setOnClickListener(this);
            findViewById(R.id.tvForgetPass).setOnClickListener(this);
            etEmailId = (EditText) findViewById(R.id.etEmailId);
            etPassword = (EditText) findViewById(R.id.etPassword);
            textView1 = (TextView) findViewById(R.id.textView1);
            tvForgetPass = (TextView) findViewById(R.id.tvForgetPass);
            unique_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            ApplicationGlobal.prefsManager.setDeviceId(unique_id);


        }

    }

    @Override
    public void onClick(final View v) {

        switch (v.getId()) {
            case R.id.btnLogin:

                if (etEmailId.getText().toString().trim().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                } else if (etPassword.getText().toString().trim().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                } else {
                    if (CommonMethods.isNetworkConnected(LoginActivity.this)) {


                        loginRetrofit(etEmailId.getText().toString(), etPassword.getText().toString()
                                , Constants.device_type, ApplicationGlobal.prefsManager.getDeviceId()
                                , Constants.fcm_id);
                    } else {
                        Toast.makeText(this, "Internet not connected", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.tvForgetPass:
                custom_dialog();
                break;
        }

    }

    private void loginRetrofit(String usercred, String password, String device_type,
                               String device_id, String fcm_id) {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(LoginActivity.this);
        /*progressDialog.setMessage(getString(R.string.string_title_upload_progressbar_));
        progressDialog.show();*/

        RestClient.get().loginResponse(usercred, password, device_type, device_id, fcm_id)
                .enqueue(new Callback<ApiList>() {
                    @Override
                    public void onResponse(Call<ApiList> call, Response<ApiList> response) {

                        progressDialog.dismiss();
                        try {
                            if (response.code() == 200 && response.body() != null) {
                                ApplicationGlobal.sessionId = response.body().getAccessToken();
                                ApplicationGlobal.prefsManager.setSessionId(response.body()
                                        .getAccessToken());
                                Intent i = new Intent(LoginActivity.this,
                                        SwipeTabActivity.class);
                                startActivity(i);
                                finish();
                            } else
                                CommonMethods.showErrorMessage(LoginActivity.this,
                                        response.errorBody());
                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onFailure(Call<ApiList> call, Throwable t) {

                        progressDialog.dismiss();
                    }


                });


    }


    private void custom_dialog() {
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.forgot_password_dialog);
        Toolbar dialogToolbar = (Toolbar) dialog.findViewById(R.id.dialogToolbar);
        dialogToolbar.setNavigationIcon(R.drawable.close);
        dialogToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        Button submitButton = (Button) dialog.findViewById(R.id.btnSubmit);
        final EditText editText = (EditText) dialog.findViewById(R.id.editText);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editText.getText().toString().trim();
                if (!email.isEmpty()) {
                    if (CommonMethods.isNetworkConnected(LoginActivity.this)) {
                        forgotPasswordResponse(email);
                    } else {
                        Toast.makeText(LoginActivity.this, "Internet not connected",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Please enter email id", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
        dialog.show();

    }

    private void forgotPasswordResponse(String email) {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage(getString(R.string.string_title_upload_progressbar_));
        progressDialog.show();
        RestClient.get().forgotPasswordResponse(email).enqueue(new Callback<ForgotPasswordApi>() {
            @Override
            public void onResponse(Call<ForgotPasswordApi> call,
                                   Response<ForgotPasswordApi> response) {

                progressDialog.dismiss();
                try {
                    if (response.code() == 200 && response.body() != null) {
                        Toast.makeText(LoginActivity.this, response.body().getmessage(), Toast.
                                LENGTH_SHORT).show();
                    } else
                        CommonMethods.showErrorMessage(LoginActivity.this,
                                response.errorBody());
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordApi> call, Throwable t) {

                progressDialog.dismiss();
            }
        });

    }
}








