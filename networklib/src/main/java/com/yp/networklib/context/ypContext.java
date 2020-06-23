package com.yp.networklib.context;

import android.content.Intent;

import com.yp.baseframworklib.utils.StringUtils;

/**
 * @author : yanpu
 * @date : 2020-06-23
 * @description:
 */
public class ypContext  extends BridgeContext {

    public static boolean isInitThrid = false;

    public static final String TRANS_HEAD_HTTPS_FIX = StringUtils.sub(TRANS_HEAD_HTTPS, URL_AVOW, URL_SPEC, URL_SPEC);
    public static final String TRANS_HEAD_HTTP_FIX = StringUtils.sub(TRANS_HEAD_HTTP, URL_AVOW, URL_SPEC, URL_SPEC);

    /**
     * scheme协议头
     */
    public static final String TRANS_HEAD_JYDATA = "jydata://";

    /**
     * 平台
     */
    public static final String PLATFROM = "android";

    /**
     * 应用包名 如：com.piaoshen.ticket
     */
    public static String PACKAGE_NAME;

    /**
     * 版本号 如：1.6.0
     */
    public static String VERSION_NAME;

    /**
     * versionCode 如：24
     */
    public static int VERSION_CODE;


    //传授格式关键字
    public static final String KEY_CODE = "errorCode";
    public static final String KEY_MESSAGE = "errorMsg";
    public static final String KEY_DATA = "data";

    /**
     * 用户唯一标识符
     * <p>
     * 格式： UDID_FORMAT
     * 存储： sp内 KEY_UDID
     * 示例： a-8b913775-e3d9-4844-9e50-12fd00df80cd
     */
    public static String UDID;

    public static final String KEY_UDID = "udid";
    public static final String UDID_FORMAT = "a-%s";

    /**
     * 应用安装时间戳
     */
    public static long TIME_INSTALL;

    public static String DOMAIN = "https://api.jydata.com/";
    public static String DOMAIN_H5 = "https://e.jydata.com/";

    /**
     * 启动模式
     */
    public static int FLAGS_LOGON = Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP;

    /**
     * 默认状态栏
     */
    public static boolean IS_BAR_DARK = false;
    public static boolean IS_BAR_SHOW = true;


    public static final String KEY_USER_ID = "usrId";

    public static final String KEY_TIMEOUT_CONNECT = "connectTimeout";
    public static final String KEY_TIMEOUT_READ = "readTimeout";
    public static final String KEY_TIMEOUT_WRITE = "writeTimeout";

    /**
     * 服务器时间补全
     */
    public static long TIME_SYNC_DIFF = 0;

    /**
     * 无权限 - 业务
     * 这个地方改走Jumper，不要直连
     */
    public static String CLS_ERR_PERMISSION_BUSINESS = null;
    public static String CLS_ERR_PERMISSION_BUSINESS_STOP = null;
}
