package com.letv.launcher;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.t6launcher.R;
import com.letv.launcher.widget.CellLayout;
import com.letv.launcher.widget.Workspace;

public class Launcher extends Activity {

    private Workspace mWorkspace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        setupViews();

        // debug
        loadData();
    }

    private void setupViews() {
        mWorkspace = (Workspace) findViewById(R.id.workspace);
        mWorkspace.setPageSpacing(30);
    }

    void loadData() {
        for (int i = 0; i < 5; i++) {
            CellLayout screen = (CellLayout) getLayoutInflater().inflate(R.layout.workspace_screen, null);
            mWorkspace.addView(screen);
            if (i != 1) {
                screen.setBackgroundColor(Color.YELLOW);
            }
        }
    }

    public void onClick(View v) {

    }
}
