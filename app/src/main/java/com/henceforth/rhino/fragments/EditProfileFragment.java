package com.henceforth.rhino.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.henceforth.rhino.R;
import com.henceforth.rhino.utills.ApplicationGlobal;
import com.henceforth.rhino.utills.CommonMethods;
import com.henceforth.rhino.utills.Constants;
import com.henceforth.rhino.utills.GetSampledImage;
import com.henceforth.rhino.utills.RetrofitUtils;
import com.henceforth.rhino.webServices.apis.RestClient;
import com.henceforth.rhino.webServices.pojo.EditProfile;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileFragment extends Fragment implements GetSampledImage.SampledImageAsyncResp {


    @BindView(R.id.toolbarTitle) TextView toolbarTitle;
    @BindView(R.id.lin_lay) LinearLayout linLay;
    @BindView(R.id.toolbarProfile) Toolbar toolbarProfile;
    //@BindView(R.id.ivProfileUpload) ImageView ivProfileUpload;
    //@BindView(R.id.etCustomerId) EditText etCustomerId;
    @BindView(R.id.etFName) EditText etFName;
    @BindView(R.id.etMidName) EditText etMidName;
    @BindView(R.id.etLastName) EditText etLastName;
    @BindView(R.id.etCompName) EditText etCompName;
    @BindView(R.id.etAddress1) EditText etAddress1;
    @BindView(R.id.etAddress2) EditText etAddress2;
    @BindView(R.id.etAddress3) EditText etAddress3;
    @BindView(R.id.etCity) EditText etCity;
    @BindView(R.id.etState) EditText etState;
    @BindView(R.id.etCountry) EditText etCountry;
    @BindView(R.id.etPhone) EditText etPhone;
    @BindView(R.id.btnSubmitProfile)
    Button btnSubmitProfile;
    ImageView ivProfileUpload;
    String compNameP, fNameP, MNameP, LNameP, add1P, add2P, add3P, phone_noP, CustIdP, cityP, stateP,
            countryP, faxP, licencePlateNo = "NYC AF2345", vehicle_idP = "123456";

    private String picturePath;
    private File imageProfile;
    private static final int MY_PERMISSIONS_CAMERA_HARDWARE = 102;
    private static final int MY_PERMISSIONS_CAMERA_STORAGE = 1033;
    File f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        ButterKnife.bind(this, view);
        return view;


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!ApplicationGlobal.prefsManager.getProfile().isEmpty()) {
            EditProfile editProfile = new Gson().fromJson(ApplicationGlobal.prefsManager.getProfile()
                    , EditProfile.class);

            etFName.setText(editProfile.getFirstname());
            etMidName.setText(editProfile.getMiddlename());
            etLastName.setText(editProfile.getLastname());
           // etCustomerId.setText(editProfile.getCustomerId());
            etAddress1.setText(editProfile.getAddress1());
            etAddress2.setText(editProfile.getAddress2());
            etAddress3.setText(editProfile.getAddress3());
            etPhone.setText(editProfile.getPhoneNo());
            etCity.setText(editProfile.getCity());
            etState.setText(editProfile.getState());
            etCountry.setText(editProfile.getCountry());
            etCompName.setText(editProfile.getCompanyName());

            Glide.with(getActivity()).load(editProfile.getImage()).centerCrop().into(ivProfileUpload);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivProfileUpload = (ImageView) view.findViewById(R.id.ivProfileUpload);
        ivProfileUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT > 22) {
                    checkPermission();
                } else {
                    showPictureOptionsDialog();
                    // selectImage();
                }
            }
        });
        toolbarProfile = (Toolbar) getView().findViewById(R.id.toolbarProfile);
        toolbarProfile.setNavigationIcon(R.drawable.ic_close_white);
        toolbarProfile.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    @OnClick({R.id.btnSubmitProfile})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btnSubmitProfile:
                if (CommonMethods.isNetworkConnected(getActivity())) {
                    compNameP = etCompName.getText().toString();
                    fNameP = etFName.getText().toString();
                    MNameP = etMidName.getText().toString();
                    LNameP = etLastName.getText().toString();
                    add1P = etAddress1.getText().toString();
                    add2P = etAddress2.getText().toString();
                    add3P = etAddress3.getText().toString();
                    phone_noP = etPhone.getText().toString();
                  //  CustIdP = etCustomerId.getText().toString();
                    cityP = etCity.getText().toString();
                    stateP = etState.getText().toString();
                    countryP = etCountry.getText().toString();
                    faxP = "";
                    CommonMethods.hideKeyboard(getActivity());
               if (etFName.getText().toString().trim().isEmpty()) {
                        CommonMethods.showToast(getActivity(), "Please Enter First Name");
                    }else if (etLastName.getText().toString().trim().isEmpty()) {
                        CommonMethods.showToast(getActivity(), "Please Enter Last Name");
                    } else if (etAddress1.getText().toString().trim().isEmpty()) {
                        CommonMethods.showToast(getActivity(), "Please Enter Address1");
                    }  else if (etPhone.getText().toString().trim().isEmpty()) {
                        CommonMethods.showToast(getActivity(), "Please Enter Phone Number");
                    }  else if (etCity.getText().toString().trim().isEmpty()) {
                        CommonMethods.showToast(getActivity(), "Please Enter City");
                    } else if (etState.getText().toString().trim().isEmpty()) {
                        CommonMethods.showToast(getActivity(), "Please Enter State");
                    } else if (etCountry.getText().toString().trim().isEmpty()) {
                        CommonMethods.showToast(getActivity(), "Please Enter Country");
                    } else {

                        hitEditProfile();
                    }
                } else {
                    CommonMethods.showInternetNotConnectedToast(getActivity());
                }
                break;
        }
    }

    private void hitEditProfile() {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.string_title_upload_progressbar_));
        progressDialog.show();

