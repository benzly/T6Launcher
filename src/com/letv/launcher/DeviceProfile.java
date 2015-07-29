/*
 * Copyright (C) 2008 The Android Open Source Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.letv.launcher;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


class DeviceProfileQuery {
    DeviceProfile profile;
    float widthDps;
    float heightDps;
    float value;
    PointF dimens;

    DeviceProfileQuery(DeviceProfile p, float v) {
        widthDps = p.minWidthDps;
        heightDps = p.minHeightDps;
        value = v;
        dimens = new PointF(widthDps, heightDps);
        profile = p;
    }
}


public class DeviceProfile {
    public static interface DeviceProfileCallbacks {
        public void onAvailableSizeChanged(DeviceProfile grid);
    }

    String name;
    float minWidthDps;
    float minHeightDps;
    public float numRows;
    public float numColumns;
    float numHotseatIcons;
    float iconSize;
    private float iconTextSize;
    private int iconDrawablePaddingOriginalPx;
    private float hotseatIconSize;

    int defaultLayoutId;

    boolean isLandscape;
    boolean isTablet;
    boolean isLargeTablet;
    boolean isLayoutRtl;
    boolean transposeLayoutWithOrientation;


    int widthPx;
    int heightPx;
    int availableWidthPx;
    int availableHeightPx;
    int defaultPageSpacingPx;

    int overviewModeMinIconZoneHeightPx;
    int overviewModeMaxIconZoneHeightPx;
    int overviewModeBarItemWidthPx;
    int overviewModeBarSpacerWidthPx;
    float overviewModeIconZoneRatio;
    float overviewModeScaleFactor;

    public int iconSizePx;
    int iconTextSizePx;
    int iconDrawablePaddingPx;
    int cellWidthPx;
    int cellHeightPx;
    int allAppsIconSizePx;
    int allAppsIconTextSizePx;
    int allAppsCellWidthPx;
    int allAppsCellHeightPx;
    int allAppsCellPaddingPx;
    int folderBackgroundOffset;
    int folderIconSizePx;
    int folderCellWidthPx;
    int folderCellHeightPx;
    int hotseatCellWidthPx;
    int hotseatCellHeightPx;
    int hotseatIconSizePx;
    int hotseatBarHeightPx;
    int hotseatAllAppsRank;
    int allAppsNumRows;
    int allAppsNumCols;
    int searchBarSpaceWidthPx;
    int searchBarSpaceHeightPx;
    int pageIndicatorHeightPx;
    int allAppsButtonVisualSize;

    float dragViewScale;

    int allAppsShortEdgeCount = -1;
    int allAppsLongEdgeCount = -1;

    private ArrayList<DeviceProfileCallbacks> mCallbacks = new ArrayList<DeviceProfileCallbacks>();

    DeviceProfile(String n, float w, float h, float r, float c, float is, float its, float hs, float his, int dlId) {
        // Ensure that we have an odd number of hotseat items (since we need to place all apps)
        if (!LauncherAppState.isDisableAllApps() && hs % 2 == 0) {
            throw new RuntimeException("All Device Profiles must have an odd number of hotseat spaces");
        }

        name = n;
        minWidthDps = w;
        minHeightDps = h;
        numRows = r;
        numColumns = c;
        iconSize = is;
        iconTextSize = its;
        numHotseatIcons = hs;
        hotseatIconSize = his;
        defaultLayoutId = dlId;
    }

    DeviceProfile() {}

    DeviceProfile(Context context, ArrayList<DeviceProfile> profiles, float minWidth, float minHeight, int wPx, int hPx, int awPx,
            int ahPx, Resources res) {
        DisplayMetrics dm = res.getDisplayMetrics();
        // TODO
        // 加载一些默认数据
        iconSizePx = 200;
    }



    void addCallback(DeviceProfileCallbacks cb) {
        mCallbacks.add(cb);
        cb.onAvailableSizeChanged(this);
    }

    void removeCallback(DeviceProfileCallbacks cb) {
        mCallbacks.remove(cb);
    }

    public void updateFromConfiguration(Context context, Resources resources, int wPx, int hPx, int awPx, int ahPx) {
        Configuration configuration = resources.getConfiguration();
    }

    int getWorkspacePageSpacing(int orientation) {
        return 0;
    }

    boolean isX55() {
        return false;
    }

    int getVisibleChildCount(ViewGroup parent) {
        int visibleChildren = 0;
        for (int i = 0; i < parent.getChildCount(); i++) {
            if (parent.getChildAt(i).getVisibility() != View.GONE) {
                visibleChildren++;
            }
        }
        return visibleChildren;
    }

    public void layout(Launcher launcher) {
        // Layout the search bar space

        // Layout the hotseat

        // Layout the page indicators

        // Layout AllApps

        // Layout the Overview Mode
    }
}
