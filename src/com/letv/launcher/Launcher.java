package com.letv.launcher;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

import com.stv.launcher.compat.PackageInstallerCompat.PackageInstallInfo;
import com.stv.launcher.compat.UserHandleCompat;
import com.stv.launcher.launcher3widget.PagedView;
import com.stv.launcher.launcher3widget.Workspace;
import com.stv.launcher.widget.CellLayout;
import com.stv.launcher.widget.FocusIndicatorView;
import com.stv.launcher.widget.MetroSpace;
import com.stv.launcher.widget.TabSpace;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Launcher extends Activity implements LauncherModel.Callbacks {

    public static final String TAG = Launcher.class.getSimpleName();

    public static final int SCREEN_COUNT = 5;

    private LauncherModel mLauncherModel;

    private TabSpace mTabSpace;
    private MetroSpace mMetroSpace;
    private FocusIndicatorView mFocusIndicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        LauncherAppState app = LauncherAppState.getInstance();
        // Lazy-initialize the dynamic grid
        DeviceProfile grid = app.initDynamicGrid(this);
        mLauncherModel = app.setLauncher(this);
        setupViews();
        grid.layout(this);
        mLauncherModel.startLoader(true, PagedView.INVALID_RESTORE_PAGE);
    }

    private void setupViews() {
        mFocusIndicatorView = (FocusIndicatorView) findViewById(R.id.focus_indicator);
        mTabSpace = (TabSpace) findViewById(R.id.tabspace);
        mMetroSpace = (MetroSpace) findViewById(R.id.metro_space);
        mTabSpace.setOnTabChangeListener(mMetroSpace);
    }


    public void onClick(View v) {

    }

    @Override
    public void startBinding() {
        Log.d(TAG, "startBinding");
    }

    @Override
    public void bindScreens(HashMap<Long, String> orderedScreenIds) {
        Log.d(TAG, "bindScreens " + orderedScreenIds);
        if (orderedScreenIds != null) {
            for (Long i : orderedScreenIds.keySet()) {
                mTabSpace.addTab(mTabSpace.newTabSpec(orderedScreenIds.get(i)));
            }
        }
    }

    @Override
    public void bindItems(int screenId, ArrayList<ItemInfo> items) {
        Log.d(TAG, "bindItems screenId=" + screenId + " size=" + items);
    }

    @Override
    public void finishBindingItems(boolean upgradePath) {
        Log.d(TAG, "finishBindingItems " + upgradePath);
    }

    @Override
    public void bindAllApplications(ArrayList<AppInfo> apps) {
        Log.d(TAG, "bindAllApplications " + (apps != null ? apps.size() : "null"));
    }

    @Override
    public void bindAppsAdded(ArrayList<Long> newScreens, ArrayList<ItemInfo> addNotAnimated, ArrayList<ItemInfo> addAnimated,
            ArrayList<AppInfo> addedApps) {

    }

    @Override
    public void bindComponentsRemoved(ArrayList<String> packageNames, ArrayList<AppInfo> appInfos, UserHandleCompat user, int reason) {

    }

    @Override
    public void bindAppsUpdated(ArrayList<AppInfo> apps) {

    }

    @Override
    public void bindShortcutsChanged(ArrayList<ShortcutInfo> updated, ArrayList<ShortcutInfo> removed, UserHandleCompat user) {

    }

    @Override
    public void bindPackagesUpdated(ArrayList<Object> widgetsAndShortcuts) {

    }

    @Override
    public void updatePackageState(ArrayList<PackageInstallInfo> installInfo) {

    }

    @Override
    public void updatePackageBadge(String packageName) {

    }

    @Override
    public int getCurrentWorkspaceScreen() {
        return 0;
    }

    @Override
    public boolean setLoadOnResume() {
        return false;
    }

    @Override
    public void bindSearchablesChanged() {

    }

    @Override
    public void onPageBoundSynchronously(int page) {

    }

    @Override
    public void dumpLogsToLocalData() {

    }

    @Override
    public boolean isAllAppsButtonRank(int rank) {
        return false;
    }
}
