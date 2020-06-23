package com.yp.networklib.context;

import com.yp.networklib.context.BridgeContext;

/**
 * @author : yanpu
 * @date : 2020-06-23
 * @description:
 */
public class NetContext extends BridgeContext {

    public static long TIMEOUT_CONNECT = 10;
    public static long TIMEOUT_READ = 15;
    public static long TIMEOUT_WRITE = 30;

    public static final String DOMAIN_BASE = "https://www.android.com/";

    public static final String HEADER_COOKIE_SET = "Set-Cookie";

    public static final String KEY_COOKIE = "cookie";
    public static final String NAME_PREFERCE = "net";
}
