package com.yp.networklib.context;

import com.yp.baseframworklib.CoreContext;

/**
 * @author : yanpu
 * @date : 2020-06-23
 * @description:
 */
public class ShellContext extends CoreContext {
    public static final String CLS_XPOSED = "de.robv.android.xposed.XposedBridge";
    public static final String XPOSED_DIS = "disableHooks";

    public ShellContext() {
    }
}
