package com.letv.launcher.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewGroup;

import com.letv.launcher.ItemInfo;
import com.stv.launcher.widget.MetroSpace;
import com.stv.launcher.widget.MetroViewPager;
import com.stv.launcher.widget.TabSpace;

import java.util.ArrayList;

public class SpaceAdapter extends FragmentStatePagerAdapter implements TabSpace.OnTabChangedListener, ViewPager.OnPageChangeListener {

    private static final String TAG = SpaceAdapter.class.getSimpleName();
    Context context;
    TabSpace tabSpace;
    MetroSpace metroSpace;
    ArrayList<TabInfo> tabs = new ArrayList<SpaceAdapter.TabInfo>();

    private class TabInfo {
        String tag;
        Class<?> clss;
        Bundle args;
        BaseFragment fragment;

        TabInfo(String tag, Class<?> _class, Bundle args) {
            this.tag = tag;
            this.clss = _class;
            this.args = args;
            this.fragment = (BaseFragment) Fragment.instantiate(context, clss.getName());
            this.fragment.tag = tag;
        }
    }

    public SpaceAdapter(FragmentActivity activity, TabSpace tabSpace, MetroSpace metroSpace) {
        super(activity.getSupportFragmentManager());
        this.context = activity;
        this.tabSpace = tabSpace;
        this.metroSpace = metroSpace;

        MetroViewPager viewPager = metroSpace.getViewPager();
        viewPager.setAdapter(this);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setOnPageChangeListener(this);
        tabSpace.setOnTabChangedListener(this);
    }

    public void addTab(String tabName, Class<?> clss, Bundle args) {
        Log.d(TAG, "addTab " + tabName);
        TabSpace.TabSpec tabSpec = tabSpace.newTabSpec(tabName);
        String tag = tabSpec.getTag();
        TabInfo info = new TabInfo(tag, clss, args);
        tabs.add(info);
        tabSpace.addTab(tabSpec);
        notifyDataSetChanged();
    }

    public void removeTab(String tabName) {
        Log.d(TAG, "removeTab " + tabName);
        TabInfo beRmInfo = null;
        for (TabInfo info : tabs) {
            if (info.tag.equals(tabName)) {
                beRmInfo = info;
                break;
            }
        }
        if (beRmInfo != null) {
            tabSpace.removeTab(beRmInfo.tag);
            tabs.remove(beRmInfo);
            beRmInfo = null;
            notifyDataSetChanged();
        }
    }

    public void bindTabItems(int tab, ArrayList<ItemInfo> items) {
        TabInfo tabInfo = tabs.get(tab);
        tabInfo.fragment.bindData(items);
    }

    @Override
    public void onTabChanged(String tabId) {
        int position = tabSpace.getCurrentTab();
        Log.d(TAG, "onTabChanged " + tabId + " " + position);
        metroSpace.getViewPager().setCurrentItem(position);
        for (int i = 0; i < tabs.size(); i++) {
            tabs.get(position).fragment.onFragmentShowChanged(position == i);
        }
    }

    @Override
    public Fragment getItem(int position) {
        TabInfo info = tabs.get(position);
        return info.fragment;
    }

    @Override
    public int getItemPosition(Object object) {
        // In order to dynamically delete
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public void onPageSelected(int position) {
        ViewGroup root = (ViewGroup) tabSpace.getParent();

        int oldFocusability = root.getDescendantFocusability();
        root.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        tabSpace.setCurrentTab(position);
        root.setDescendantFocusability(oldFocusability);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {}

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {}

}