//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),
//                f);
//
//        // MultipartBody.Part is used to send also the actual file name
//
//        MultipartBody.Part image =
//                MultipartBody.Part.createFormData("image", f.getName(), requestFile);
//        RequestBody name1 =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), fname);
//
//        RequestBody phone =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), phoneno);
//        RequestBody license_plate =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), license_plate_no);
//        RequestBody vehicleId =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), vehicle_id);
//        RequestBody name2 =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), middlename);
//        RequestBody name3 =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), lastname);
//        RequestBody compname =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), company_name);
//        RequestBody add1 =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), address1);
//        RequestBody add2 =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), address2);
//        RequestBody add3 =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), address3);
//        RequestBody fax =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), fax_no);
//        RequestBody City =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), city);
//        RequestBody State =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), state);
//        RequestBody Country =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), country);


        Call<EditProfile> profileCall;
        if (imageProfile == null) {
            profileCall = RestClient.get().editProfile(RetrofitUtils.stringToRequestBody(fNameP),
                    RetrofitUtils.stringToRequestBody(phone_noP),
                    RetrofitUtils.stringToRequestBody(LNameP),
                    RetrofitUtils.stringToRequestBody(MNameP),
                    RetrofitUtils.stringToRequestBody(compNameP),
                    RetrofitUtils.stringToRequestBody(add1P),
                    RetrofitUtils.stringToRequestBody(add2P),
                    RetrofitUtils.stringToRequestBody(add3P),
                    RetrofitUtils.stringToRequestBody(cityP),
                    RetrofitUtils.stringToRequestBody(stateP),
                    RetrofitUtils.stringToRequestBody(countryP));
        } else
            profileCall = RestClient.get().editProfileWithImage(RetrofitUtils.stringToRequestBody(fNameP),
                    RetrofitUtils.imageToRequestBody(imageProfile, "image"),
                    RetrofitUtils.stringToRequestBody(phone_noP),
                    RetrofitUtils.stringToRequestBody(LNameP),
                    RetrofitUtils.stringToRequestBody(MNameP),
                    RetrofitUtils.stringToRequestBody(compNameP),
                    RetrofitUtils.stringToRequestBody(add1P),
                    RetrofitUtils.stringToRequestBody(add2P),
                    RetrofitUtils.stringToRequestBody(add3P),
                    RetrofitUtils.stringToRequestBody(cityP),
                    RetrofitUtils.stringToRequestBody(stateP),
                    RetrofitUtils.stringToRequestBody(countryP));

        profileCall.enqueue(new Callback<EditProfile>() {
            @Override
            public void onResponse(Call<EditProfile> call, Response<EditProfile> response) {
                if (response.isSuccessful()) {
                    ApplicationGlobal.prefsManager.setProfile(new Gson().toJson(response.body()));
                    Toast.makeText(getActivity(), response.body().getImage(), Toast.LENGTH_LONG).show();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_frame, new ProfileFragment())
                            .commit();
                    ApplicationGlobal.prefsManager.setPhoneNo(phone_noP);
                    progressDialog.dismiss();
                    Log.e("Image", response.body().getImage());
//                    getFragmentManager().popBackStackImmediate();
                } else {
                    try {
                        CommonMethods.showErrorMessage(getActivity(), response.errorBody());
                        progressDialog.dismiss();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<EditProfile> call, Throwable t) {
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?

            // Show an expanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.


            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_CAMERA_HARDWARE);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        } else if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Show an expanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.


            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_CAMERA_STORAGE);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        } else {
            showPictureOptionsDialog();
            // selectImage();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_CAMERA_HARDWARE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    checkPermission();

                }
            }
            case MY_PERMISSIONS_CAMERA_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    showPictureOptionsDialog();
                    //selectImage();

                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void showPictureOptionsDialog() {
        new MaterialDialog.Builder(getActivity())
                .theme(Theme.LIGHT)
                .items(R.array.media_options)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view,
                                            int which, CharSequence text) {
                        switch (which) {
                            case 0:
                                Intent i = new Intent(
                                        Intent.ACTION_PICK,
                                        MediaStore.Images.Media
                                                .EXTERNAL_CONTENT_URI);
                                startActivityForResult(i,
                                        Constants.GALLERY_REQUEST);
                                break;
                            case 1:
                                Intent takePictureIntent = new Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE);
                                File f;
                                try {
                                    f = CommonMethods.setUpImageFile(Constants
                                            .LOCAL_STORAGE_BASE_PATH_FOR_USER_PHOTOS);
                                    assert f != null;
                                    picturePath = f.getAbsolutePath();
                                   /*takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                            Uri.fromFile(f));*/
                                    //for nougat we use FileProvider(store in xml folder and manifest.xml)
                                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                            FileProvider.getUriForFile(getActivity(), getActivity()
                                                    .getApplicationContext().getPackageName() +
                                                    ".provider", f));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    picturePath = null;
                                }
                                startActivityForResult(takePictureIntent,
                                        Constants.CAMERA_REQUEST);
                                break;
                            case 2:
                                dialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                })
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (resultCode == Activity.RESULT_OK) {

                boolean isGalleryImage = false;
                if (requestCode == Constants.GALLERY_REQUEST) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    isGalleryImage = true;
                }
                new GetSampledImage(this).execute(picturePath,
                        Constants.LOCAL_STORAGE_BASE_PATH_FOR_USER_PHOTOS,
                        String.valueOf(isGalleryImage),
                        String.valueOf((int) getResources()
                                .getDimension(R.dimen.app_bar_height)));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSampledImageAsyncPostExecute(File file) {
        imageProfile = file;
        if (imageProfile != null) {
            ivProfileUpload.setImageURI(Uri.parse(Constants.LOCAL_FILE_PREFIX +
                    imageProfile));
        }
    }


