package com.henceforth.rhino.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.henceforth.rhino.R;
import com.henceforth.rhino.activities.VehicleMakeActivity;
import com.henceforth.rhino.activities.YearPickerActivity;
import com.henceforth.rhino.dialoges.AddedVehiclesDialog;
import com.henceforth.rhino.utills.ApiList;
import com.henceforth.rhino.utills.ApplicationGlobal;
import com.henceforth.rhino.utills.CommonMethods;
import com.henceforth.rhino.utills.Constants;
import com.henceforth.rhino.utills.LocationGetter;
import com.henceforth.rhino.webServices.apis.RestClient;
import com.henceforth.rhino.webServices.pojo.AddedVehicle;
import com.henceforth.rhino.webServices.pojo.EditProfile;
import com.henceforth.rhino.webServices.pojo.VehicleListing;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RaiseRequestFragment extends Fragment implements View.OnClickListener {
    EditText etMileage, etVehicleModel, etMemid, etIdNo, tvOdometerReading,
            etPhoneNo, etBrandName, tvVehicleYear, etLicence, etVehicleType, tvServiceType,
            etDescription;
    TextView tvContactInfo;
    String serviceId;
    private LocationEnableRequest locationEnableRequest = new LocationEnableRequest();
    ArrayList<VehicleListing> info = new ArrayList<>();
    VehicleListing listing;
    String notForYou;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_raise_request, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        etLicence = (EditText) getView().findViewById(R.id.etLicence);
        etMileage = (EditText) getView().findViewById(R.id.etMileage);
        etVehicleType = (EditText) getView().findViewById(R.id.etVehicleType);
        etVehicleModel = (EditText) getView().findViewById(R.id.etVehicleModel);
        tvVehicleYear = (EditText) getView().findViewById(R.id.tvVehicleYear);
        etPhoneNo = (EditText) getView().findViewById(R.id.etPhoneNo);
        etBrandName = (EditText) getView().findViewById(R.id.etBrandName);
        tvContactInfo = (TextView) getView().findViewById(R.id.tvContactInfo);
        tvServiceType = (EditText) getView().findViewById(R.id.tvServiceType);
        etDescription = (EditText) getView().findViewById(R.id.etDescription);
        tvOdometerReading = (EditText) getView().findViewById(R.id.tvOdometerReading);
        etMemid = (EditText) getView().findViewById(R.id.etMemid);
        etIdNo = (EditText) getView().findViewById(R.id.etIdNo);
        //etNotForU = (EditText) getView().findViewById(R.id.etNotForU);
        if (!ApplicationGlobal.prefsManager.getProfile().isEmpty()) {
            EditProfile editProfile = new Gson().fromJson(ApplicationGlobal
                    .prefsManager.getProfile(), EditProfile.class);
            etPhoneNo.setText(editProfile.getPhoneNo());
        }
        //if (!ApplicationGlobal.prefsManager.getPhoneNo().isEmpty())

        tvServiceType.setOnClickListener(this);
        tvContactInfo.setOnClickListener(this);
        getView().findViewById(R.id.btnSubmit).setOnClickListener(this);
        etBrandName.setOnClickListener(this);
        tvVehicleYear.setOnClickListener(this);
        etVehicleType.setOnClickListener(this);
        etLicence.setOnClickListener(this);
        getActivity().registerReceiver(locationEnableRequest,
                new IntentFilter("LocationEnableRequest"));
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //set different color for phone and dial

        SpannableStringBuilder builder = new SpannableStringBuilder();

        SpannableString str1 = new SpannableString(getString(R.string.dial));
        str1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.BackgroundColor)), 0, str1.length(), 0);
        builder.append(str1);

        SpannableString str2 = new SpannableString(getString(R.string.dial_no));
        str2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(),
                R.color.colorAccent)), 0, str2.length(), 0);
        builder.append(str2);

        tvContactInfo.setText(builder, TextView.BufferType.SPANNABLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                etMemid.clearFocus();
            }
        }, 100);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tvServiceType:
