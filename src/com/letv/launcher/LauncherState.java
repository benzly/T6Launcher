package com.letv.launcher;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.stv.launcher.activity.Launcher;
import com.stv.launcher.compat.LauncherAppsCompat;
import com.stv.launcher.compat.PackageInstallerCompat;
import com.stv.launcher.compat.PackageInstallerCompat.PackageInstallInfo;
import com.stv.launcher.provider.LauncherProvider;
import com.stv.launcher.utils.AppFilter;
import com.stv.launcher.utils.BuildInfo;
import com.stv.launcher.utils.IconCache;
import com.stv.launcher.utils.LauncherFiles;
import com.stv.launcher.utils.Utilities;

public class LauncherState implements DeviceProfile.DeviceProfileCallbacks {
    private static final String TAG = LauncherState.class.getSimpleName();
    private static final boolean DEBUG = false;

    private final AppFilter mAppFilter;
    private final BuildInfo mBuildInfo;
    private final LauncherModel mModel;
    private final IconCache mIconCache;

    private final float mScreenDensity;
    private final int mLongPressTimeout = 300;

    private static WeakReference<LauncherProvider> sLauncherProvider;
    private static Context sContext;

    private static LauncherState INSTANCE;

    private DynamicGrid mDynamicGrid;

    public static LauncherState getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LauncherState();
        }
        return INSTANCE;
    }

    public static LauncherState getInstanceNoCreate() {
        return INSTANCE;
    }

    public Context getContext() {
        return sContext;
    }

    public static void setApplicationContext(Context context) {
        if (sContext != null) {
            Log.w(Launcher.TAG, "setApplicationContext called twice! old=" + sContext + " new=" + context);
        }
        sContext = context.getApplicationContext();
    }

    private LauncherState() {
        if (sContext == null) {
            throw new IllegalStateException("LauncherAppState inited before app context set");
        }
        Log.v(Launcher.TAG, "LauncherAppState inited");

        // mScreenDensity *before* creating icon cache
        mScreenDensity = sContext.getResources().getDisplayMetrics().density;

        mIconCache = new IconCache(sContext);

        mAppFilter = AppFilter.loadByName(null);
        mBuildInfo = BuildInfo.loadByName(null);
        mModel = new LauncherModel(this, mIconCache, mAppFilter);
        final LauncherAppsCompat launcherApps = LauncherAppsCompat.getInstance(sContext);
        launcherApps.addOnAppsChangedCallback(mModel);

        // Register intent receivers
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_LOCALE_CHANGED);
        filter.addAction(Intent.ACTION_CONFIGURATION_CHANGED);
        sContext.registerReceiver(mModel, filter);
        filter = new IntentFilter();
        filter.addAction(SearchManager.INTENT_GLOBAL_SEARCH_ACTIVITY_CHANGED);
        sContext.registerReceiver(mModel, filter);
        filter = new IntentFilter();
        filter.addAction(SearchManager.INTENT_ACTION_SEARCHABLES_CHANGED);
        sContext.registerReceiver(mModel, filter);


        ContentResolver resolver = sContext.getContentResolver();
        // TODO
        // resolver.registerContentObserver(LauncherSettings.Favorites.CONTENT_URI, true,
        // mFavoritesObserver);
    }

    /**
     * Call from Application.onTerminate(), which is not guaranteed to ever be called.
     */
    public void onTerminate() {
        sContext.unregisterReceiver(mModel);
        final LauncherAppsCompat launcherApps = LauncherAppsCompat.getInstance(sContext);
        launcherApps.removeOnAppsChangedCallback(mModel);
        PackageInstallerCompat.getInstance(sContext).onStop();

        ContentResolver resolver = sContext.getContentResolver();
        resolver.unregisterContentObserver(mFavoritesObserver);
    }

    /**
     * Receives notifications whenever the user favorites have changed.
     */
    private final ContentObserver mFavoritesObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            // If the database has ever changed, then we really need to force a reload of the
            // workspace on the next load
            mModel.resetLoadedState(false, true);
            mModel.startLoaderFromBackground();
        }
    };

    public LauncherModel setLauncher(Launcher launcher) {
        mModel.initialize(launcher);
        return mModel;
    }

    public IconCache getIconCache() {
        return mIconCache;
    }

    public LauncherModel getModel() {
        return mModel;
    }

    boolean shouldShowAppOrWidgetProvider(ComponentName componentName) {
        return mAppFilter == null || mAppFilter.shouldShowApp(componentName);
    }

    public static void setLauncherProvider(LauncherProvider provider) {
        sLauncherProvider = new WeakReference<LauncherProvider>(provider);
    }

    static LauncherProvider getLauncherProvider() {
        return sLauncherProvider.get();
    }

    public static String getSharedPreferencesKey() {
        return LauncherFiles.SHARED_PREFERENCES_KEY;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public DeviceProfile initDynamicGrid(Context context) {
        mDynamicGrid = createDynamicGrid(context, mDynamicGrid);
        mDynamicGrid.getDeviceProfile().addCallback(this);
        return mDynamicGrid.getDeviceProfile();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    static DynamicGrid createDynamicGrid(Context context, DynamicGrid dynamicGrid) {
        // Determine the dynamic grid properties
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point realSize = new Point();
        display.getRealSize(realSize);
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);

        if (dynamicGrid == null) {
            Point smallestSize = new Point();
            Point largestSize = new Point();
            display.getCurrentSizeRange(smallestSize, largestSize);

            dynamicGrid =
                    new DynamicGrid(context, context.getResources(), Math.min(smallestSize.x, smallestSize.y), Math.min(largestSize.x, largestSize.y),
                            realSize.x, realSize.y, dm.widthPixels, dm.heightPixels);
        }

        // Update the icon size
        DeviceProfile grid = dynamicGrid.getDeviceProfile();
        grid.updateFromConfiguration(context, context.getResources(), realSize.x, realSize.y, dm.widthPixels, dm.heightPixels);
        return dynamicGrid;
    }

    public DynamicGrid getDynamicGrid() {
        return mDynamicGrid;
    }

    public static boolean isScreenLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public float getScreenDensity() {
        return mScreenDensity;
    }

    public int getLongPressTimeout() {
        return mLongPressTimeout;
    }

    public void onWallpaperChanged() {

    }

    public boolean hasWallpaperChangedSinceLastCheck() {
        return false;
    }

    @Override
    public void onAvailableSizeChanged(DeviceProfile grid) {
        Utilities.setIconSize(grid.iconSizePx);
    }

    public static boolean isDisableAllApps() {
        return false;
    }

    public static boolean isDogfoodBuild() {
        return getInstance().mBuildInfo.isDogfoodBuild();
    }

    public void setPackageState(ArrayList<PackageInstallInfo> installInfo) {
        mModel.setPackageState(installInfo);
    }

    /**
     * Updates the icons and label of all icons for the provided package name.
     */
    public void updatePackageBadge(String packageName) {
        mModel.updatePackageBadge(packageName);
    }
}
