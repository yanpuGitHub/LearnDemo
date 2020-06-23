package com.yp.baseframworklib.utils;

import android.os.Build;
import android.view.View;

/**
 * @author : yanpu
 * @date : 2020-06-23
 * @description:
 */
public class ImmersiveUtils {

    public static void fullScreen(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            view.setSystemUiVisibility(uiOptions);
        }
    }


    public static void hiddenNav(View v) {
        setSystemUiVisibility(v, getOptionHiddenNav());
    }

    public static int getOptionHiddenNav() {
        int uiOptions = View.GONE;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        return uiOptions;
    }

    public static void setSystemUiVisibility(View v, int uiOptions) {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) { // lower api
            //View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(uiOptions);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //for new api versions.
            v.setSystemUiVisibility(uiOptions);
        }
    }
}
