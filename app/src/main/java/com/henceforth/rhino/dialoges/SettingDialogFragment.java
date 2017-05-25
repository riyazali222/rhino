package com.henceforth.rhino.dialoges;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.henceforth.rhino.utills.RestClient;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingDialogFragment extends DialogFragment implements
        CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    TextView tvChangePassword, tvLogout;
    Toolbar toolbarSetting;
    SwitchCompat switchButton;
    private String status;
    Context mContext;

    /*public SettingDialogFragment() {
        mContext = getActivity();
    }*/


     @TargetApi(Build.VERSION_CODES.LOLLIPOP)
     private void changeStatusBarColor() {
         getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(),
          R.color.colorPrimaryDark));
     }
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
                    .MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        changeStatusBarColor();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!ApplicationGlobal.prefsManager.getStatus().equals("")) {
            if (ApplicationGlobal.prefsManager.getStatus().equals("1")) {
                switchButton.setChecked(true);
                status = "1";

            } else {
                switchButton.setChecked(false);
                status = "0";
            }
        } else {
            switchButton.setChecked(true);
        }
        switchButton.setOnCheckedChangeListener(this);
        toolbarSetting.setNavigationIcon(R.drawable.ic_close_white);
        toolbarSetting.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        tvLogout.setOnClickListener(this);
        tvChangePassword.setOnClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        // init();
        View view = inflater.inflate(R.layout.activity_setting, container, false);
        tvChangePassword = (TextView) view.findViewById(R.id.tvChangePassword);
        tvLogout = (TextView) view.findViewById(R.id.tvLogout);
        switchButton = (SwitchCompat) view.findViewById(R.id.switchButton);
        toolbarSetting = (Toolbar) view.findViewById(R.id.toolbarSetting);

        return view;
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
        final Dialog dialog = new Dialog(getActivity(), R.style.slideFromTopDialog);
        dialog.setContentView(R.layout.dialog_change_password);
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
                String _method="PUT";
                if (!oldPass.isEmpty() && !newPass.isEmpty() && !etNewPass.equals(etOldPass)) {
                    if (CommonMethods.isNetworkConnected(getActivity())) {
                        ChangePasswordRetrofit(oldPass, newPass);
                        dialog.dismiss();
                    } else
                        CommonMethods.showInternetNotConnectedToast(getActivity());

                } else {
                    Toast.makeText(getActivity(), "Please enter email id", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        dialog.show();

    }

    private void logoutDialog() {

        final Dialog dialog = new Dialog(getActivity(), R.style.slideFromTopDialog);
        dialog.setContentView(R.layout.dialog_logout);

        final Button btnCancle = (Button) dialog.findViewById(R.id.btnCancle);
        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String device_id = ApplicationGlobal.prefsManager.getDeviceId();
                if (CommonMethods.isNetworkConnected(getActivity())) {
                    logoutRetrofit(device_id);

                    dialog.dismiss();
                } else
                    CommonMethods.showInternetNotConnectedToast(getActivity());

            }

        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //getActivity().onBackPressed();
            }
        });
        dialog.show();

    }

    private void logoutRetrofit(String device_id) {
        CommonMethods.showProgressDialog(getActivity());
        RestClient.get().LogoutResponse(device_id).enqueue(new Callback<LogoutApi>() {
            @Override
            public void onResponse(Call<LogoutApi> call, Response<LogoutApi> response) {
                String stat = ApplicationGlobal.prefsManager.getStatus();
                CommonMethods.dismissProgressDialog();
                try {
                    if (response.code() == 200 && response.body() != null) {

                        CommonMethods.logout(getActivity());


                    } else
                        CommonMethods.showErrorMessage(getActivity(),
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
        CommonMethods.showProgressDialog(getActivity());
        RestClient.get().ChangePasswordResponse(oldPass, newPass).enqueue(new Callback<ChangePasswordApi>() {
            @Override
            public void onResponse(Call<ChangePasswordApi> call, Response<ChangePasswordApi> response) {

                CommonMethods.dismissProgressDialog();
                try {
                    if (response.code() == 200 && response.body() != null) {
                        CommonMethods.showToast(getActivity(), response.body().getMsg());


                    } else
                        CommonMethods.showErrorMessage(getActivity(),
                                response.errorBody());
                }
                catch (Exception e) {

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

    private void EnableDisableNotificationApi(String device_id, final String status) {
        CommonMethods.showProgressDialog(getActivity());
        RestClient.get().EnableDisableNotification(device_id, status)
                .enqueue(new Callback<EnableDisableApi>() {
                    @Override
                    public void onResponse(Call<EnableDisableApi> call, Response<EnableDisableApi>
                            response) {

                        CommonMethods.dismissProgressDialog();
                        try {
                            if (response.code() == 200 && response.body() != null) {
                                /*Toast.makeText(getActivity(), ApplicationGlobal.prefsManager.getStatus()
                                        , Toast.LENGTH_LONG).show();*/

                                ApplicationGlobal.prefsManager.setNotificationId(Integer.parseInt(status));
                            } else
                                CommonMethods.showErrorMessage(getActivity(),
                                        response.errorBody());
                        } catch (Exception e) {
                            //e.printStackTrace();
                            /*Toast.makeText(getActivity(), e.getLocalizedMessage()
                                    , Toast.LENGTH_LONG).show();*/
                        }
                    }

                    @Override
                    public void onFailure(Call<EnableDisableApi> call, Throwable t) {
                        /*Toast.makeText(getActivity(), "Failure"
                                , Toast.LENGTH_LONG).show();*/
                        CommonMethods.dismissProgressDialog();
                    }
                });

    }
}



