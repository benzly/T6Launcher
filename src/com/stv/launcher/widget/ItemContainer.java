package com.stv.launcher.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class ItemContainer extends RelativeLayout {

    public int index;

    public ItemContainer(Context context) {
        this(context, null);
    }

    public ItemContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int childcount = getChildCount();
        for (int i = 0; i < childcount; i++) {
            View child = getChildAt(i);
            if (index % 2 == 0) {
                child.layout(0, 200, 200, 400);
            } else {
                child.layout(0, 0, 200, 200);
            }
        }
    }

}