//                DialogFragment d= ServiceTypeFragment.newInstance();
                CommonMethods.showDialogFragmentFullScreen((AppCompatActivity) getActivity(), new ServiceTypeFragment(), "Tag");

                break;
            case R.id.etBrandName:
                Intent intent = new Intent(getActivity(), VehicleMakeActivity.class);
                startActivityForResult(intent, 2);
                break;

            case R.id.btnSubmit:
                CommonMethods.hideKeyboard(getActivity());
                if (etLicence.getText().toString().trim().isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterRegistrationNumber));
                else if (etMemid.getText().toString().trim().isEmpty()) {
                    CommonMethods.showToast(getActivity(), getString(R.string.enterMembershipId));
                } else if (etIdNo.getText().toString().trim().isEmpty()) {
                    CommonMethods.showToast(getActivity(), getString(R.string.enterVehicleIdNumber));
                } else if (tvServiceType.getText().toString().trim().isEmpty()) {
                    CommonMethods.showToast(getActivity(), "Please Enter Service Type");
                } else if (tvOdometerReading.getText().toString().trim().isEmpty()) {
                    CommonMethods.showToast(getActivity(), "Please Enter Odometer Reading");
                } else if (etMileage.getText().toString().trim().isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterVehicleMilage));
                else if (etVehicleType.getText().toString().trim().isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterVehicleType));
                else if (tvServiceType.getText().toString().trim().isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterVehicleRequestType));
                else if (etBrandName.getText().toString().trim().isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.selectVehicleCompany));
                else if (etVehicleModel.getText().toString().trim().isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterVehicleModel));
                else if (tvVehicleYear.getText().toString().trim().isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterVehicleYear));
                else if (etPhoneNo.getText().toString().trim().isEmpty())
                    CommonMethods.showToast(getActivity(), getString(R.string.enterPhoneNumber));
                else {
                    if (CommonMethods.isNetworkConnected(getActivity())) {
                        safeLocationPopup();

                    } else
                        CommonMethods.showInternetNotConnectedToast(getActivity());

                }
                break;

            case R.id.etVehicleType:
                showPopupWindow(etVehicleType, R.array.array_list);
                break;

            case R.id.tvContactInfo:
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + 1800243394));
                startActivity(callIntent);
                break;

            case R.id.tvVehicleYear:
                Intent j = new Intent(getActivity(), YearPickerActivity.class);
                startActivityForResult(j, 3);
                break;
            case R.id.etLicence:
//                DialogFragment dialog = AddedVehiclesDialog.newInstance();
                CommonMethods.showDialogFragmentFullScreen((AppCompatActivity) getActivity(), new AddedVehiclesDialog(), "Tag");


                // VehicleListing foo = bundle.getParcelable("Foo");
                   /* value = getArguments().getString("REGISTRATION_NO");
                    etLicence.setText(value);*/


        }
    }

    public String safeLocationPopup() {
        final Dialog dialog = new Dialog(getActivity(), R.style.slideFromTopDialog);
        dialog.setContentView(R.layout.popup_safe_location);
        // Toolbar dialogToolbar = (Toolbar) dialog.findViewById(R.id.toolbarLogout);
//        dialogToolbar.setNavigationIcon(R.drawable.ic_close_white);
//        dialogToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
        final Button btnCancle = (Button) dialog.findViewById(R.id.btnCancle);
        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPopup();
                dialog.dismiss();
            }

        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suggestionPopup();
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setCancelable(false);
        return null;
    }

    private void requestPopup() {
        final Dialog dialog = new Dialog(getActivity(), R.style.slideFromTopDialog);
        dialog.setContentView(R.layout.popup_membership_of_vehicle);
        // Toolbar dialogToolbar = (Toolbar) dialog.findViewById(R.id.toolbarLogout);
//        dialogToolbar.setNavigationIcon(R.drawable.ic_close_white);
//        dialogToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
        final Button btnCancle = (Button) dialog.findViewById(R.id.btnCancle);
        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notForYou = "1";
                checkLocation();
                dialog.dismiss();
            }

        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notForYou = "0";
                checkLocation();
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setCancelable(false);

    }


    public void suggestionPopup() {
        final Dialog dialog = new Dialog(getActivity(), R.style.slideFromTopDialog);
        dialog.setContentView(R.layout.popup_suggestion);
        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }

        });

        dialog.show();
        dialog.setCancelable(false);
    }
