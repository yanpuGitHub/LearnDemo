package com.yp.networklib.log;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : yanpu
 * @date : 2020-06-23
 * @description:
 */
public class LoggerSaveManager {

    private static Map<String, LoggerSave> map = new HashMap();
    protected static boolean isSave = false;
    private static LoggerSaveCat lsc;

    public LoggerSaveManager() {
    }

    public static LoggerSave get() {
        return get((String)null);
    }

    public static LoggerSave get(String name) {
        return null != name && !"".equals(name) ? (LoggerSave)map.get(name) : (LoggerSave)map.get(0);
    }

    public static int start(Context ctx) {
        isSave = true;
        return create(ctx);
    }

    public static int start(Context ctx, String logPath, String logName) {
        isSave = true;
        return create(ctx, logPath, logName);
    }

    public static int create(Context ctx) {
        return create(ctx, (String)null, (String)null);
    }

    public static int create(Context ctx, String logPath, String logName) {
        if (map.containsKey(logName)) {
            return map.size();
        } else {
            LoggerSave ls = new LoggerSave(ctx, logPath, logName);
            map.put(logName, ls);
            return map.size();
        }
    }

    public static void save(String name, String str) {
        try {
            ((LoggerSave)map.get(name)).save(str);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public static void startCat(Context ctx) {
        startCat(ctx, (String)null, (String)null);
    }

    public static void startCat(Context ctx, String logPath, String logName) {
        try {
            if (null == lsc) {
                lsc = new LoggerSaveCat(ctx, logPath, logName);
            }

            lsc.start();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public static void stopCat() {
        if (null != lsc) {
            lsc.stop();
        }

    }
}
