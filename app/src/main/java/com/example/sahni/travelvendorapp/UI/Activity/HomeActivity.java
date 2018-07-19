package com.example.sahni.travelvendorapp.UI.Activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sahni.travelvendorapp.Constant;
import com.example.sahni.travelvendorapp.Data.Job;
import com.example.sahni.travelvendorapp.Data.Vendor;
import com.example.sahni.travelvendorapp.Graphics.ColorShades;
import com.example.sahni.travelvendorapp.Graphics.CustomPageColorTransformer;
import com.example.sahni.travelvendorapp.R;
import com.example.sahni.travelvendorapp.UI.Fragment.BookingFragment;
import com.example.sahni.travelvendorapp.UI.Fragment.DriversFragment;
import com.example.sahni.travelvendorapp.UI.Fragment.VehiclesFragment;
import com.example.sahni.travelvendorapp.UI.ViewPagerAdapter.HomePagerAdapter;
import com.example.sahni.travelvendorapp.UI.ListAdapter.CallBacks.BookingCallback;
import com.example.sahni.travelvendorapp.UI.ListAdapter.CallBacks.DriverCallback;
import com.example.sahni.travelvendorapp.UI.ListAdapter.CallBacks.VehiclesCallBack;
import com.example.sahni.travelvendorapp.ViewModels.HomeViewModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BookingCallback, DriverCallback, VehiclesCallBack {

    HomeViewModel viewModel;
    Toolbar toolbar;
    DrawerLayout drawer;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window=getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_home);
        viewModel= ViewModelProviders.of(this).get(HomeViewModel.class);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Travel Vendor");

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(new HomePagerAdapter(getSupportFragmentManager(),this,this,this));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, float positionOffset, int positionOffsetPixels) {

                View landingBGView = findViewById(R.id.landing_background);
                int colorBg[] = getResources().getIntArray(R.array.landing_bg);


                ColorShades shades = new ColorShades();
                shades.setFromColor(colorBg[position % colorBg.length])
                        .setToColor(colorBg[(position + 1) % colorBg.length])
                        .setShade(positionOffset);

                landingBGView.setBackgroundColor(shades.generate());

            }

            public void onPageSelected(int position) {

            }

            public void onPageScrollStateChanged(int state) {
            }
        });
        viewPager.setPageTransformer(true, new CustomPageColorTransformer());
        viewPager.setOffscreenPageLimit(3);

        viewModel.fetchVendor(getSharedPreferences(Constant.SHARED_PREFRENCE,MODE_PRIVATE)).observe(this, new Observer<Vendor>() {
            @Override
            public void onChanged(@Nullable Vendor vendor) {
                if(vendor==null)
                    logOut();
                else {
                    View headerLayout = navigationView.getHeaderView(0);
                    TextView name = headerLayout.findViewById(R.id.name);
                    name.setText(vendor.getName());
                    TextView phone = headerLayout.findViewById(R.id.phone);
                    phone.setText(vendor.getPhone() + "");
                    viewModel.getBookingFragment().observe(HomeActivity.this, new Observer<BookingFragment>() {
                        @Override
                        public void onChanged(@Nullable BookingFragment bookingFragment) {
                            bookingFragment.refresh();
                        }
                    });
                    viewModel.getDriversFragment().observe(HomeActivity.this, new Observer<DriversFragment>() {
                        @Override
                        public void onChanged(@Nullable DriversFragment driversFragment) {
                            driversFragment.refresh();
                        }
                    });
                    viewModel.getVehiclesFragment().observe(HomeActivity.this, new Observer<VehiclesFragment>() {
                        @Override
                        public void onChanged(@Nullable VehiclesFragment vehiclesFragment) {
                            vehiclesFragment.refresh();
                        }
                    });
                }
            }
        });
    }

    private void logOut() {
        Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        SharedPreferences sharedPreferences=getSharedPreferences(Constant.SHARED_PREFRENCE,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Intent intent=new Intent(this,StartUpActivity.class);
        intent.putExtra(Constant.FIRST_ACCESS,false);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.current_status) {
            Toast.makeText(this,"Show On going ride details",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.my_account) {
            Toast.makeText(this,"Balance and Recharge",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.setting) {
            Toast.makeText(this,"Change password and other options",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.log_out) {
            logOut();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void book(Job job) {
        Toast.makeText(this,job.getId(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPhoto(final CircleImageView profile, String photo) {
        viewModel.fetchPhoto().observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(@Nullable Bitmap bitmap) {
                profile.setImageBitmap(bitmap);
            }
        });
    }
}
