package com.stv.launcher.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class SearchDesktop extends FrameLayout {

    public SearchDesktop(Context context) {
        this(context, null);
    }

    public SearchDesktop(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchDesktop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TextView tv = new TextView(context);
        tv.setText("自定义搜索页");
        tv.setTextSize(200);
        tv.setGravity(Gravity.CENTER);
        addView(tv);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
