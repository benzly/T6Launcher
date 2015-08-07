package com.stv.launcher.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.letv.launcher.ItemInfo;
import com.letv.launcher.R;
import com.stv.launcher.widget.AppIconDrawer;

import java.util.ArrayList;

public class SearchFragment extends PagerFragment {

    private static final String TAG = SearchFragment.class.getSimpleName();
    private AppIconDrawer icon1;
    private AppIconDrawer icon2;
    private AppIconDrawer icon3;

    @Override
    protected View onInflaterContent(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fragment_search, null);
        icon1 = (AppIconDrawer) content.findViewById(R.id.icon1);
        icon2 = (AppIconDrawer) content.findViewById(R.id.icon2);
        icon3 = (AppIconDrawer) content.findViewById(R.id.icon3);
        return content;
    }

    @Override
    protected void onFragmentShowChanged(boolean gainShow) {

    }

    @Override
    protected void bindData(ArrayList<ItemInfo> items) {

    }

    @Override
    protected void release() {

    }

    @Override
    public boolean priorityAccessKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        if (action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            Log.d(TAG, "handleKeyEvent right");
        }
        return super.priorityAccessKeyEvent(event);
    }

    @Override
    public boolean onFocusRequested(int requestDirection) {
        if (requestDirection == FOCUS_LEFT_IN) {
            icon1.requestFocus();
        } else if (requestDirection == FOCUS_RIGHT_IN) {
            icon3.requestFocus();
        }
        return false;
    }
}
