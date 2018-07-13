package com.example.sahni.travelvendorapp.Graphics;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.sahni.travelvendorapp.R;

/**
 * Created by sahni on 3/7/18.
 */

public class CustomPageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {

        View rootView = page.findViewById(R.id.view);

        if (position < -1) {
        } else if (position <= 0) {
            if (rootView != null) {
                rootView.setAlpha(1+position);
            }

        } else if (position <= 1) {
            if (rootView != null) {
                rootView.setAlpha(1-position);
            }

        }
    }
}
