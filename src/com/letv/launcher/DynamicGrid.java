package com.letv.launcher;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.util.ArrayList;


public class DynamicGrid {
    @SuppressWarnings("unused")
    private static final String TAG = "DynamicGrid";

    private DeviceProfile mProfile;
    private float mMinWidth;
    private float mMinHeight;

    static float DEFAULT_ICON_SIZE_DP = 60;
    static float DEFAULT_ICON_SIZE_PX = 0;

    public static float dpiFromPx(int size, DisplayMetrics metrics) {
        float densityRatio = (float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT;
        return (size / densityRatio);
    }

    public static int pxFromDp(float size, DisplayMetrics metrics) {
        return (int) Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, metrics));
    }

    public static int pxFromSp(float size, DisplayMetrics metrics) {
        return (int) Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size, metrics));
    }

    public DynamicGrid(Context context, Resources resources, int minWidthPx, int minHeightPx, int widthPx, int heightPx, int awPx, int ahPx) {
        DisplayMetrics dm = resources.getDisplayMetrics();
        ArrayList<DeviceProfile> deviceProfiles = new ArrayList<DeviceProfile>();
        boolean hasAA = !LauncherState.isDisableAllApps();
        DEFAULT_ICON_SIZE_PX = pxFromDp(DEFAULT_ICON_SIZE_DP, dm);
        deviceProfiles.add(new DeviceProfile("TV", 255, 300, 2, 3, 48, 13, (hasAA ? 3 : 5), 48, R.xml.default_screens));
        mMinWidth = dpiFromPx(minWidthPx, dm);
        mMinHeight = dpiFromPx(minHeightPx, dm);
        mProfile = new DeviceProfile(context, deviceProfiles, mMinWidth, mMinHeight, widthPx, heightPx, awPx, ahPx, resources);
    }

    public DeviceProfile getDeviceProfile() {
        return mProfile;
    }

}
