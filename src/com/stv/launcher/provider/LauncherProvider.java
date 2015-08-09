package com.stv.launcher.provider;

import org.xmlpull.v1.XmlPullParser;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.letv.launcher.LauncherState;

public class LauncherProvider extends ContentProvider {
    private static final String TAG = LauncherProvider.class.getSimpleName();

    @Override
    public boolean onCreate() {
        LauncherState.setLauncherProvider(this);
        loadDefaultWorkspace();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    public void deleteDatabase() {

    }

    /**
     * Loads the default workspace based on the following priority scheme: 1) From a package
     * provided by play store 2) From a partner configuration APK, already in the system image 3)
     * The default configuration for the particular device
     */
    public synchronized void loadDefaultFavoritesIfNecessary() {}

    private void loadDefaultWorkspace() {
        XmlResourceParser parser = null;
        try {
            parser = getContext().getResources().getXml(com.letv.launcher.R.xml.default_screens);
            /** begin parser */
            int eventType = parser.getEventType();
            StringBuilder sb = new StringBuilder();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String tag = parser.getName();
                        if (tag.equals("screen")) {
                            sb.append("\n<screen>");
                        } else if (tag.equals("screenId")) {
                            int screenId = Integer.valueOf(parser.nextText());
                            sb.append("\n   id=" + screenId);
                        } else if (tag.equals("tabName")) {
                            String tab = parser.nextText();
                            sb.append("\n   tab=" + tab);
                        } else if (tag.equals("spanXSize")) {
                            int spanXSize = Integer.valueOf(parser.nextText());
                            sb.append("\n   spanXSize=" + spanXSize);
                        } else if (tag.equals("spanYSize")) {
                            int spanYSize = Integer.valueOf(parser.nextText());
                            sb.append("\n   spanYSize=" + spanYSize);
                        } else if (tag.equals("model")) {
                            sb.append("\n   <model>");
                        } else if (tag.equals("position")) {
                            int modelPosition = Integer.valueOf(parser.nextText());
                            sb.append("\n      position=" + modelPosition);
                        } else if (tag.equals("title")) {
                            String modelTitle = parser.nextText();
                            sb.append("\n      title=" + modelTitle);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        String endTag = parser.getName();
                        if (endTag.equals("model")) {
                            /** 解析完成一个model,存到DB中 */
                            sb.append("\n   </model>");
                        } else if (endTag.equals("screen")) {
                            sb.append("\n</screen>");
                            Log.d(TAG, sb.toString());
                            sb.delete(0, sb.length());
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            Log.d("", "" + e);
        }
    }
}
