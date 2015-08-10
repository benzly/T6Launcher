package com.letv.launcher;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.widget.FrameLayout.LayoutParams;

import com.stv.launcher.activity.Launcher;
import com.stv.launcher.utils.LeLog;
import com.stv.launcher.widget.MetroViewPager;

import java.util.ArrayList;

class DeviceProfileQuery {
    DeviceProfile profile;
    float value;
    PointF dimens;

    DeviceProfileQuery(DeviceProfile p, float v) {
        value = v;
        profile = p;
    }
}


public class DeviceProfile {

    public static interface DeviceProfileCallbacks {
        public void onAvailableSizeChanged(DeviceProfile grid);
    }

    public String name;
    public int iconSizePx = 200;

    public int screenWidth;
    public int screenHeigth;

    public int tabspace_layout_height;
    public int tabspace_layout_margin_top;
    public int tabspace_item_padding_horizontal_edge;
    public int tabspace_item_margin;
    public int tabspace_item_padding_top_edge;
    public int tabspace_item_padding_bottom_edge;
    public int tabspace_item_normal_textsize;

    public int metro_dynamic_grid_padding_top_edge;
    public int metro_dynamic_grid_padding_horizontal_edge;
    public int metro_dynamic_grid_padding_bottom_edge;

    /** dynamic grid size */
    public Rect gridValidRect;
    /** video size */
    public Rect videoValidRect;

    private ArrayList<DeviceProfileCallbacks> mCallbacks = new ArrayList<DeviceProfileCallbacks>();

    DeviceProfile(String n, float w, float h, float r, float c, float is, float its, float hs, float his, int dlId) {
        name = n;
    }

    DeviceProfile(Context context, ArrayList<DeviceProfile> profiles, float minWidth, float minHeight, int wPx, int hPx, int awPx,
            int ahPx, Resources res) {
        DisplayMetrics dm = res.getDisplayMetrics();

        name = "TV";

        screenWidth = dm.widthPixels;
        screenHeigth = dm.heightPixels;

        tabspace_layout_height = (int) context.getResources().getDimension(R.dimen.tabspace_layout_height);
        tabspace_layout_margin_top = (int) context.getResources().getDimension(R.dimen.tabspace_layout_margin_top);

        tabspace_item_padding_horizontal_edge = (int) context.getResources().getDimension(R.dimen.tabspace_item_padding_horizontal_edge);
        tabspace_item_margin = (int) context.getResources().getDimension(R.dimen.tabspace_item_margin);
        tabspace_item_padding_top_edge = (int) context.getResources().getDimension(R.dimen.tabspace_item_padding_top_edge);
        tabspace_item_padding_bottom_edge = (int) context.getResources().getDimension(R.dimen.tabspace_item_padding_bottom_edge);
        tabspace_item_normal_textsize = (int) context.getResources().getDimension(R.dimen.tabspace_item_normal_textsize);

        metro_dynamic_grid_padding_top_edge = (int) context.getResources().getDimension(R.dimen.metro_dynamic_grid_padding_top_edge);
        metro_dynamic_grid_padding_horizontal_edge =
                (int) context.getResources().getDimension(R.dimen.metro_dynamic_grid_padding_horizontal_edge);
        metro_dynamic_grid_padding_bottom_edge = (int) context.getResources().getDimension(R.dimen.metro_dynamic_grid_padding_bottom_edge);

        gridValidRect =
                new Rect(metro_dynamic_grid_padding_horizontal_edge, metro_dynamic_grid_padding_top_edge, screenWidth
                        - metro_dynamic_grid_padding_horizontal_edge, metro_dynamic_grid_padding_bottom_edge);

        videoValidRect = new Rect(0, 0, screenWidth, screenHeigth);

        LeLog.d("DeviceProfile", toString());
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

    public void layout(Launcher launcher) {
        // Layout the MetroSpace
        MetroViewPager viewPager = launcher.getMetroSpace().getViewPager();
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        viewPager.setLayoutParams(params);

        // Layout the TabSpace
        launcher.getTabSpace().layout(this);
    }

    public int getIconPxSize() {
        return iconSizePx;
    }

    @Override
    public String toString() {
        return "DeviceProfile [name=" + name + ", screenWidth=" + screenWidth + ", screenHeigth=" + screenHeigth + ", gridValidRect="
                + gridValidRect + ", videoValidRect=" + videoValidRect + "]";
    }


}
