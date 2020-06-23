package com.yp.networklib.code;

/**
 * @author : yanpu
 * @date : 2020-06-23
 * @description:
 */
public class NetRetCode extends BridgeRetcode {
    public static final int FAIL_NET = 3200;
    public static final int FAIL_NET_UNKNOWN_HOST = 3201;
    public static final int FAIL_NET_TIMEOUT_SOCKET = 3202;
    public static final int FAIL_NET_CONNECTION = 3203;
    public static final int FAIL_NET_RUNTIME = 3204;
    public static final int FAIL_NET_RESPONSE = 3205;
    public static final int FAIL_NET_RESPONSE_CHECK = 3305;
    public static final int ERROR_PROTOCOL = 4000;
    public static final int ERROR_SERVER = 5100;


    //http status code
    public static final int HTTP_401 = 401;
    public static final int HTTP_200 = 200;
}
