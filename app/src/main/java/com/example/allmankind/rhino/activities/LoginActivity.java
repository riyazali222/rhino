package com.example.allmankind.rhino.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allmankind.rhino.R;
import com.example.allmankind.rhino.utills.ApiList;
import com.example.allmankind.rhino.utills.Constants;
import com.example.allmankind.rhino.webServices.APIs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.allmankind.rhino.R.id.tvForgetPass;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etEmailId, etPassword;
    TextView textView1;
    String unique_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmailId = (EditText) findViewById(R.id.etEmailId);
        etPassword = (EditText) findViewById(R.id.etPassword);
        textView1 = (TextView) findViewById(R.id.textView1);
        findViewById(R.id.btnLogin).setOnClickListener(this);
        findViewById(tvForgetPass).setOnClickListener(this);
        unique_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    @Override
    public void onClick(final View v) {

        switch (v.getId()) {
            case R.id.btnLogin:
                String usercred = etEmailId.getText().toString();
                String password = etPassword.getText().toString();
                String device_type = "1";
                String device_id = unique_id;
                String fcm_id = "sdflje343kjrfksj";

                if (!usercred.isEmpty() && !password.isEmpty()) {

                    textView1.setVisibility(View.VISIBLE);
                    //  getData(usercred, password);
                    loginRetrofit(usercred, password, device_type, device_id, fcm_id);

                } else {
                    Toast.makeText(this, "Fields are empty !", Toast.LENGTH_LONG).show();

                }
                /*Intent i = new Intent(LoginActivity.this, ServiceProviderActivity.class);
                startActivity(i);*/

                break;
            case tvForgetPass:
                custom_dialog();
                break;
        }

    }

    private void loginRetrofit(String usercred, String password, String device_type, String device_id, String fcm_id) {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage(getString(R.string.string_title_upload_progressbar_));
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIs apiInterface = retrofit.create(APIs.class);
        final Call<ApiList> response = apiInterface.loginResponse(usercred, password, device_type, device_id, fcm_id);
        response.enqueue(new Callback<ApiList>() {
            @Override
            public void onResponse(Call<ApiList> call, Response<ApiList> response) {

                progressDialog.dismiss();

                // Response Success or Fail
                if (response.isSuccessful()) {
                    //Toast.makeText(LoginActivity.this, response.body().getUsercred(), Toast.LENGTH_LONG).show();
                    Intent i = new Intent(LoginActivity.this, ServiceProviderActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(LoginActivity.this, "Incorrect email or password", Toast.LENGTH_LONG).show();
                }


                textView1.setVisibility(View.VISIBLE);
                // ivProfileLogin.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ApiList> call, Throwable t) {

                progressDialog.dismiss();
            }


        });

    }

    private void custom_dialog() {
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.dialog_forgot_password);
        Toolbar dialogToolbar = (Toolbar) dialog.findViewById(R.id.dialogToolbar);
        dialogToolbar.setNavigationIcon(R.drawable.close);
        dialogToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        /*ImageView image = (ImageView) dialog.findViewById(R.id.close);*/
        Button submitButton = (Button) dialog.findViewById(R.id.btnSubmit);
        final EditText editText = (EditText) dialog.findViewById(R.id.editText);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editText.getText().toString();
                if (!email.isEmpty()) {
                    forgotPasswordResponse(email);
                    // Toast.makeText(LoginActivity.this, "email send", Toast.LENGTH_SHORT).show();
                    /*dialog.dismiss();*/
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

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIs apiInterface = retrofit.create(APIs.class);
        final Call<ApiList> response = apiInterface.forgotPasswordResponse(email);
        response.enqueue(new Callback<ApiList>() {
            @Override
            public void onResponse(Call<ApiList> call, Response<ApiList> response) {

                progressDialog.dismiss();

                // Response Success or Fail
                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "email send", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(LoginActivity.this, "Incorrect Email Id", Toast.LENGTH_LONG).show();
                }


                textView1.setVisibility(View.VISIBLE);
                // ivProfileLogin.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ApiList> call, Throwable t) {

                progressDialog.dismiss();
            }


        });

    }
}






