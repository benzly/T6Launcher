package com.stv.launcher.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.letv.launcher.R;
import com.stv.launcher.app.ItemInfo;
import com.stv.launcher.widget.SearchPagerContainer;

public class SearchFragment extends PagerFragment {

    private static final String TAG = SearchFragment.class.getSimpleName();
    SearchPagerContainer s;
    

    @Override
    protected View onInflaterContent(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       s = (SearchPagerContainer) inflater.inflate(R.layout.fragment_search, null);
        return s;
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
    public boolean onFocusRequested(int requestDirection) {
        if (requestDirection == FOCUS_LEFT_IN) {
            s.requestLeft();
        } else if (requestDirection == FOCUS_RIGHT_IN) {
            s.requestRight();
        }
        return false;
    }
}
