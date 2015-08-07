package com.letv.launcher;

import static com.squareup.leakcanary.LeakCanary.leakInfo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.squareup.leakcanary.AbstractAnalysisResultService;
import com.squareup.leakcanary.AnalysisResult;
import com.squareup.leakcanary.HeapDump;

public class HeapAnalysisResultService extends AbstractAnalysisResultService {


    @Override
    protected void onHeapAnalyzed(HeapDump heapDump, AnalysisResult result) {
        String leakInfo = leakInfo(this, heapDump, result);
        Log.d("T2LeakCanary", "\n");
        Log.d("T2LeakCanary", "---------------------------result---------------------------");
        Log.d("T2LeakCanary", leakInfo);
        Log.d("T2LeakCanary", "------------------------------------------------------------");
    }

    public static String leakInfo(Context context, HeapDump heapDump, AnalysisResult result) {
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        PackageInfo packageInfo;
        try {
            packageInfo = packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        String versionName = packageInfo.versionName;
        int versionCode = packageInfo.versionCode;
        String info = "In " + packageName + ":" + versionName + ":" + versionCode + ".\n";
        if (result.leakFound) {
            if (result.excludedLeak) {
                info += "* LEAK CAN BE IGNORED.\n";
            }
            info += "* " + result.className;
            if (!heapDump.referenceName.equals("")) {
                info += " (" + heapDump.referenceName + ")";
            }
            info += " has leaked:\n" + result.leakTrace.toString() + "\n";
        } else if (result.failure != null) {
            info += "* FAILURE:\n" + Log.getStackTraceString(result.failure) + "\n";
        } else {
            info += "* NO LEAK FOUND.\n\n";
        }
        info +=
                "* Reference Key: " + heapDump.referenceKey + "\n" + "* Device: " + Build.MANUFACTURER + " " + Build.BRAND + " "
                        + Build.MODEL + " " + Build.PRODUCT + "\n" + "* Android Version: " + Build.VERSION.RELEASE + " API: "
                        + Build.VERSION.SDK_INT + "\n" + "* Durations: watch=" + heapDump.watchDurationMs + "ms, gc="
                        + heapDump.gcDurationMs + "ms, heap dump=" + heapDump.heapDumpDurationMs + "ms, analysis="
                        + result.analysisDurationMs + "ms" + "\n";

        return info;
    }

}
