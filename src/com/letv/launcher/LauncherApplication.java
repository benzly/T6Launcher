package com.letv.launcher;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

public class LauncherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this, HeapAnalysisResultService.class);
        LauncherAppState.setApplicationContext(this);
        LauncherAppState.getInstance();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        LauncherAppState.getInstance().onTerminate();
    }

}
