package com.letv.launcher.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.letv.launcher.ItemInfo;
import com.letv.launcher.R;
import com.stv.launcher.widget.AppIconDrawer;
import com.stv.launcher.widget.MetroSpace;

import java.util.ArrayList;

public class EmptyFragment extends BaseFragment {

    private static final String TAG = EmptyFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate " + tag);
    }

    @Override
    protected View onInflaterContent(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fragment_empty, null);

        AppIconDrawer icon1 = (AppIconDrawer) content.findViewById(R.id.icon1);
        AppIconDrawer icon2 = (AppIconDrawer) content.findViewById(R.id.icon2);
        AppIconDrawer icon3 = (AppIconDrawer) content.findViewById(R.id.icon3);


        return content;
    }

    @Override
    protected void bindData(ArrayList<ItemInfo> items) {

    }

    @Override
    protected void asyncGetData() {

    }

    @Override
    protected void release() {

    }

    @Override
    public void onFragmentShowChanged(boolean gainShow) {
        if (gainShow) {
            MetroSpace sp = (MetroSpace) ((ViewGroup) mContentView.getRootView()).findViewById(R.id.metro_space);
            Log.d(TAG, tag + " is show ------\n");
            if (mContentView != null) {
                ((TextView) mContentView.findViewById(R.id.tv)).setText(tag + "");
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
