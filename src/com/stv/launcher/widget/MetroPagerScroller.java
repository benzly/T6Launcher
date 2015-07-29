package com.stv.launcher.widget;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class MetroPagerScroller extends Scroller {
    private int mDuration = 300;

    public MetroPagerScroller(Context context) {
        super(context);
    }

    public MetroPagerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    public void setmDuration(int time) {
        mDuration = time;
    }

    public int getmDuration() {
        return mDuration;
    }

}
