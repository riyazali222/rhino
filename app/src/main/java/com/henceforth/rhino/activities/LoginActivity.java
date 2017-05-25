package com.henceforth.rhino.activities;

import android.app.Dialog;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.henceforth.rhino.R;
import com.henceforth.rhino.webServices.ApiList;
import com.henceforth.rhino.utills.ApplicationGlobal;
import com.henceforth.rhino.utills.CommonMethods;
import com.henceforth.rhino.utills.Constants;
import com.henceforth.rhino.utills.CustomTypefaceSpan;
import com.henceforth.rhino.utills.ForgotPasswordApi;
import com.henceforth.rhino.utills.RestClient;
import com.henceforth.rhino.webServices.pojo.ProfileList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etEmailId, etPassword;
    TextView textView1, tvForgetPass, tvTermsCondition;
    public static String unique_id;
    private boolean activityKilled = false;
    ArrayList<ProfileList> profileLists = new ArrayList<>();
    private SharedPreferences mSharedPreferences;
    public static SharedPreferences.Editor mEditor;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!ApplicationGlobal.sessionId.isEmpty()) {
            setContentView(R.layout.activity_splash);
            StartAnimations();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!activityKilled) {
                        Intent i = new Intent(LoginActivity.this, SwipeTabActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            }, 3000);
        } else {
            setContentView(R.layout.activity_login);
            findViewById(R.id.btnLogin).setOnClickListener(this);
            findViewById(R.id.tvForgetPass).setOnClickListener(this);
            findViewById(R.id.tvTermsCondition).setOnClickListener(this);
            TextView tvTermsCondition = (TextView) findViewById(R.id.tvTermsCondition);
            //Spannable String
            String s = "By signing in, you agree with our Terms and Conditions";
            Typeface robotoRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
            Typeface robotoBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
            TypefaceSpan robotoRegularSpan = new CustomTypefaceSpan("", robotoRegular);
            TypefaceSpan robotoBoldSpan = new CustomTypefaceSpan("", robotoBold);
            SpannableStringBuilder sb = new SpannableStringBuilder(s);
            sb.setSpan(robotoRegularSpan, 0, 33, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            sb.setSpan(robotoBoldSpan, 33, s.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            sb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)),
                    34, s.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            tvTermsCondition.setText(sb);

            etEmailId = (EditText) findViewById(R.id.etEmailId);
            etPassword = (EditText) findViewById(R.id.etPassword);
            textView1 = (TextView) findViewById(R.id.textView1);
            tvForgetPass = (TextView) findViewById(R.id.tvForgetPass);
            unique_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            ApplicationGlobal.prefsManager.setDeviceId(unique_id);
        }
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l = (LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.ivSplash);
        iv.clearAnimation();
        iv.startAnimation(anim);
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
                /*Intent i = new Intent(LoginActivity.this,
                        SwipeTabActivity.class);
                startActivity(i);*/
                break;

            case R.id.tvForgetPass:
                custom_dialog();
                break;

            case R.id.tvTermsCondition:
                Intent intent = new Intent(LoginActivity.this, TermsConditionActivity.class);
                startActivity(intent);
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
                                ApplicationGlobal.sessionId = response.body().getAccess_token();
                                ApplicationGlobal.prefsManager.setSessionID(response.body()
                                        .getAccess_token());
//                             /*   String img=response.body().getImage();
//                                String email=response.body().getEmail();
//                                String custId=response.body().getCustomer_id();
//                                String  fname=response.body().getFirstname();
//                                String lname=response.body().getLastname();
//                                String mname=response.body().getMiddlename();
//                                String add1=response.body().getAddress1();
//                                String add2=response.body().getAddress2();
//                                String add3=response.body().getAddress3();
//                                String city=response.body().getCity();
//                                String state=response.body().getState();
//                                String country=response.body().getCountry();
//                                String pn=response.body().getPhone_no();
//                                ProfileList list=new ProfileList(custId,email,fname,mname,lname,
//                                        img,add1,add2,add3,city,state,country,pn);
//                                Gson gson = new Gson();
//                                String json = gson.toJson(list);
//                                mEditor.putString("MyObject", json);
//                                mEditor.commit();*/
                                ApplicationGlobal.prefsManager.setProfile(new Gson().toJson(response.body()));
                                Intent intent = new Intent("UPDATE");
                                intent.putExtra("Update", "");
                                LocalBroadcastManager.getInstance(LoginActivity.this)
                                        .sendBroadcast(intent);

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
        dialog = new Dialog(LoginActivity.this, R.style.slideFromTopDialog);


        dialog.setContentView(R.layout.dialog_forgot_password);
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
                        CommonMethods.showToast(LoginActivity.this,"An Email has been sent to you");
                        dialog.dismiss();

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