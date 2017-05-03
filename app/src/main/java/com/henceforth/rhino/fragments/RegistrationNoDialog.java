package com.henceforth.rhino.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.henceforth.rhino.R;

/**
 * Created by HOME on 5/2/2017.
 */

public class RegistrationNoDialog extends DialogFragment {
    TextView tvRegisteredNo;
    EditText etRegistrationNo;
    Button btnCancel;
    Button btnSubmit;
    Toolbar toolbarReg;
    ImageView ivtoolbarBack;
    private Context mContext;

   /* public RegistrationNoDialog() {
        mContext = getActivity();
    }
*/
    /*public static RegistrationNoDialog newInstance() {
        return new RegistrationNoDialog();

    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_registration_no, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbarReg = (Toolbar) getView().findViewById(R.id.toolbarReg);
        toolbarReg.setNavigationIcon(R.drawable.ic_close_white);
        toolbarReg.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        }
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }


   /* @OnClick({R.id.tvRegisteredNo, R.id.etRegistrationNo, R.id.btnCancel, R.id.btnSubmit,
            R.id.ivtoolbarBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvRegisteredNo:
                break;
            case R.id.etRegistrationNo:
                break;
            case R.id.btnCancel:
                break;
            case R.id.btnSubmit:
                break;
            case R.id.ivtoolbarBack:
                getActivity().onBackPressed();
                break;
        }
    }*/


}
