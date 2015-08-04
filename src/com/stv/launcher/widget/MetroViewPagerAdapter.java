package com.stv.launcher.widget;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MetroViewPagerAdapter extends PagerAdapter {

    private static final String TAG = MetroViewPagerAdapter.class.getSimpleName();
    private ArrayList<View> mPagers;
    private int mRefreshPosition = -1;

    public MetroViewPagerAdapter(ArrayList<View> pagers) {
        if (pagers == null) {
            this.mPagers = new ArrayList<View>(0);
        } else {
            this.mPagers = pagers;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d(TAG, "destroyItem " + position);
        container.removeView(mPagers.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d(TAG, "instantiateItem " + position);
        View item = mPagers.get(position);
        item.setTag(position);
        container.addView(item);
        return item;
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
        View view = (View) object;
        if (mRefreshPosition == (Integer) view.getTag()) {
            return POSITION_NONE;
        } else {
            return POSITION_UNCHANGED;
        }
    }

    public void add(ArrayList<View> pages) {
        mPagers.clear();
        mPagers.addAll(pages);
    }

    public void notifyDataSetChanged(int position) {
        mRefreshPosition = position;
        notifyDataSetChanged();
    }

}
