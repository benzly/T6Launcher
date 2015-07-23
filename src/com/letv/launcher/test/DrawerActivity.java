package com.letv.launcher.test;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.example.t6launcher.R;

public class DrawerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawerlayout);
        // getWindow().getDecorView().setBackgroundColor(Color.WHITE);
    }
}
