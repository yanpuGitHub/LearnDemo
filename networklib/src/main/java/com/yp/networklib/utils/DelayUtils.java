package com.yp.networklib.utils;

import android.os.Handler;

/**
 * @author senrsl
 * @ClassName: DelayUtils
 * @Package: com.piaoshen.common.utils
 * @CreateTime: 2020/4/17 6:42 PM
 */
public class DelayUtils {

    private long delay = 1000l;

    private boolean isFlag = true;

    public DelayUtils() {
    }

    public DelayUtils(long delay) {
        this.delay = delay;
    }

    public synchronized boolean getFlag() {
        if (!isFlag) return false;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isFlag = true;
            }
        }, delay);
        isFlag = false;

        return true;
    }

    public boolean isFlag() {
        return isFlag;
    }
}
