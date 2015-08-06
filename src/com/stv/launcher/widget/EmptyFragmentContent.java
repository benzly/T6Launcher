package com.stv.launcher.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

public class EmptyFragmentContent extends View {

    public EmptyFragmentContent(Context context) {
        this(context, null);
    }

    public EmptyFragmentContent(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyFragmentContent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAlpha(0.5f);
        setBackgroundColor(Color.GRAY);
    }
}
