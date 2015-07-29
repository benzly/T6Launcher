package com.stv.launcher.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.letv.launcher.R;

import java.util.ArrayList;
import java.util.List;

public class TabSpace extends FrameLayout implements View.OnFocusChangeListener {

    public interface OnTabChangeListener {
        void onTabChanged(int tabId);
    }

    private boolean mHasOverlappingRendering = false;
    private TabContent mTabContent;
    private List<TabSpec> mTabSpecs = new ArrayList<TabSpec>(2);

    private OnTabChangeListener mOnTabChangeListener;

    public TabSpace(Context context) {
        this(context, null);
    }

    public TabSpace(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabSpace(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTabContent = new TabContent(context);
        mTabContent.setOrientation(LinearLayout.HORIZONTAL);
        FrameLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(mTabContent, params);
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

    public void setOnTabChangeListener(OnTabChangeListener l) {
        mOnTabChangeListener = l;
    }

    public void addTab(TabSpec tabSpec) {
        if (tabSpec.tag == null || tabSpec.tag.isEmpty()) {
            throw new IllegalArgumentException("you must specify a way to create the tab tag.");
        }
        mTabSpecs.add(tabSpec);
        mTabContent.addView(createTabView(tabSpec));
    }

    private View createTabView(TabSpec tabSpec) {
        Button textView = new Button(getContext());
        textView.setOnFocusChangeListener(this);
        textView.setSelectAllOnFocus(true);
        textView.setFocusable(true);
        textView.setTextSize(30);
        textView.setText(tabSpec.tag);
        textView.setTextColor(R.drawable.temp_text_selector);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
        params.setMargins(20, 0, 20, 0);
        params.gravity = Gravity.CENTER;
        textView.setLayoutParams(params);
        return textView;
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
                    setCurrentTab(i);
                    mOnTabChangeListener.onTabChanged(i);
                    break;
                }
                i++;
            }
        }
    }

    public int getTabCount() {
        return mTabContent.getChildCount();
    }

    public void setCurrentTab(int index) {
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
