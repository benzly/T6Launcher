package com.stv.launcher.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.letv.launcher.ItemInfo;
import com.letv.launcher.R;

import java.util.ArrayList;

public class LiveFragment extends PagerFragment {

    TextView tv;
    boolean isBackgroundPlay = false;

    @Override
    protected View onInflaterContent(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fragment_live, null);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        content.setLayoutParams(lp);
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
            } else {
                tv.setText(isBackgroundPlay ? "背景播放" : "播放暂停");
            }
        }
    }

}
