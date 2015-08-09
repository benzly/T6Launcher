package com.stv.launcher.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RelativeLayout;

import com.letv.launcher.R;

public class SearchPagerContainer extends RelativeLayout {

    private AppIconDrawer icon1;
    private AppIconDrawer icon2;
    private AppIconDrawer icon3;
    private AppIconDrawer icon4;

    public SearchPagerContainer(Context context) {
        this(context, null);
    }

    public SearchPagerContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchPagerContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        icon1 = (AppIconDrawer) findViewById(R.id.icon1);
        icon2 = (AppIconDrawer) findViewById(R.id.icon2);
        icon3 = (AppIconDrawer) findViewById(R.id.icon3);
        icon4 = (AppIconDrawer) findViewById(R.id.icon4);
    }

    public void requestLeft() {
        icon1.requestFocus();
    }

    public void requestRight() {
        icon3.requestFocus();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            boolean handle = false;
            int keyCode = event.getKeyCode();
            if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                if (icon3.hasFocus()) {
                    icon2.requestFocus();
                    handle = true;
                } else if (icon2.hasFocus()) {
                    icon1.requestFocus();
                    handle = true;
                }
            } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                if (icon1.hasFocus()) {
                    icon2.requestFocus();
                    handle = true;
                } else if (icon2.hasFocus()) {
                    icon3.requestFocus();
                    handle = true;
                }
            } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                if (icon1.hasFocus() || icon2.hasFocus() || icon3.hasFocus()) {
                    icon4.requestFocus();
                    handle = true;
                }
            }
            Log.d("xubin", "pager " + handle);
            return handle;
        }
        return super.dispatchKeyEvent(event);
    }
}
