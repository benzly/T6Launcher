package com.stv.launcher.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class PagerContainer extends RelativeLayout {

    private static final String TAG = PagerContainer.class.getSimpleName();

    public PagerContainer(Context context) {
        super(context);
    }

    public PagerContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PagerContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PagerContainer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // Log.d(TAG, "dispatchKeyEvent " + event);
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
