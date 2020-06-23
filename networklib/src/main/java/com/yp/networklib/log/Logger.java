package com.yp.networklib.log;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

/**
 * @author : yanpu
 * @date : 2020-06-23
 * @description:
 */
public class Logger extends LoggerSaveManager {
    private static final String TAG = "TEST";
    private static boolean isLog = true;
    public static final String DONT_SHOW = "DONT_SHOW";
    private static Logger.Callback cb;

    public Logger() {
    }

    public static void w(String str) {
        w((Context)null, (String)str);
    }

    public static void w(Context ctx, String str) {
        w(ctx, (String)null, str);
    }

    public static void w(Context ctx, String str, long millis) {
        w(ctx, (String)null, str, millis);
    }

    public static void w(Context ctx, String logName, String str) {
        w(ctx, logName, str, 0L);
    }

    public static void w(Context ctx, String logName, String str, long millis) {
        if (null == str || "".equals(str)) {
            str = "EPT";
        }

        boolean isShow = null != ctx && !str.startsWith("DONT_SHOW");
        if (isShow) {
            final Toast toast = Toast.makeText(ctx, str, 1);
            toast.show();
            if (millis > 0L) {
                (new Handler()).postDelayed(new Runnable() {
                    public void run() {
                        toast.cancel();
                    }
                }, millis);
            }
        }

        if (isLog) {
            Log.w("TEST", str);
        }

        if (isSave) {
            save(logName, str);
        }

        if (null != cb) {
            cb.onLogger(logName, str, isLog, isSave, isShow, millis);
        }

    }

    public static void w(Object... obj) {
        w((Context)null, (Object[])obj);
    }

    public static void w(Context ctx, Object... obj) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < obj.length; ++i) {
            sb.append("\n\t");
            sb.append(i);
            sb.append("：");
            sb.append(obj[i]);
        }

        w(ctx, sb.toString());
    }

    public static void setLog(boolean isEnable) {
        isLog = isEnable;
    }

    public static void setCallback(Logger.Callback callback) {
        cb = callback;
    }

    public interface Callback {
        void onLogger(String var1, String var2, boolean var3, boolean var4, boolean var5, long var6);
    }
}
