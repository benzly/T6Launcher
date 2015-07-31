package com.stv.launcher.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.letv.launcher.model.ScreenInfo;
import com.stv.launcher.widget.TabSpace.OnTabChangeListener;

import java.util.ArrayList;

public class MetroSpace extends FrameLayout implements OnTabChangeListener {

    private static final String TAG = MetroSpace.class.getSimpleName();

    private MetroViewPager mViewPager;
    private ArrayList<View> mPages = new ArrayList<View>();

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
        // add viewPager
        mViewPager = new MetroViewPager(getContext());
        mViewPager.setAdapter(new MetroViewPagerAdapter(null));
        addView(mViewPager);

        //
    }

    public MetroViewPager getViewPager() {
        return mViewPager;
    }

    public void bindScreens(ArrayList<ScreenInfo> orderedScreens) {
        for (ScreenInfo screen : orderedScreens) {
            mPages.add(createPage(screen));
        }
        mViewPager.getAdapter().add(mPages);
        mViewPager.getAdapter().notifyDataSetChanged();
    }

    private View createPage(ScreenInfo screen) {
        switch (screen.type) {
            case EMPTY:
                TextView t = new TextView(getContext());
                t.setText("empty");
                t.setTextSize(100);
                return t;
            case AUTOMATIC:
                CellLayout cellLayout = new CellLayout(getContext());
                cellLayout.setBackgroundColor(Color.RED);
                return cellLayout;
            case MANUAL:
                if (screen.name.equals("搜索")) {
                    SearchDesktop s = new SearchDesktop(getContext());
                    return s;
                } else {
                    TextView tt = new TextView(getContext());
                    tt.setText("未定义layout");
                    tt.setTextSize(100);
                    return tt;
                }
            default:
                TextView ttt = new TextView(getContext());
                ttt.setText("not match screen type");
                ttt.setTextSize(100);
                return ttt;
        }

    }

    @Override
    public void onTabChanged(int tabId) {
        mViewPager.setCurrentItem(tabId);
    }

}
