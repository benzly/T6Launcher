package com.stv.launcher.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.letv.launcher.DeviceProfile;
import com.letv.launcher.LauncherState;
import com.letv.launcher.R;
import com.stv.launcher.activity.Launcher;
import com.stv.launcher.activity.ScreenManagerActivity;

import java.util.ArrayList;
import java.util.List;

public class TabSpace extends FrameLayout implements View.OnFocusChangeListener, View.OnClickListener {
    private static final String TAG = TabSpace.class.getSimpleName();

    public interface OnTabChangedListener {
        void onTabChanged(String tabId);
    }

    public static final int EDIT_TAB_RECODE = 2012;

    private int mCurrentTab = -1;
    private Launcher mLauncher;
    private TabContent mTabContent;
    private Button mManagerButton;
    private HorizontalScrollView mContentScrollView;
    private boolean mHasOverlappingRendering = false;
    private List<TabSpec> mTabSpecs = new ArrayList<TabSpec>(2);

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
        mContentScrollView.setHorizontalScrollBarEnabled(false);
        addView(mContentScrollView);

        // tab content
        mTabContent = new TabContent(context);
        mContentScrollView.addView(mTabContent);

        // tab manager
        mManagerButton = new Button(context);
        mManagerButton.setFocusable(true);
        mManagerButton.setText(R.string.tab_manager_text);
        mManagerButton.setTextSize(36);
        mManagerButton.setGravity(Gravity.CENTER);
        mManagerButton.setOnKeyListener(new ManagerButtonKeyListener());
        addView(mManagerButton);
    }

    public void layout(DeviceProfile profile) {
        LayoutParams scrollViewParams = (LayoutParams) mContentScrollView.getLayoutParams();
        scrollViewParams.width = LayoutParams.MATCH_PARENT;
        scrollViewParams.height = LayoutParams.MATCH_PARENT;
        mContentScrollView.setPadding(profile.tabspace_item_padding_horizontal_edge - profile.tabspace_item_margin, 0,
                profile.tabspace_item_padding_horizontal_edge, 0);
        mContentScrollView.setLayoutParams(scrollViewParams);

        LayoutParams contentParams = (LayoutParams) mTabContent.getLayoutParams();
        contentParams.width = LayoutParams.WRAP_CONTENT;
        contentParams.height = LayoutParams.MATCH_PARENT;
        mTabContent.setLayoutParams(contentParams);

        LayoutParams managerButtonParams = (LayoutParams) mManagerButton.getLayoutParams();
        managerButtonParams.width = LayoutParams.WRAP_CONTENT;
        managerButtonParams.height = LayoutParams.MATCH_PARENT;
        managerButtonParams.gravity = Gravity.RIGHT;
        managerButtonParams.rightMargin = profile.tabspace_item_padding_horizontal_edge;
        mManagerButton.setLayoutParams(managerButtonParams);
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

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
                int tabCount = mTabContent.getChildCount();
                if (tabCount > 0 && mTabContent.getChildAt(0).hasFocus()) {
                    return true;
                }
            } else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
                int tabCount = mTabContent.getChildCount();
                if (tabCount > 0 && mTabContent.getChildAt(tabCount - 1).hasFocus()) {
                    mManagerButton.requestFocus();
                    setSelection(mCurrentTab);
                    return true;
                }
            } else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
                setSelection(mCurrentTab);
            }
        }
        return super.dispatchKeyEvent(event);
    }

    class ManagerButtonKeyListener implements View.OnKeyListener {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                    return true;
                } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                    int tabCount = mTabContent.getChildCount();
                    if (tabCount > 0 && mTabContent.getChildAt(tabCount - 1).requestFocus()) {
                        return true;
                    }
                } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {

                }
            } else {
                if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                    ScreenManagerActivity.sScrennDatas.clear();
                    ScreenManagerActivity.sScrennDatas.addAll(mLauncher.mScreens);
                    Intent i = new Intent(mLauncher, ScreenManagerActivity.class);
                    mLauncher.startActivityForResult(i, EDIT_TAB_RECODE);
                    return true;
                }
            }
            return false;
        }

    }

    public void setLauncher(Launcher launcher) {
        mLauncher = launcher;
    }

    public void setOnTabChangedListener(OnTabChangedListener l) {
        mOnTabChangeListener = l;
    }

    public void requestLastFocus() {
        Log.d(TAG, "requestLastFocus " + mCurrentTab);
        if (mCurrentTab < 0) {
            mCurrentTab = 0;
        }
        mTabContent.getChildAt(mCurrentTab).requestFocus();
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
        DeviceProfile profile = LauncherState.getInstance().getDynamicGrid().getDeviceProfile();
        Button tabItem = (Button) LayoutInflater.from(getContext()).inflate(R.layout.tabspace_item, null);
        tabItem.setOnFocusChangeListener(this);
        tabItem.setOnClickListener(this);
        tabItem.setTextSize(profile.tabspace_item_normal_textsize);
        tabItem.setText(tabSpec.tag);
        tabItem.setTag(tabSpec);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(profile.tabspace_item_margin, 0, 0, 0);
        tabItem.setLayoutParams(params);
        return tabItem;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus && (v == this || v == mTabContent) && getTabCount() > 0) {
            mTabContent.getChildAt(mTabContent.getCurrentSelectTab()).requestFocus();
            return;
        }
        if (hasFocus) {
            ((TextView) v).getPaint().setFakeBoldText(true);
            int i = 0;
            int numTabs = getTabCount();
            while (i < numTabs) {
                if (mTabContent.getChildAt(i) == v) {
                    mTabContent.setSelection(mCurrentTab, false);
                    mCurrentTab = i;
                    if (mOnTabChangeListener != null) {
                        mOnTabChangeListener.onTabChanged(mTabSpecs.get(i).tag);
                    }
                    break;
                }
                i++;
            }
        } else {
            ((TextView) v).getPaint().setFakeBoldText(false);
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

    public void setSelection(int index) {
        if (index < 0 || index >= mTabSpecs.size()) {
            return;
        }
        mTabContent.setSelection(mCurrentTab, false);
        mCurrentTab = index;
        mTabContent.setSelection(index, true);
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
            setOrientation(LinearLayout.HORIZONTAL);
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

        public void setSelection(int i, boolean select) {
            if (getChildCount() > i) {
                getChildAt(i).setSelected(select);
            }
        }
    }
}
