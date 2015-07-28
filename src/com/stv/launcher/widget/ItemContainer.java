package com.stv.launcher.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class ItemContainer extends RelativeLayout {

    public int index;

    public ItemContainer(Context context) {
        super(context);
    }

    public ItemContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ItemContainer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
