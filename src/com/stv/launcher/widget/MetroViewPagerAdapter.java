package com.stv.launcher.widget;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MetroViewPagerAdapter extends PagerAdapter {

    private ArrayList<View> mPagers;

    public MetroViewPagerAdapter(ArrayList<View> pagers) {
        if (pagers == null) {
            this.mPagers = new ArrayList<View>(0);
        } else {
            this.mPagers = pagers;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mPagers.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mPagers.get(position));
        return mPagers.get(position);
    }

    @Override
    public int getCount() {
        return mPagers.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void add(ArrayList<View> pages) {
        mPagers.clear();
        mPagers.addAll(pages);
    }
}
