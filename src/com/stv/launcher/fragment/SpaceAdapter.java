package com.stv.launcher.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewGroup;

import com.stv.launcher.app.ItemInfo;
import com.stv.launcher.v4fragment.FragmentStatePagerAdapter;
import com.stv.launcher.widget.MetroSpace;
import com.stv.launcher.widget.MetroViewPager;
import com.stv.launcher.widget.TabSpace;

public class SpaceAdapter extends FragmentStatePagerAdapter implements TabSpace.OnTabChangedListener, ViewPager.OnPageChangeListener {

    private static final String TAG = SpaceAdapter.class.getSimpleName();
    Context context;
    TabSpace tabSpace;
    MetroSpace metroSpace;
    int mCurrentTab = -1;
    ArrayList<TabInfo> tabs = new ArrayList<SpaceAdapter.TabInfo>();
    HashMap<String, BaseFragment> mFragmentCaches = new HashMap<String, BaseFragment>(5);

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
        }
    }

    public SpaceAdapter(FragmentActivity activity, TabSpace tabSpace, MetroSpace metroSpace) {
        super(activity.getFragmentManager());
        this.context = activity;
        this.tabSpace = tabSpace;
        this.metroSpace = metroSpace;

        FragmentManager.enableDebugLogging(true);
        
        MetroViewPager viewPager = metroSpace.getViewPager();
        viewPager.setAdapter(this);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setOnPageChangeListener(this);
        tabSpace.setOnTabChangedListener(this);
    }

    @Override
    public void onTabChanged(String tabId) {
        Log.d(TAG, "onTabChanged " + tabId);
        mCurrentTab = tabSpace.getCurrentTab();
        metroSpace.getViewPager().setCurrentItem(mCurrentTab);
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
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public void onPageSelected(int position) {
        final boolean pagerActive = metroSpace.getViewPager().hasFocus();
        final boolean switchLeft = mCurrentTab > position;
        Log.d(TAG, "onPageSelected=" + position + "  pagerActive=" + pagerActive + "  switchLeft=" + switchLeft);
        getCurrentFragment().onFragmentShowChanged(false);
        mCurrentTab = position;
        final BaseFragment fragment = getCurrentFragment();
        fragment.onFragmentShowChanged(true);
        if (pagerActive) {
            fragment.onFocusRequested(switchLeft ? BaseFragment.FOCUS_RIGHT_IN : BaseFragment.FOCUS_LEFT_IN);
            tabSpace.setSelection(mCurrentTab);
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {}

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {}

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
        if (tabInfo.fragment instanceof PagerFragment) {
            ((PagerFragment) tabInfo.fragment).bindData(items);
        }
    }

    public void setCurrentTab(int tab) {
        tabSpace.setCurrentTab(tab);
    }

    public BaseFragment getCurrentFragment() {
        if (mCurrentTab >= 0 && mCurrentTab < tabs.size()) {
            return tabs.get(mCurrentTab).fragment;
        }
        return null;
    }

    public TabSpace getTabSpace() {
        return tabSpace;
    }
}
