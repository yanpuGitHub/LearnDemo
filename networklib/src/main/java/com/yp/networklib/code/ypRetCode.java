package com.yp.networklib.code;

/**
 * @author : yanpu
 * @date : 2020-06-23
 * @description:
 */
public class ypRetCode extends BridgeRetcode {

    public static final int OPER_SUSS = 0;   //API Suss
    public static final int OPER_ERR = 1;
    public static final int UNAUTHORIZED = -1;  //未鉴权


    //需要重新登录
    public static final int LOGON_TIMEOUT = -10000;
    public static final int LOGON_OTHER = -10001;
    public static final int LOGON_RE = -10002;

    //无权限-业务
    public static final int ERR_PERMISSION_BUSINESS = 10403;
}
