package com.stv.launcher.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.letv.launcher.Launcher;
import com.letv.launcher.R;
import com.letv.launcher.ScreenManagerActivity;

import java.util.ArrayList;
import java.util.List;

public class TabSpace extends FrameLayout implements View.OnFocusChangeListener, View.OnClickListener {

    public interface OnTabChangedListener {
        void onTabChanged(String tabId);
    }

    public static final int EDIT_TAB_RECODE = 2012;

    private boolean mHasOverlappingRendering = false;
    private TabContent mTabContent;
    private HorizontalScrollView mContentScrollView;
    private Button mManagerBt;
    private Launcher mLauncher;
    private List<TabSpec> mTabSpecs = new ArrayList<TabSpec>(2);
    private int mCurrentTab = -1;

    private OnTabChangedListener mOnTabChangeListener;

    public TabSpace(Context context) {
        this(context, null);
    }

    public TabSpace(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabSpace(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // tab scrollView
        mContentScrollView = new HorizontalScrollView(context);
        addView(mContentScrollView, new LayoutParams(800, LayoutParams.MATCH_PARENT));
        mContentScrollView.setBackgroundColor(Color.WHITE);

        // tab content
        mTabContent = new TabContent(context);
        mTabContent.setOrientation(LinearLayout.HORIZONTAL);
        mContentScrollView.addView(mTabContent, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        mTabContent.setBackgroundColor(Color.YELLOW);

        // manager button
        mManagerBt = new Button(context);
        mManagerBt.setText("管理");
        mManagerBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenManagerActivity.sScrennDatas.clear();
                ScreenManagerActivity.sScrennDatas.addAll(mLauncher.mScreens);
                Intent i = new Intent(mLauncher, ScreenManagerActivity.class);
                mLauncher.startActivityForResult(i, EDIT_TAB_RECODE);
            }
        });

        LayoutParams btParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        btParams.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
        addView(mManagerBt, btParams);
    }

    @SuppressLint("NewApi")
    @Override
    public boolean hasOverlappingRendering() {
        return mHasOverlappingRendering ? super.hasOverlappingRendering() : false;
    }

    /**
     * @param has 子view之间是否有重叠
     */
    public void setHasOverlappingRendering(boolean has) {
        mHasOverlappingRendering = has;
        mTabContent.setChildrenDrawingOrderEnabled(mHasOverlappingRendering);
    }

    public void setLauncher(Launcher launcher) {
        mLauncher = launcher;
    }

    public void setOnTabChangedListener(OnTabChangedListener l) {
        mOnTabChangeListener = l;
    }

    public void addTab(TabSpec tabSpec) {
        if (tabSpec.tag == null || tabSpec.tag.isEmpty()) {
            throw new IllegalArgumentException("you must specify a way to create the tab tag.");
        }
        mTabSpecs.add(tabSpec);
        mTabContent.addView(createTabView(tabSpec));
    }

    public void removeTab(String tag) {
        int beRmIndex = -1;
        for (int i = 0; i < mTabSpecs.size(); i++) {
            if (tag.equals(mTabSpecs.get(i).tag)) {
                beRmIndex = i;
                break;
            }
        }
        if (beRmIndex != -1) {
            mTabSpecs.remove(beRmIndex);
            mTabContent.removeViewAt(beRmIndex);
        }
    }

    public String getTagByTab(int tab) {
        if (tab >= 0 && tab < mTabSpecs.size()) {
            return mTabSpecs.get(tab).tag;
        }
        return null;
    }

    public int getCurrentTab() {
        return mCurrentTab;
    }

    public String getCurrentTag() {
        return mTabSpecs.get(mCurrentTab).tag;
    }

    private View createTabView(TabSpec tabSpec) {
        TabItem textView = new TabItem(getContext());
        textView.setOnFocusChangeListener(this);
        textView.setOnClickListener(this);
        textView.setSelectAllOnFocus(true);
        textView.setFocusable(true);
        textView.setTextSize(30);
        textView.setText(tabSpec.tag);
        textView.setTag(tabSpec);
        textView.setTextColor(R.drawable.temp_text_selector);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
        params.setMargins(20, 0, 20, 0);
        params.gravity = Gravity.CENTER;
        textView.setLayoutParams(params);
        return textView;
    }

    private class TabItem extends FocusProcessButton {

        public TabItem(Context context) {
            this(context, null);
        }

        public TabItem(Context context, AttributeSet attrs) {
            this(context, attrs, android.R.attr.buttonStyle);
        }

        public TabItem(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus && (v == this || v == mTabContent) && getTabCount() > 0) {
            mTabContent.getChildAt(mTabContent.getCurrentSelectTab()).requestFocus();
            return;
        }
        if (hasFocus && mOnTabChangeListener != null) {
            int i = 0;
            int numTabs = getTabCount();
            while (i < numTabs) {
                if (mTabContent.getChildAt(i) == v) {
                    // setCurrentTab(i);
                    mCurrentTab = i;
                    mOnTabChangeListener.onTabChanged(mTabSpecs.get(i).tag);
                    break;
                }
                i++;
            }
        }
    }

    @Override
    public void onClick(View v) {
        int numTabs = getTabCount();
        int i = 0;
        while (i < numTabs) {
            if (mTabContent.getChildAt(i) == v) {
                v.requestFocus();
                setCurrentTab(i);
                mOnTabChangeListener.onTabChanged(mTabSpecs.get(i).tag);
                break;
            }
            i++;
        }
    }

    public int getTabCount() {
        return mTabContent.getChildCount();
    }

    public void setCurrentTabByTag(String tag) {
        for (int i = 0; i < mTabSpecs.size(); i++) {
            if (mTabSpecs.get(i).getTag().equals(tag)) {
                setCurrentTab(i);
                break;
            }
        }
    }

    public void setCurrentTab(int index) {
        if (index < 0 || index >= mTabSpecs.size()) {
            return;
        }
        if (index == mCurrentTab) {
            return;
        }
        mCurrentTab = index;
        mTabContent.setCurrentTab(index);
    }

    public void setCurrentTab(String tag) {

    }

    public TabSpec newTabSpec(String tag) {
        return new TabSpec(tag);
    }

    public class TabSpec {
        private String tag;

        public TabSpec(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return tag;
        }
    }

    class TabContent extends LinearLayout {

        private int selectedTab = 0;

        public TabContent(Context context) {
            this(context, null);
        }

        public TabContent(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public TabContent(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @SuppressLint("NewApi")
        @Override
        public boolean hasOverlappingRendering() {
            return mHasOverlappingRendering ? super.hasOverlappingRendering() : false;
        }

        @Override
        protected void setChildrenDrawingOrderEnabled(boolean enabled) {
            super.setChildrenDrawingOrderEnabled(enabled);
        }

        @Override
        protected int getChildDrawingOrder(int childCount, int i) {
            if (mHasOverlappingRendering) {
                // draw the selected tab last
                if (selectedTab == -1) {
                    return i;
                } else {
                    if (i == childCount - 1) {
                        return selectedTab;
                    } else if (i >= selectedTab) {
                        return i + 1;
                    } else {
                        return i;
                    }
                }
            } else {
                return super.getChildDrawingOrder(childCount, i);
            }
        }

        public int getCurrentSelectTab() {
            return selectedTab;
        }

        public void setCurrentTab(int i) {
            if (getChildCount() > i) {
                getChildAt(i).requestFocus();
            }
        }
    }
}
