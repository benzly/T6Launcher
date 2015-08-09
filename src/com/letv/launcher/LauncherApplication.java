package com.letv.launcher;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.stv.launcher.service.HeapAnalysisResultService;

public class LauncherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this, HeapAnalysisResultService.class);
        LauncherState.setApplicationContext(this);
        LauncherState.getInstance();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        LauncherState.getInstance().onTerminate();
    }

}
