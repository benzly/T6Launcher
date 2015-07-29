package com.stv.launcher.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.letv.launcher.R;
import com.stv.launcher.widget.TabSpace.OnTabChangeListener;

public class MetroSpace extends FrameLayout implements OnTabChangeListener {

    private static final String TAG = MetroSpace.class.getSimpleName();

    private MetroViewPager mViewPager;
    private MetroViewPagerAdapter mAdapter;

    public MetroSpace(Context context) {
        this(context, null);
    }

    public MetroSpace(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MetroSpace(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initMetroSpace();
    }

    protected void initMetroSpace() {
        mViewPager = (MetroViewPager) findViewById(R.id.metro_viewpager);
        mAdapter = new MetroViewPagerAdapter(null);
        Log.d(TAG, "mViewPager=" + mViewPager + "  mAdapter=" + mAdapter);
        // mViewPager.setAdapter(mAdapter);
    }

    @Override
    public void onTabChanged(int tabId) {

    }

}
