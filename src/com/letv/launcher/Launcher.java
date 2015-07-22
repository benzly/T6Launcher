package com.letv.launcher;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

import com.example.t6launcher.R;
import com.letv.launcher.widget.CellLayout;
import com.letv.launcher.widget.FocusIndicatorView;
import com.letv.launcher.widget.Workspace;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Launcher extends Activity {

    private Workspace mWorkspace;
    private FocusIndicatorView mFocusIndicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        setupViews();

        // debug
        loadData();
    }

    private void setupViews() {
        mFocusIndicatorView = (FocusIndicatorView) findViewById(R.id.focus_indicator);
        mWorkspace = (Workspace) findViewById(R.id.workspace);
        mWorkspace.setPageSpacing(30);
    }

    ArrayList<CellLayout> cells = new ArrayList<CellLayout>();

    void loadData() {
        for (int i = 0; i < 5; i++) {
            CellLayout screen = (CellLayout) getLayoutInflater().inflate(R.layout.workspace_screen, null);
            screen.setIndex(i);
            mWorkspace.addView(screen);
            if (i != 1) {
                screen.setBackgroundResource(R.drawable.focus_highlight);
                ImageView iv = new ImageView(this);
                iv.setImageResource(R.drawable.ic_setting_3d);
                iv.setFocusable(true);
                iv.setOnFocusChangeListener(mFocusIndicatorView);
                screen.addChild(iv);
            }
            cells.add(screen);
        }
    }

    public void onClick(View v) {

    }
}
