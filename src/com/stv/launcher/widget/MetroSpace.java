package com.stv.launcher.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.letv.launcher.ScreenInfo;
import com.stv.launcher.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetroSpace extends FrameLayout {

    private static final String TAG = MetroSpace.class.getSimpleName();

    private MetroViewPager mViewPager;
    private static final int DEFAULT_VIEWPAGER_ID = 99999;
    private ArrayList<ScreenInfo> mScreenInfos = new ArrayList<ScreenInfo>();

    public static final int EXTRA_EMPTY_SCREEN_ID = 2012;
    public static final int INVALID_RESTORE_PAGE = -1;

    public MetroSpace(Context context) {
        this(context, null);
    }

    public MetroSpace(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MetroSpace(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViewPager = new MetroViewPager(getContext());
        mViewPager.setId(DEFAULT_VIEWPAGER_ID);
        mViewPager.setFocusable(false);
        addView(mViewPager);
    }

    public MetroViewPager getViewPager() {
        return mViewPager;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    public boolean priorityAccessKeyEvent(KeyEvent event) {
        BaseFragment current = mViewPager.getAdapter().getCurrentFragment();
        if (current != null) {
            if (!current.priorityAccessKeyEvent(event)) {
                int action = event.getAction();
                int keyCode = event.getKeyCode();
                if (action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                    mViewPager.getAdapter().getTabSpace().requestLastFocus();
                    return true;
                }
            }
        }
        return false;
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