//    private void hitEditProfile(String fname, final String phoneno, String license_plate_no, String
//            vehicle_id, String lastname, String middlename, String company_name, String address1,
//                                String address2, String address3, String fax_no, String city,
//                                String state, String country) {
//        final ProgressDialog progressDialog;
//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage(getString(R.string.string_title_upload_progressbar_));
//        progressDialog.show();
//
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),
//                f);
//
//        // MultipartBody.Part is used to send also the actual file name
//
//        MultipartBody.Part image =
//                MultipartBody.Part.createFormData("image", f.getName(), requestFile);
//        RequestBody name1 =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), fname);
//
//        RequestBody phone =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), phoneno);
//        RequestBody license_plate =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), license_plate_no);
//        RequestBody vehicleId =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), vehicle_id);
//        RequestBody name2 =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), middlename);
//        RequestBody name3 =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), lastname);
//        RequestBody compname =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), company_name);
//        RequestBody add1 =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), address1);
//        RequestBody add2 =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), address2);
//        RequestBody add3 =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), address3);
//        RequestBody fax =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), fax_no);
//        RequestBody City =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), city);
//        RequestBody State =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), state);
//        RequestBody Country =
//                RequestBody.create(
//                        MediaType.parse("text/pain"), country);
//
//
//
//        RestClient.get().EditProfileResponse(name1, image,
//                phone, license_plate, vehicleId, name2, name3, compname, add1, add2, add3, fax, City,
//                State, Country).enqueue(new Callback<EditProfile>() {
//            @Override
//            public void onResponse(Call<EditProfile> call, Response<EditProfile> response) {
//
//                progressDialog.dismiss();
//
//                if (response.code() == 200 && response.body() != null) {
//                    Toast.makeText(getActivity(), response.body().getCustomerId(), Toast.LENGTH_LONG)
//                            .show();
//                    ApplicationGlobal.prefsManager.setProfile(new Gson().toJson(response.body()));
//                    ProfileData list = new ProfileData(fNameP, LNameP, MNameP, phone_noP, cityP,
//                            faxP, stateP, countryP, add1P, add2P, add3P, compNameP, CustIdP);
//
//                    Intent intent = new Intent("UPDATE");
//                    intent.putExtra("Data_New", list);
//                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
//                    getFragmentManager().popBackStackImmediate();
//                }
//                picturePath = "";
//
//            }
//
//            @Override
//            public void onFailure(Call<EditProfile> call, Throwable t) {
//                progressDialog.dismiss();
//
//            }
//
//
//        });
//
//    }


}
