package com.stv.launcher.fragment;

import android.app.Fragment;
import android.content.Context;

public class LivePagerController {

    private LiveFragment mLiveFragment;
    private static LivePagerController mPagerController;

    private LivePagerController(Context context) {
        mLiveFragment = (LiveFragment) Fragment.instantiate(context, LiveFragment.class.getName());
    }

    public static LivePagerController getInstance(Context context) {
        synchronized (LivePagerController.class) {
            if (mPagerController == null) {
                synchronized (LivePagerController.class) {
                    mPagerController = new LivePagerController(context);
                }
            }
        }
        return mPagerController;
    }

    public void onFragmentShowChanged(boolean gainShow) {
        mLiveFragment.onFragmentShowChanged(gainShow);
    }

    public Fragment getLiveFragment() {
        return mLiveFragment;
    }
}
