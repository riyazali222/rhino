package com.henceforth.rhino.adapters;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.henceforth.rhino.R;
import com.henceforth.rhino.fragments.AddVehicleFragment;
import com.henceforth.rhino.fragments.EditAddedVehicleFragment;
import com.henceforth.rhino.utills.AddVehicles;
import com.henceforth.rhino.utills.ApplicationGlobal;
import com.henceforth.rhino.utills.CommonMethods;
import com.henceforth.rhino.webServices.pojo.VehicleListing;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddVehicleAdapter extends RecyclerView.Adapter<AddVehicleAdapter.MyViewHolder> {

    private List<VehicleListing> vehiclesInfoList;
    private Context mContext;
//    FragmentManager fragmentManager;


    public AddVehicleAdapter(Context mContext, List<VehicleListing> vehiclesInfoList) {
        this.vehiclesInfoList = vehiclesInfoList;
        this.mContext = mContext;
//        fragmentManager = ((ProfileFragment) mContext).getFragmentManager();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_add_vehicles, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final VehicleListing item = vehiclesInfoList.get(position);
       // holder.tvVehicleBrand.setText(item.getBrand());
        holder.tvRegistrationNo.setText(item.getLicense_plate_no());
        holder.tvVehicleName.setText(item.getType_of_vehicle());

    }




    @Override
    public int getItemCount() {
        return vehiclesInfoList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvVehicleName) TextView tvVehicleName;
        @BindView(R.id.tvVehicleBrand) TextView tvVehicleBrand;
        @BindView(R.id.tvRegistrationNo) TextView tvRegistrationNo;

        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);


        }
        @OnClick({R.id.ivDelete, R.id.rootView})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.ivDelete:
                    final Dialog dialog = new Dialog(mContext, R.style.slideFromTopDialog);
                    dialog.setContentView(R.layout.dialog_delete_list);
                    Toolbar dialogToolbar = (Toolbar) dialog.findViewById(R.id.toolbarLogout);
                    dialogToolbar.setNavigationIcon(R.drawable.ic_close_white);
                    dialogToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    final Button btnCancle = (Button) dialog.findViewById(R.id.btnCancle);
                    Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (CommonMethods.isNetworkConnected(mContext)) {
                                final VehicleListing item = vehiclesInfoList.get(getAdapterPosition());
                                vehiclesInfoList.remove(getAdapterPosition());
                                String VehicleId = String.valueOf(item.getUser_vehicle_id());
                                notifyItemRemoved(getAdapterPosition());
                                notifyItemRangeChanged(getAdapterPosition(), vehiclesInfoList.size());
                                Intent i = new Intent("UPDATE");
                                i.putExtra("DELETE", VehicleId);
                                LocalBroadcastManager.getInstance(mContext).sendBroadcast(i);
                                dialog.dismiss();
                            } else
                                CommonMethods.showInternetNotConnectedToast(mContext);

                        }

                    });
                    btnCancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                case R.id.rootView:
                    final VehicleListing item = vehiclesInfoList.get(getAdapterPosition());



                    EditAddedVehicleFragment fragment = new EditAddedVehicleFragment();
                    AddVehicles listing=new AddVehicles(item.getUser_vehicle_id(),
                            item.getLicense_plate_no(),item.getVehicle_identification_number(),
                            item.getMembership_id(), item.getVehicle_mileage(),
                            item.getType_of_vehicle(), item.getVehicle_make_id(),
                            item.getVehicle_model(), item.getVehicle_year(),
                            item.getUser_vehicle_id(),"Brand");
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("list_info", listing);
                    fragment.setArguments(bundle);
                    ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_frame, fragment,"Tag").addToBackStack(null).commit();
                    break;
            }
        }

    }

}
