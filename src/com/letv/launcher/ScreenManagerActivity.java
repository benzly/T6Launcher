package com.letv.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stv.launcher.widget.TabSpace;

import java.util.ArrayList;

public class ScreenManagerActivity extends Activity {

    public static final ArrayList<ScreenInfo> sScrennDatas = new ArrayList<ScreenInfo>();
    public static final ArrayList<ScreenInfo> sBeRemoved = new ArrayList<ScreenInfo>();
    public static final ArrayList<ScreenInfo> sBeAdded = new ArrayList<ScreenInfo>();

    String[] array = {"视频", "搜索", "应用", "发现", "亲子", "汽车", "游戏", "乐见"};
    LinearLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_manager);
        content = (LinearLayout) findViewById(R.id.content);
        for (int i = 0; i < array.length; i++) {
            LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.temp_item, null);
            ((TextView) layout.findViewById(R.id.tv)).setText(array[i]);
            content.addView(layout);
            for (int j = 0; j < sScrennDatas.size(); j++) {
                if (sScrennDatas.get(j).name.equals(array[i])) {
                    ((CheckBox) layout.findViewById(R.id.box)).setChecked(true);
                    break;
                }
            }
        }
    }

    public ScreenInfo getScreenInfo(String tag) {
        for (int j = 0; j < sScrennDatas.size(); j++) {
            if (tag.equals(sScrennDatas.get(j).name)) {
                return sScrennDatas.get(j);
            }
        }
        return null;
    }

    public void onBackPressed() {
        sBeRemoved.clear();
        sBeAdded.clear();
        log.clear();

        for (int i = 0; i < content.getChildCount(); i++) {
            LinearLayout layout = (LinearLayout) content.getChildAt(i);
            CheckBox cb = (CheckBox) layout.findViewById(R.id.box);
            String tag = ((TextView) layout.findViewById(R.id.tv)).getText().toString();
            if (cb.isChecked()) {
                ScreenInfo info = getScreenInfo(tag);
                if (info == null) {
                    String name = array[i];
                    log.add(name);
                    LayoutType type = LayoutType.EMPTY;
                    if (name.equals("视频") || name.equals("应用")) {
                        type = LayoutType.MANUAL;
                    } else if (name.equals("搜索")) {
                        type = LayoutType.EMPTY;
                    } else {
                        type = LayoutType.AUTOMATIC;
                    }
                    info = new ScreenInfo(i, name, type);
                    info.type = LayoutType.AUTOMATIC;
                    sScrennDatas.add(info);
                    sBeAdded.add(info);
                }
            } else {
                ScreenInfo info = getScreenInfo(tag);
                if (info != null) {
                    sScrennDatas.remove(info);
                    sBeRemoved.add(info);
                }
            }
        }
        Toast.makeText(getApplicationContext(), "" + log, 1).show();
        setResult(TabSpace.EDIT_TAB_RECODE);
        finish();
    };

    ArrayList<String> log = new ArrayList<String>();

}
