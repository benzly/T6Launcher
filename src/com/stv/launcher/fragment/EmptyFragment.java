package com.stv.launcher.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.letv.launcher.ItemInfo;
import com.letv.launcher.R;

import java.util.ArrayList;

public class EmptyFragment extends PagerFragment {

    private static final String TAG = EmptyFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate " + tag);
    }

    @Override
    protected View onInflaterContent(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_empty, null);
    }

    @Override
    protected void bindData(ArrayList<ItemInfo> items) {

    }

    @Override
    protected void release() {

    }

    @Override
    public void onFragmentShowChanged(boolean gainShow) {
        if (gainShow) {
            Log.d(TAG, tag + " is show ------");
        }
    }

    @Override
    protected boolean onFocusRequested(int requestDirection) {
        return false;
    }

}
