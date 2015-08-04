package com.stv.launcher.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.letv.launcher.model.ScreenInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetroSpace extends FrameLayout {

    private static final String TAG = MetroSpace.class.getSimpleName();

    private MetroViewPager mViewPager;
    private ArrayList<ScreenInfo> mScreenInfos = new ArrayList<ScreenInfo>();

    public MetroSpace(Context context) {
        this(context, null);
    }

    public MetroSpace(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MetroSpace(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initMetroSpace();
    }

    protected void initMetroSpace() {
        // add viewPager
        mViewPager = new MetroViewPager(getContext());
        // must set default id for viewPager
        mViewPager.setId(99999999);
        mViewPager.setAdapter(new MetroViewPagerAdapter(null));
        mViewPager.setOffscreenPageLimit(2);
        addView(mViewPager);
    }

    public MetroViewPager getViewPager() {
        return mViewPager;
    }

    private View createPage(ScreenInfo screen) {
        Log.d(TAG, "createPage " + screen.name);
        switch (screen.type) {
            case EMPTY:
                TextView t = new TextView(getContext());
                t.setText("empty");
                t.setTextSize(100);
                return t;
            case AUTOMATIC:
                CellLayout cellLayout = new CellLayout(getContext());
                cellLayout.setBackgroundColor(Color.RED);
                return cellLayout;
            case MANUAL:
                if (screen.name.equals("搜索")) {
                    SearchDesktop s = new SearchDesktop(getContext());
                    return s;
                } else {
                    TextView tt = new TextView(getContext());
                    tt.setText("未定义layout");
                    tt.setTextSize(100);
                    return tt;
                }
            default:
                TextView ttt = new TextView(getContext());
                ttt.setText("not match screen type");
                ttt.setTextSize(100);
                return ttt;
        }

    }

    public static ArrayList<ScreenInfo> getDifference(ArrayList<ScreenInfo> old, ArrayList<ScreenInfo> cur) {
        Map<ScreenInfo, Integer> map = new HashMap<ScreenInfo, Integer>(old.size() + cur.size());
        ArrayList<ScreenInfo> shortcutAdds = new ArrayList<ScreenInfo>();
        for (ScreenInfo info : cur) {
            map.put(info, 1);
        }
        for (ScreenInfo info : old) {
            map.put(info, 2);
        }
        for (Map.Entry<ScreenInfo, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                shortcutAdds.add(entry.getKey());
            }
        }
        return shortcutAdds;
    }

    public static ArrayList<ScreenInfo> getCommon(ArrayList<ScreenInfo> old, ArrayList<ScreenInfo> cur) {
        Map<ScreenInfo, Integer> map = new HashMap<ScreenInfo, Integer>(old.size() + cur.size());
        ArrayList<ScreenInfo> shortcutRms = new ArrayList<ScreenInfo>();

        List<ScreenInfo> max = old;
        List<ScreenInfo> min = cur;
        if (cur.size() > old.size()) {
            max = cur;
            min = old;
        }
        for (ScreenInfo info : max) {
            map.put(info, 1);
        }
        for (ScreenInfo info : min) {
            Integer value = map.get(info);
            if (value != null) {
                map.put(info, 2);// 相同的元素,value+1
                continue;
            }
            map.put(info, 1);// 不同的元素，value设为1
        }
        for (Map.Entry<ScreenInfo, Integer> entry : map.entrySet()) {
            if (entry.getValue() != 1) {
                shortcutRms.add(entry.getKey());
            }
        }
        return shortcutRms;
    }

}
