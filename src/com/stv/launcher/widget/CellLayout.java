package com.stv.launcher.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class CellLayout extends ViewGroup {

    private ItemContainer mItemContainer;

    public CellLayout(Context context) {
        this(context, null);
    }

    public CellLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CellLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mItemContainer = new ItemContainer(context);
        addView(mItemContainer);
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

    public void setIndex(int i) {
        mItemContainer.index = i;
    }

    public void addChild(View child) {
        mItemContainer.addView(child);
    }

}
