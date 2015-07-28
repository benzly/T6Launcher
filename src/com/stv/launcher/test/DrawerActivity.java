package com.stv.launcher.test;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import com.example.t6launcher.R;
import com.stv.launcher.widget.AppIconDrawer;
import com.stv.launcher.widget.FocusIndicatorView;

public class DrawerActivity extends Activity {

    AppIconDrawer mDrawer;
    Button trunbig, uninstall, requestfocus, refresh;
    FocusIndicatorView mFocusIndicatorView;

    boolean tag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawerlayout);
        getWindow().getDecorView().setBackgroundColor(Color.GRAY);


        mDrawer = (AppIconDrawer) findViewById(R.id.drawer);
        mFocusIndicatorView = (FocusIndicatorView) findViewById(R.id.focus_indicator);
        mDrawer.setFocusable(true);
        mDrawer.setOnFocusChangeListener(mFocusIndicatorView);

        trunbig = (Button) findViewById(R.id.trun_big);
        uninstall = (Button) findViewById(R.id.uninstall);
        requestfocus = (Button) findViewById(R.id.move);
        refresh = (Button) findViewById(R.id.refresh_data);

        requestfocus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TranslateAnimation ta = new TranslateAnimation(0, 200, 0, 200);
                ta.setDuration(1000);
                ScaleAnimation sa = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f);
                sa.setDuration(1000);
                AnimationSet as = new AnimationSet(true);
                as.addAnimation(ta);
                as.addAnimation(sa);
                mDrawer.startAnimation(ta);
            }
        });
        uninstall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = !tag;
                mDrawer.showUninstallTag(tag);
            }
        });


        trunbig.setOnFocusChangeListener(mFocusIndicatorView);
        uninstall.setOnFocusChangeListener(mFocusIndicatorView);
        requestfocus.setOnFocusChangeListener(mFocusIndicatorView);
        refresh.setOnFocusChangeListener(mFocusIndicatorView);
    }
}
