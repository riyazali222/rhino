package com.henceforth.rhino.activities;

import android.app.Dialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import android.widget.TextView;
import android.widget.Toast;

import com.henceforth.rhino.R;
import com.henceforth.rhino.utills.ApplicationGlobal;
import com.henceforth.rhino.utills.CommonMethods;
import com.henceforth.rhino.webServices.apis.ChangePasswordApi;
import com.henceforth.rhino.webServices.apis.EnableDisableApi;
import com.henceforth.rhino.webServices.apis.LogoutApi;
import com.henceforth.rhino.webServices.apis.RestClient;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    TextView tvChangePassword, tvLogout;
    Toolbar toolbarSetting;
    SwitchCompat switchButton;
    private String status ;

   // String stat;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();

      if(!ApplicationGlobal.prefsManager.getStatus().equals("")){
           if(ApplicationGlobal.prefsManager.getStatus().equals("1")){
               switchButton.setChecked(true);
               status = "1";

           }
           else{
               switchButton.setChecked(false);
               status = "0";
           }
       }

       else{
           switchButton.setChecked(true);
       }
    }

    private void init() {
        tvChangePassword = (TextView) findViewById(R.id.tvChangePassword);
        tvLogout = (TextView) findViewById(R.id.tvLogout);
        switchButton = (SwitchCompat) findViewById(R.id.switchButton);
        switchButton.setOnCheckedChangeListener(this);
        toolbarSetting = (Toolbar) findViewById(R.id.toolbarSetting);
        toolbarSetting.setNavigationIcon(R.drawable.ic_close_white);
        toolbarSetting.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tvLogout.setOnClickListener(this);
        tvChangePassword.setOnClickListener(this);
    }




    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.tvLogout:

                logoutDialog();
                break;

            case R.id.tvChangePassword:
                ChangePasswordDialog();
                break;
        }
    }

    private void ChangePasswordDialog() {
        final Dialog dialog = new Dialog(this, R.style.slideFromTopDialog);
        dialog.setContentView(R.layout.change_password_dialog);
        final ShowHidePasswordEditText etOldPass = (ShowHidePasswordEditText)
                dialog.findViewById(R.id.etOldPass);
        final ShowHidePasswordEditText etNewPass = (ShowHidePasswordEditText)
                dialog.findViewById(R.id.etNewPass);
        Toolbar dialogToolbar = (Toolbar) dialog.findViewById(R.id.tbChangePass);
        dialogToolbar.setNavigationIcon(R.drawable.ic_close_white);
        dialogToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        Button btnChange = (Button) dialog.findViewById(R.id.btnChange);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = etOldPass.getText().toString().trim();
                String newPass = etNewPass.getText().toString().trim();
                if (!oldPass.isEmpty() && !newPass.isEmpty() && !etNewPass.equals(etOldPass)) {
                    if (CommonMethods.isNetworkConnected(SettingActivity.this)) {
                        ChangePasswordRetrofit(oldPass, newPass);
                        dialog.dismiss();
                    } else
                        CommonMethods.showInternetNotConnectedToast(SettingActivity.this);

                } else {
                    Toast.makeText(SettingActivity.this, "Please enter email id", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        dialog.show();

    }

    private void logoutDialog() {

        final Dialog dialog = new Dialog(SettingActivity.this, R.style.slideFromTopDialog);
        dialog.setContentView(R.layout.logout_dialog);
        Toolbar dialogToolbar = (Toolbar) dialog.findViewById(R.id.toolbarLogout);
        dialogToolbar.setNavigationIcon(R.drawable.ic_close_white);
        dialogToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        final Button btnCancle = (Button) dialog.findViewById(R.id.btnCancle);
        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String device_id = ApplicationGlobal.prefsManager.getDeviceId();
                if (CommonMethods.isNetworkConnected(SettingActivity.this)) {
                    logoutRetrofit(device_id);

                    dialog.dismiss();
                } else
                    CommonMethods.showInternetNotConnectedToast(SettingActivity.this);

            }

        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void logoutRetrofit(String device_id) {
        CommonMethods.showProgressDialog(SettingActivity.this);
        RestClient.get().LogoutResponse(device_id).enqueue(new Callback<LogoutApi>() {
            @Override
            public void onResponse(Call<LogoutApi> call, Response<LogoutApi> response) {
                String stat=ApplicationGlobal.prefsManager.getStatus();
                CommonMethods.dismissProgressDialog();
                try {
                    if (response.code() == 200 && response.body() != null) {

                        CommonMethods.logout(SettingActivity.this);


                    } else
                        CommonMethods.showErrorMessage(SettingActivity.this,
                                response.errorBody());
                } catch (Exception e) {

                }
                ApplicationGlobal.prefsManager.setStatus(stat);
            }

            @Override
            public void onFailure(Call<LogoutApi> call, Throwable t) {

                CommonMethods.dismissProgressDialog();
            }
        });
    }

    private void ChangePasswordRetrofit(String oldPass, String newPass) {
        CommonMethods.showProgressDialog(SettingActivity.this);
        RestClient.get().ChangePasswordResponse(oldPass, newPass).enqueue(new Callback<ChangePasswordApi>() {
            @Override
            public void onResponse(Call<ChangePasswordApi> call, Response<ChangePasswordApi> response) {

                CommonMethods.dismissProgressDialog();
                try {
                    if (response.code() == 200 && response.body() != null) {
                        CommonMethods.showToast(SettingActivity.this, response.body().getMsg());


                    } else
                        CommonMethods.showErrorMessage(SettingActivity.this,
                                response.errorBody());
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<ChangePasswordApi> call, Throwable t) {

                CommonMethods.dismissProgressDialog();
            }
        });
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            status = "1";
             ApplicationGlobal.prefsManager.setStatus("1");

        } else {

            status = "0";
            ApplicationGlobal.prefsManager.setStatus("0");

        }

        EnableDisableNotificationApi(ApplicationGlobal.prefsManager.getDeviceId(),
               status);

    }

    private void EnableDisableNotificationApi(String device_id, String status) {
        CommonMethods.showProgressDialog(SettingActivity.this);
        RestClient.get().EnableDisableNotification(device_id, status)
                .enqueue(new Callback<EnableDisableApi>() {
            @Override
            public void onResponse(Call<EnableDisableApi> call, Response<EnableDisableApi> response) {

                CommonMethods.dismissProgressDialog();
                try {
                    if (response.code() == 200 && response.body() != null) {
                        Toast.makeText(SettingActivity.this, ApplicationGlobal.prefsManager.getStatus()
                                , Toast.LENGTH_LONG).show();


                    }
                    else
                        CommonMethods.showErrorMessage(SettingActivity.this,
                                response.errorBody());
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(SettingActivity.this, e.getLocalizedMessage()
                            , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<EnableDisableApi> call, Throwable t) {
                Toast.makeText(SettingActivity.this, "Failure"
                        , Toast.LENGTH_LONG).show();
                CommonMethods.dismissProgressDialog();
            }
        });

    }

}
