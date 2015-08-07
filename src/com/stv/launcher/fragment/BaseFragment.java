package com.stv.launcher.fragment;

import android.app.Fragment;
import android.view.KeyEvent;

public abstract class BaseFragment extends Fragment {

    protected static final int FOCUS_LEFT_IN = 0x00000011;
    protected static final int FOCUS_TOP_IN = 0x00000012;
    protected static final int FOCUS_RIGHT_IN = 0x00000013;
    protected static final int FOCUS_BOTTOM_IN = 0x00000014;

    protected int mId;

    protected abstract boolean onFocusRequested(int requestDirection);

    protected abstract void onFragmentShowChanged(boolean gainShow);

    public boolean priorityAccessKeyEvent(KeyEvent event) {
        return false;
    }

}
