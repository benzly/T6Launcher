package com.stv.launcher.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.HorizontalScrollView;
import android.widget.Scroller;

import com.stv.launcher.fragment.SpaceAdapter;

import java.lang.reflect.Field;

public class MetroViewPager extends ViewPager {

    public MetroViewPager(Context context) {
        this(context, null);
    }

    public MetroViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            Scroller scroller = new MetroPagerScroller(context, new Interpolator() {
                @Override
                public float getInterpolation(float t) {
                    return (float) Math.sqrt(2 * t - t * t);
                }
            });
            mField.set(this, scroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean canScroll(View v, boolean arg1, int arg2, int arg3, int arg4) {
        if (v instanceof HorizontalScrollView) {
            return false;
        }
        return super.canScroll(v, arg1, arg2, arg3, arg4);
    }

    @Override
    public SpaceAdapter getAdapter() {
        return (SpaceAdapter) super.getAdapter();
    }
}
