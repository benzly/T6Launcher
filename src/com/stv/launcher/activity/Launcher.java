package com.stv.launcher.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.letv.launcher.DeviceProfile;
import com.letv.launcher.LauncherModel;
import com.letv.launcher.LauncherState;
import com.letv.launcher.R;
import com.letv.launcher.ScreenInfo;
import com.stv.launcher.app.AppInfo;
import com.stv.launcher.app.ItemInfo;
import com.stv.launcher.app.ShortcutInfo;
import com.stv.launcher.compat.PackageInstallerCompat.PackageInstallInfo;
import com.stv.launcher.compat.UserHandleCompat;
import com.stv.launcher.fragment.EmptyFragment;
import com.stv.launcher.fragment.LiveFragment;
import com.stv.launcher.fragment.SearchFragment;
import com.stv.launcher.fragment.SpaceAdapter;
import com.stv.launcher.widget.MetroSpace;
import com.stv.launcher.widget.TabSpace;

public class Launcher extends FragmentActivity implements LauncherModel.Callbacks {

    public static final String TAG = Launcher.class.getSimpleName();

    public static final int SCREEN_COUNT = 5;

    private LauncherModel mLauncherModel;

    private TabSpace mTabSpace;
    private MetroSpace mMetroSpace;
    private SpaceAdapter mSpaceAdapter;

    public ArrayList<ScreenInfo> mScreens = new ArrayList<ScreenInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        LauncherState app = LauncherState.getInstance();
        mLauncherModel = app.setLauncher(this);

        setupViews();
        // Lazy-initialize the dynamic grid
        DeviceProfile grid = app.initDynamicGrid(this);
        grid.layout(this);

        mLauncherModel.startLoader(true, MetroSpace.INVALID_RESTORE_PAGE);
    }

    private void setupViews() {
        mTabSpace = (TabSpace) findViewById(R.id.tabspace);
        mMetroSpace = (MetroSpace) findViewById(R.id.metro_space);
        mTabSpace.setLauncher(this);
        mSpaceAdapter = new SpaceAdapter(this, mTabSpace, mMetroSpace);
    }

    public MetroSpace getMetroSpace() {
        return mMetroSpace;
    }

    public TabSpace getTabSpace() {
        return mTabSpace;
    }

    public void onClick(View v) {

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        handleActivityResult(requestCode, resultCode, data);
    }

    private void handleActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == TabSpace.EDIT_TAB_RECODE) {

            // need remove
            for (ScreenInfo info : ScreenManagerActivity.sBeRemoved) {
                mScreens.remove(info);
                mSpaceAdapter.removeTab(info.name);
            }

            // need add
            for (ScreenInfo info : ScreenManagerActivity.sBeAdded) {
                mScreens.add(info);
                mSpaceAdapter.addTab(info.name, EmptyFragment.class, null);
            }
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        boolean handled = super.dispatchKeyEvent(event);
        if (!handled) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                Log.d("xubin", "launcher");
            }
        }
        return handled;
    }

    public void debug() {
        mSpaceAdapter.removeTab(mTabSpace.getTagByTab(0));
    }

    @Override
    public void startBinding() {
        Log.d(TAG, "startBinding");
    }

    @Override
    public void bindScreens(ArrayList<ScreenInfo> orderedScreens) {
        Log.d(TAG, "bindScreens " + orderedScreens);
        if (orderedScreens != null) {
            mScreens.clear();
            mScreens.addAll(orderedScreens);

            int i = 0;
            for (ScreenInfo screen : orderedScreens) {
                if (i == 0) {
                    mSpaceAdapter.addTab(screen.name, LiveFragment.class, null);
                } else {
                    mSpaceAdapter.addTab(screen.name, SearchFragment.class, null);
                }
                i++;
            }
            mTabSpace.setCurrentTab(1);
        }
    }

    @Override
    public void bindItems(int screenId, ArrayList<ItemInfo> items) {
        Log.d(TAG, "bindItems screenId=" + screenId);
        if (items != null) {
            mSpaceAdapter.bindTabItems(screenId, items);
        }
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
    public void bindAppsAdded(ArrayList<Long> newScreens, ArrayList<ItemInfo> addNotAnimated, ArrayList<ItemInfo> addAnimated, ArrayList<AppInfo> addedApps) {

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
