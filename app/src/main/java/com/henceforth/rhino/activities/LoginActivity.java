package com.henceforth.rhino.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.henceforth.rhino.R;
import com.henceforth.rhino.utills.ApiList;
import com.henceforth.rhino.utills.ApplicationGlobal;
import com.henceforth.rhino.utills.CommonMethods;
import com.henceforth.rhino.utills.Constants;
import com.henceforth.rhino.utills.ForgotPasswordApi;
import com.henceforth.rhino.webServices.apis.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etEmailId, etPassword;
    TextView textView1, tvForgetPass;
    public static String unique_id;
    private boolean activityKilled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!ApplicationGlobal.sessionId.isEmpty()) {
            setContentView(R.layout.activity_splash);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!activityKilled) {
                        Intent i = new Intent(LoginActivity.this, SwipeTabActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            }, 1000);
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
                        CommonMethods.hideKeyboard(LoginActivity.this);
                        loginRetrofit(etEmailId.getText().toString(), etPassword.getText().toString()
                                , Constants.device_type, ApplicationGlobal.prefsManager.getDeviceId()
                                , Constants.fcm_id);
                    } else {
                        CommonMethods.showInternetNotConnectedToast(LoginActivity.this);
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
        CommonMethods.showProgressDialog(this);
        RestClient.get().loginResponse(usercred, password, device_type, device_id, fcm_id)
                .enqueue(new Callback<ApiList>() {
                    @Override
                    public void onResponse(Call<ApiList> call, Response<ApiList> response) {

                        CommonMethods.dismissProgressDialog();
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

                        CommonMethods.dismissProgressDialog();
                    }
                });
    }


    private void custom_dialog() {
        final Dialog dialog = new Dialog(LoginActivity.this, R.style.slideFromTopDialog);
        dialog.setContentView(R.layout.forgot_password_dialog);
        Toolbar dialogToolbar = (Toolbar) dialog.findViewById(R.id.dialogToolbar);
        dialogToolbar.setNavigationIcon(R.drawable.ic_close_white);
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
                    if (CommonMethods.isNetworkConnected(LoginActivity.this))
                        forgotPasswordResponse(email);
                    else
                        CommonMethods.showInternetNotConnectedToast(LoginActivity.this);

                } else {
                    Toast.makeText(LoginActivity.this, "Please enter email id", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
        dialog.show();

    }

    private void forgotPasswordResponse(String email) {
        CommonMethods.showProgressDialog(LoginActivity.this);
        RestClient.get().forgotPasswordResponse(email).enqueue(new Callback<ForgotPasswordApi>() {
            @Override
            public void onResponse(Call<ForgotPasswordApi> call,
                                   Response<ForgotPasswordApi> response) {

                CommonMethods.dismissProgressDialog();
                try {
                    if (response.code() == 200 && response.body() != null) {
                        CommonMethods.showToast(LoginActivity.this, response.body().getmessage());
                    } else
                        CommonMethods.showErrorMessage(LoginActivity.this,
                                response.errorBody());
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordApi> call, Throwable t) {
                CommonMethods.showErrorToast(LoginActivity.this);
                CommonMethods.dismissProgressDialog();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityKilled = true;
    }
}