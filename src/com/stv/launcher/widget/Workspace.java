package com.stv.launcher.widget;

import android.content.Context;
import android.util.AttributeSet;

public class Workspace extends SmoothPagedView {

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

}
