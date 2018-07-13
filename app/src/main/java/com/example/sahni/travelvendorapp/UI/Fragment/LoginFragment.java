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
import com.example.sahni.travelvendorapp.UI.Fragment.Callbacks.LoginStartUpCallBack;
import com.example.sahni.travelvendorapp.ViewModels.StartUpViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    View rootView;
    EditText phoneNo;
    EditText password;
    ProgressBar progressBar;
    ConstraintLayout loginDetails;
    Button login;
    TextView signUp;
    LoginStartUpCallBack callBack;
    StartUpViewModel viewModel;

    public LoginFragment() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public LoginFragment(LoginStartUpCallBack callBack){
        this.callBack=callBack;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_login, container, false);

        viewModel= ViewModelProviders.of(getActivity()).get(StartUpViewModel.class);
        phoneNo=rootView.findViewById(R.id.phone);
        password=rootView.findViewById(R.id.password);
        login=rootView.findViewById(R.id.login);
        signUp=rootView.findViewById(R.id.sign_up);
        progressBar=rootView.findViewById(R.id.progress);
        loginDetails=rootView.findViewById(R.id.LoginDetails);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FetchData()==0)
                    callBack.login(Constant.LOGIN);
                else
                    Toast.makeText(getContext(), "Empty Fields Not Allowed", Toast.LENGTH_SHORT).show();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.register(Constant.REGISTER);
            }
        });
        return rootView;
    }

    private int FetchData() {
        String password=this.password.getText().toString();
        String phoneNo=this.phoneNo.getText().toString();
        if(phoneNo.equals("")||password.equals("")) {
            return -1;
        }
        else {
            viewModel.setPassword(password);
            viewModel.setPhone(phoneNo);
            return 0;
        }
    }
    public void ToggleProgress(){
        if(progressBar.getVisibility()==View.VISIBLE){
            progressBar.setVisibility(View.GONE);
            loginDetails.setAlpha(1f);
        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            loginDetails.setAlpha(.3f);
        }
    }

    public void refresh() {
        if(viewModel.getPassword()!=null)
            password.setText(viewModel.getPassword().getValue());
        if(viewModel.getPhone()!=null)
            phoneNo.setText(viewModel.getPhone().getValue()+"");
    }
}
