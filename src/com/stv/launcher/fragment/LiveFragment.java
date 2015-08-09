package com.stv.launcher.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.letv.launcher.R;
import com.stv.launcher.app.ItemInfo;

public class LiveFragment extends PagerFragment {

    TextView tv;

    @Override
    protected View onInflaterContent(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fragment_live, null);
        tv = (TextView) content.findViewById(R.id.tv);
        return content;
    }

    @Override
    protected void bindData(ArrayList<ItemInfo> items) {

    }

    @Override
    protected void release() {

    }

    @Override
    protected boolean onFocusRequested(int requestDirection) {
        return false;
    }

    @Override
    protected void onFragmentShowChanged(boolean gainShow) {
        if (tv != null) {
            if (gainShow) {
                tv.setText("正在播放");
            }
        }
    }

}
