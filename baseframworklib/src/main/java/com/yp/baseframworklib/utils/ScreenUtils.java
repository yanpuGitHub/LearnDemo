package com.yp.baseframworklib.utils;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * @author : yanpu
 * @date : 2020-06-23
 * @description:
 */
public class ScreenUtils {

    public static int getDisplayWidth(Context ctx) {
        return getDisplayPoint(ctx).x;
    }

    public static int getDisplayHeight(Context ctx) {
        return getDisplayPoint(ctx).y;
    }

    public static Point getDisplayPoint(Context ctx) {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point;
    }

    public static DisplayMetrics getDisplayMetrics(Context ctx) {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

}
