package com.stv.launcher.fragment;

import android.app.Fragment;
import android.view.KeyEvent;

public abstract class BaseFragment extends Fragment {

    public int mId;

    public boolean handleKeyEvent(KeyEvent event) {
        return false;
    }

    public abstract boolean focusIn(int direction);
}
