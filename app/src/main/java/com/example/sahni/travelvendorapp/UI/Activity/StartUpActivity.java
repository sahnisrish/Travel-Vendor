package com.example.sahni.travelvendorapp.UI.Activity;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.sahni.travelvendorapp.Constant;
import com.example.sahni.travelvendorapp.Data.Vendor;
import com.example.sahni.travelvendorapp.R;
import com.example.sahni.travelvendorapp.UI.Fragment.Callbacks.LoginStartUpCallBack;
import com.example.sahni.travelvendorapp.UI.Fragment.Callbacks.SignUpStartUpCallback;
import com.example.sahni.travelvendorapp.UI.Fragment.LoginFragment;
import com.example.sahni.travelvendorapp.UI.Fragment.SignUpFragment;
import com.example.sahni.travelvendorapp.UI.Fragment.WelcomeFragment;
import com.example.sahni.travelvendorapp.ViewModels.StartUpViewModel;

import java.util.ArrayList;

public class StartUpActivity extends AppCompatActivity implements LoginStartUpCallBack, SignUpStartUpCallback {

    Window window;
    FrameLayout fragmentHolder;
    Fragment openFragment;
    SharedPreferences sharedPreferences;
    StartUpViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        viewModel= ViewModelProviders.of(this).get(StartUpViewModel.class);

        sharedPreferences=getSharedPreferences(Constant.SHARED_PREFRENCE,MODE_PRIVATE);
        viewModel.setLogIn(sharedPreferences.getBoolean(Constant.LOGGEDIN_KEY,false));

        fragmentHolder=findViewById(R.id.fragment_holder);
        viewModel.setFirstAccess(getIntent().getBooleanExtra(Constant.FIRST_ACCESS,true));
        if(viewModel.getFirstAccess())
            setWelcomeFragment();
        else
            setLoginFragment();
    }

    private void setWelcomeFragment() {
        viewModel.setFirstAccess(false);
        openFragment=new WelcomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_holder,openFragment).commitAllowingStateLoss();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(viewModel.loggedIn()==false) {
                    setLoginFragment();
                }
                else {
                    OpenHomeActivity();
                }
            }
        },2500);
    }

    private void OpenHomeActivity() {
        Intent intent=new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void setLoginFragment() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        openFragment=new LoginFragment(this);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_holder,openFragment).commitAllowingStateLoss();
    }

    @Override
    public void login(int code) {
        if(code== Constant.LOGIN){
            ((LoginFragment)openFragment).ToggleProgress();
            viewModel.fetchVendor().observe(this, new Observer<Vendor>() {
                @Override
                public void onChanged(@Nullable Vendor vendor) {
                    ((LoginFragment)openFragment).ToggleProgress();
                    if(vendor==null){
                        viewModel.setLogIn(false);
                        Toast.makeText(StartUpActivity.this,"Invalid Phone Number",Toast.LENGTH_SHORT).show();
                    }
                    else if(vendor.getPassword().equals(viewModel.getPassword().getValue())) {
                        viewModel.setLogIn(true);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.clear();
                        editor.putBoolean(Constant.LOGGEDIN_KEY,true);
                        editor.putString(Constant.PASSWORD_KEY,vendor.getPassword());
                        editor.putLong(Constant.PHONE_KEY,vendor.getPhone());
                        editor.commit();
                        OpenHomeActivity();
                    }
                    else {
                        viewModel.setLogIn(false);
                        Toast.makeText(StartUpActivity.this,"Invalid Password",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    @Override
    public void register(int code) {
        if(code==Constant.REGISTER){
            openFragment=new SignUpFragment(this);
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_holder,openFragment).commitAllowingStateLoss();
        }
    }

    @Override
    public void createAccount(int code) {
        if(code==Constant.REGISTER){
            ((SignUpFragment)openFragment).ToggleProgress();
            viewModel.fetchVendor().observe(this, new Observer<Vendor>() {
                @Override
                public void onChanged(@Nullable Vendor vendor) {
                    if(vendor==null){
                        viewModel.createAccount().observe(StartUpActivity.this, new Observer<Boolean>() {
                            @Override
                            public void onChanged(@Nullable Boolean aBoolean) {
                                ((SignUpFragment)openFragment).ToggleProgress();
                                if(aBoolean){
                                    closeExtraFragments();
                                    Toast.makeText(StartUpActivity.this,"Account Created",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(StartUpActivity.this,"Error while creating account",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else {
                        ((SignUpFragment)openFragment).ToggleProgress();
                        Toast.makeText(StartUpActivity.this, "Account already exists", Toast.LENGTH_SHORT).show();
                        viewModel.deleteVendor();
                    }
                }
            });
        }
    }

    @Override
    public void addDriver(int code) {

    }

    @Override
    public void addVehicle(int code) {

    }

    @Override
    public void onBackPressed() {
        if(openFragment instanceof LoginFragment){
            super.onBackPressed();
        }
        else {
            AlertDialog.Builder builder=new AlertDialog.Builder(this)
                    .setTitle("Leave Page?")
                    .setMessage("Do you want to quit this page without task completion?")
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            closeExtraFragments();
                        }
                    });
            builder.show();
        }
    }

    private void closeExtraFragments() {
        ArrayList<Fragment> fragments=new ArrayList<>(getSupportFragmentManager().getFragments());
        for(int i=0;i<fragments.size();i++){
            if(fragments.get(i) instanceof LoginFragment)
                openFragment=fragments.get(i);
            else
                getSupportFragmentManager().beginTransaction().remove(fragments.get(i)).commitAllowingStateLoss();
        }
        ((LoginFragment)openFragment).refresh();
    }

}
