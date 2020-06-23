package com.yp.networklib.context;

import com.yp.baseframworklib.CoreContext;

/**
 * @author : yanpu
 * @date : 2020-06-23
 * @description:
 */
public class BridgeContext extends CoreContext {

    public static final String KEY_LOGOUT = "logout";//标注退出原因

    public static String CLS_WELCOME = null;
    public static String CLS_LOGIN = null;
    public static String CLS_HOME = null;

    //三方通信开关
    public static boolean isReport;

    public static final String URI_MARKET = "market://details?id=%s";


    public static final String KEY_EB_ID = "id";
    public static final String KEY_VAR_1 = "var1";
    public static final String KEY_VAR_2 = "var2";
    public static final String KEY_VAR_3 = "var3";
    public static final String KEY_VAR_BRIDGE = "bridge";


    public static int DESIGN_WIDTH = 375;
    public static int DESIGN_HEIGHT = 667;

    //崩溃
    public static final String KEY_CRASH = "isCrash";
    public static String KEY_ENV = "env";
    public static int CRASH_INFO_MAX = 1024;

    public static String LINE = "\n";
    public static String TAB = "\t";

}
