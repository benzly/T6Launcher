package com.letv.launcher.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.letv.launcher.ItemInfo;
import com.letv.launcher.R;

import java.util.ArrayList;

public class EmptyFragment extends BaseFragment {

    private static final String TAG = EmptyFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate " + tag);
    }

    @Override
    protected View onInflaterView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_empty, null);
    }

    @Override
    protected void bindData(ArrayList<ItemInfo> items) {

    }

    @Override
    protected void asyncGetData() {

    }

    @Override
    public void onFragmentShowChange(boolean isShow) {
        if (isShow) {
            Log.d(TAG, tag + " is show ------\n");
            if (mRootView != null) {
                ((TextView) mRootView.findViewById(R.id.tv)).setText(tag + "");
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy " + tag);
    }

}
