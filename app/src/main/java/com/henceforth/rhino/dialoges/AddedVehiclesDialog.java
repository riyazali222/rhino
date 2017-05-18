


package com.henceforth.rhino.dialoges;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.henceforth.rhino.R;
import com.henceforth.rhino.adapters.NumberPlateAdapter;
import com.henceforth.rhino.utills.ApplicationGlobal;
import com.henceforth.rhino.utills.CommonMethods;
import com.henceforth.rhino.utills.Constants;
import com.henceforth.rhino.utills.RecyclerItemClickListener;
import com.henceforth.rhino.webServices.apis.APIs;
import com.henceforth.rhino.webServices.pojo.AddedVehicle;
import com.henceforth.rhino.webServices.pojo.VehicleListing;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;


public class AddedVehiclesDialog extends DialogFragment {
    EditText etRegistrationNo;
    Toolbar toolbarReg;
    Context mContext;
    RecyclerView rvNumberPlate;
    ArrayList<VehicleListing> numberPlateLists = new ArrayList<>();
    NumberPlateAdapter numberPlateAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_added_vehicle, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        etRegistrationNo = (EditText) getView().findViewById(R.id.etRegistrationNo);
        etRegistrationNo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) ||
                        (actionId == EditorInfo.IME_ACTION_DONE)) {
                    String s = v.getText().toString();
                    Intent intent = new Intent("send_data");
                    intent.putExtra("REGISTRATION_NO", s);
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                    dismiss();
                }
                return false;
            }
        });

    }

    private void init() {
        toolbarReg = (Toolbar) getView().findViewById(R.id.toolbarReg);
        toolbarReg.setNavigationIcon(R.drawable.ic_close_white);
        toolbarReg.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        rvNumberPlate = (RecyclerView) getView().findViewById(R.id.rvNumberPlate);
        rvNumberPlate.setLayoutManager(new LinearLayoutManager(getActivity()));
        numberPlateAdapter = new NumberPlateAdapter(getActivity(), numberPlateLists);
        rvNumberPlate.setAdapter(numberPlateAdapter);
        rvNumberPlate.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        VehicleListing list = numberPlateLists.get(position);
                        String brand = list.getBrand();
                        String licence = list.getLicense_plate_no();
                        String mid = list.getMembership_id();
                        String vin = list.getVehicle_identification_number();
                        String year = String.valueOf(list.getVehicle_year());
                        String model = list.getVehicle_model();
                        String type = list.getType_of_vehicle();
                        String mileage = String.valueOf(list.getVehicle_mileage());

                        AddedVehicle vehicle = new AddedVehicle(licence, mid, brand, year, vin,
                                type, model, mileage);
                        Intent intent = new Intent("send_data");
                        intent.putExtra("Data_New", vehicle);
                        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                        dismiss();
                    }
                }));
        if (CommonMethods.isNetworkConnected(getActivity())) {
            hitVehicleListingApi();
        } else
            CommonMethods.showInternetNotConnectedToast(getActivity());
    }

    private void hitVehicleListingApi() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIs VehiclesApi = retrofit.create(APIs.class);
        final Call<List<VehicleListing>> responce = VehiclesApi.VehicleListingResponse(
                ApplicationGlobal.prefsManager.getSessionID());
        responce.enqueue(new Callback<List<VehicleListing>>() {
            @Override
            public void onResponse(Call<List<VehicleListing>> call, Response<List<VehicleListing>> response) {

                try {
                    if (response.code() == 200 && response.body() != null) {

                        // numberPlateLists.clear();
                        numberPlateLists.addAll(response.body());
                        numberPlateAdapter.notifyDataSetChanged();

                    } else if (response.body() == null) {
                        //tvNoList.setVisibility(View.VISIBLE);
                        // tvNoList.setText("No List Added");

                    }
                    CommonMethods.showErrorMessage(getActivity(),
                            response.errorBody());

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<List<VehicleListing>> call, Throwable t) {
            }


        });
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
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver,
                new IntentFilter("delete_dialog"));

    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        super.onPause();
    }

    //receiving Broadcast
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getDialog().dismiss();
        }
    };
}
