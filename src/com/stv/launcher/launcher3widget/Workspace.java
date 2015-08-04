package com.stv.launcher.launcher3widget;

import android.content.Context;
import android.util.AttributeSet;

import com.stv.launcher.widget.TabSpace;
import com.stv.launcher.widget.TabSpace.OnTabChangedListener;

public class Workspace extends SmoothPagedView implements OnTabChangedListener {

    public static final int EXTRA_EMPTY_SCREEN_ID = -1;

    public Workspace(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Workspace(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContentIsRefreshable = false;
        setDataIsReady();
    }

    @Override
    public void syncPages() {

    }

    @Override
    public void syncPageItems(int page, boolean immediate) {

    }

    @Override
    public void onTabChanged(String tabId) {
        snapToPage(0);
    }

}
