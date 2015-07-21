package com.letv.launcher.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class CellLayout extends ViewGroup {

    private ItemContainer mItemContainer;

    public CellLayout(Context context) {
        super(context);
    }

    public CellLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CellLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CellLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mItemContainer = new ItemContainer(context);
        addView(mItemContainer);
        setBackgroundColor(Color.YELLOW);
        setAlpha(0.5f);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int offset = 0;
        int left = getPaddingLeft() + (int) Math.ceil(offset / 2f);
        int top = getPaddingTop();
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            child.layout(left, top, left + r - l, top + b - t);
        }
    }

}
