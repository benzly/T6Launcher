package com.letv.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ScreenManagerActivity extends Activity {

    String[] array = {"视频", "搜索", "应用", "发现", "亲子", "汽车", "游戏"};
    LinearLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_manager);
        content = (LinearLayout) findViewById(R.id.content);
        for (int i = 0; i < array.length; i++) {
            LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.temp_item, content);
            ((TextView) layout.findViewById(R.id.tv)).setText(array[i]);
        }
    }

    ArrayList<String> page = new ArrayList<String>();

    @Override
    protected void onStop() {
        super.onStop();
        page.clear();
        for (int i = 0; i < content.getChildCount(); i++) {
            LinearLayout layout = (LinearLayout) content.getChildAt(i);
            CheckBox cb = (CheckBox) layout.findViewById(R.id.box);
            if (cb.isChecked()) {
                page.add(array[i]);
            }
        }
        Toast.makeText(getApplicationContext(), "" + page, 0).show();
    }
}