//    private void dialogLicence() {
//
//        final Dialog dialog = new Dialog(getActivity(), R.style.slideFromTopDialog);
//
//
//        dialog.setContentView(R.layout.dialog_added_vehicle);
//        Toolbar dialogToolbar = (Toolbar) dialog.findViewById(R.id.dialogToolbar);
//        dialogToolbar.setNavigationIcon(R.drawable.ic_close_white);
//        dialogToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//        Button submitButton = (Button) dialog.findViewById(R.id.btnSubmit);
//        final EditText editText = (EditText) dialog.findViewById(R.id.editText);
//        submitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = editText.getText().toString().trim();
//                if (!email.isEmpty()) {
//                    if (CommonMethods.isNetworkConnected(getActivity())) {
//                        //forgotPasswordResponse(email);
//                    } else
//                        CommonMethods.showInternetNotConnectedToast(getActivity());
//
//                } else {
//                    Toast.makeText(getActivity(), "Please enter email id", Toast.LENGTH_SHORT)
//                            .show();
//                }
//            }
//        });
//        dialog.show();
//
//    }


    private void showPopupWindow(final TextView tv, final int array) {
        final ListPopupWindow popupWindow = new ListPopupWindow(getActivity());
        popupWindow.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.popup_vehicle_type,
                R.id.tvRow, getResources().getStringArray(array)));
        popupWindow.setAnchorView(tv);
        popupWindow.setWidth(ListPopupWindow.WRAP_CONTENT);
        popupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
        popupWindow.setModal(true);
        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tv.setText(getResources().getStringArray(array)[i]);
                popupWindow.dismiss();
            }
        });
        popupWindow.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            String name = data.getStringExtra("name");
            Integer id = data.getIntExtra("id", 1);
            ApplicationGlobal.prefsManager.setVehicleBrandId(id);
            etBrandName.setText(name);
        } else if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            String name = data.getStringExtra("year");
            tvVehicleYear.setText(name);
        }
    }


    private void requestServicesResponse() {

        RestClient.get().requestServicesResponse(etLicence.getText().toString(),
                etMileage.getText().toString(), etVehicleType.getText().toString(),
                String.valueOf(ApplicationGlobal.prefsManager.getServiceTypeCode()),
                String.valueOf(ApplicationGlobal.prefsManager.getVehicleBrandId()),
                etVehicleModel.getText().toString(), tvVehicleYear.getText().toString(),
                etPhoneNo.getText().toString(), ApplicationGlobal.myLat, ApplicationGlobal.myLng,
                tvOdometerReading.getText().toString(), notForYou,
                etDescription.getText().toString()).enqueue(new Callback<ApiList>() {
            @Override
            public void onResponse(Call<ApiList> call, Response<ApiList> response) {
                CommonMethods.dismissProgressDialog();
                try {
                    if (response.code() == 200 & response.body() != null) {

                        Toast.makeText(getActivity(), "Request Submitted",
                                Toast.LENGTH_SHORT).show();

                    } else
                        CommonMethods.showErrorMessage(getActivity(), response.errorBody());
                    Toast.makeText(getActivity(), response.code(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<ApiList> call, Throwable t) {
                CommonMethods.dismissProgressDialog();
            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.ENABLE_LOCATION_PERMISSION)
            checkLocation();
    }

    private void checkLocation() {
        if (CommonMethods.isLocationEnabled(getActivity())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    ActivityCompat.checkSelfPermission
                            (getActivity(),
                                    Manifest.permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION},
                        Constants.ENABLE_LOCATION_PERMISSION);

                return;
            }
            ApplicationGlobal.isGettingLocation = true;
            CommonMethods.showProgressDialog(getActivity());
            new LocationGetter(getActivity());
            getLocation();
        } else
            CommonMethods.displayLocationSettingsRequest(getActivity());
    }

    private void getLocation() {
        if (ApplicationGlobal.myLat == 0)
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getLocation();
                }
            }, 500);
        else {
            requestServicesResponse();
        }
    }

    private class LocationEnableRequest extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            checkLocation();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(locationEnableRequest);
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver,
                new IntentFilter("send_data"));

    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        super.onPause();
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra("REGISTRATION_NO")) {
                String s = intent.getStringExtra("REGISTRATION_NO");
                etLicence.setText(s);
                etMileage.setText("");
                etIdNo.setText("");
                etMemid.setText("");
                etBrandName.setText("");
                etVehicleModel.setText("");
                etVehicleType.setText("");
                tvVehicleYear.setText("");
            } else if (intent.hasExtra("ServiceList")) {
                // Services services = intent.getParcelableExtra("ServiceList");
                tvServiceType.setText(ApplicationGlobal.prefsManager.getServiceTypeName());
            }
            //tvServiceType.setText(intent.getStringExtra("ServiceList"));
            else if (intent.hasExtra("Data_New")) {

                AddedVehicle vehicle = intent.getParcelableExtra("Data_New");
                etLicence.setText(vehicle.getPlateno());
                etMileage.setText(vehicle.getMileage());
                etIdNo.setText(vehicle.getVin());
                etMemid.setText(vehicle.getMid());
                etBrandName.setText(vehicle.getBrand());
                etVehicleModel.setText(vehicle.getModel());
                etVehicleType.setText(vehicle.getTypeofvehicle());
                tvVehicleYear.setText(vehicle.getYear());

            }
        }
    };


}
