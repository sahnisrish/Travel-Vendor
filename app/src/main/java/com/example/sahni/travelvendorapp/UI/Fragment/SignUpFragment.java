package com.example.sahni.travelvendorapp.UI.Fragment;


import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sahni.travelvendorapp.Constant;
import com.example.sahni.travelvendorapp.R;
import com.example.sahni.travelvendorapp.UI.Fragment.Callbacks.SignUpStartUpCallback;
import com.example.sahni.travelvendorapp.ViewModels.StartUpViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    View rootLayout;
    SignUpStartUpCallback callback;
    Button register;
    EditText name;
    EditText rePassword;
    EditText phone;
    EditText password;
    StartUpViewModel viewModel;
    ProgressBar progressBar;
    ConstraintLayout signUpDetails;
    TextView numberDriver;
    TextView numberVehicle;
    TextView addVehicle;
    TextView addDriver;
    TextView text;

    public SignUpFragment() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public SignUpFragment(SignUpStartUpCallback callback){
        this.callback=callback;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootLayout=inflater.inflate(R.layout.fragment_sign_up, container, false);
        viewModel= ViewModelProviders.of(getActivity()).get(StartUpViewModel.class);

        register=rootLayout.findViewById(R.id.sign_up);
        name=rootLayout.findViewById(R.id.name);
        rePassword =rootLayout.findViewById(R.id.re_password);
        phone=rootLayout.findViewById(R.id.number);
        password=rootLayout.findViewById(R.id.password);
        progressBar=rootLayout.findViewById(R.id.progress);
        signUpDetails=rootLayout.findViewById(R.id.sign_up_details);
        numberDriver=rootLayout.findViewById(R.id.number_drivers);
        numberVehicle=rootLayout.findViewById(R.id.number_vehicles);
        addDriver=rootLayout.findViewById(R.id.add_driver);
        addVehicle=rootLayout.findViewById(R.id.add_vehicle);
        text=rootLayout.findViewById(R.id.text);

        SetDetails();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int k=FetchDetails();
                if(k==1)
                    callback.createAccount(Constant.REGISTER);
                else if(k==-1)
                    Toast.makeText(getContext(),"Empty fields not allowed",Toast.LENGTH_SHORT).show();
            }
        });
        addDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.addDriver(Constant.ADD_DRIVER);
            }
        });
        addVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.addVehicle(Constant.ADD_VEHICLE);
            }
        });

        return rootLayout;
    }

    private void SetDetails() {
        if(viewModel.getPhone()!=null)
            phone.setText(viewModel.getPhone().getValue()+"");
    }

    private int FetchDetails() {
        String nameData=name.getText().toString();
        String rePasswordData=rePassword.getText().toString();
        String passwordData=password.getText().toString();
        String phoneData=phone.getText().toString();

        if(!(nameData.equals("")||rePasswordData.equals("")||passwordData.equals("")||phoneData.equals(""))) {
            if(passwordData.equals(rePasswordData) && phoneData.length()==10){
                viewModel.setName(nameData);
                viewModel.setPassword(passwordData);
                viewModel.setPhone(phoneData);
                return 1;
            }
            else {
                if(phoneData.length()!=10)
                    Toast.makeText(getContext(),"Invalid Phone Number",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
                return 0;
            }
        }
        if(numberVehicle.getText().toString().equals("0")||numberDriver.getText().toString().equals("0")){
            Toast.makeText(getContext(),"Add atleast one Driver and Vehicle",Toast.LENGTH_SHORT).show();
            ChangeTextColor();
            return 0;
        }
        return -1;
    }

    public void ChangeTextColor() {

    }

    public void ToggleProgress(){
        if(progressBar.getVisibility()==View.VISIBLE){
            progressBar.setVisibility(View.GONE);
            signUpDetails.setAlpha(1f);
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            signUpDetails.setAlpha(.3f);
        }
    }

}
