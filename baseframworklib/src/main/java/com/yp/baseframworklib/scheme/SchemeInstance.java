package com.yp.baseframworklib.scheme;

import android.net.Uri;

import com.yp.baseframworklib.log.Logger;
import com.yp.baseframworklib.utils.StringUtils;

/**
 * @author : yanpu
 * @date : 2020-06-25
 * @description:
 */
public class SchemeInstance {

    private static SchemeInstance instance = new SchemeInstance();

    private SchemeInstance() {
    }

    public static SchemeInstance getInstance() {
        return instance;
    }


    private ISchemeProcess schemeProcess;

    public ISchemeProcess getSchemeProcess() {
        return schemeProcess;
    }

    public void setSchemeProcess(ISchemeProcess schemeProcess) {
        this.schemeProcess = schemeProcess;
    }

    /**
     * 解析scheme协议调起activity
     * <p>
     * 请注意，此设计基于单Stack模式
     *
     * @param uri
     */
    public void handle(String uri) {
        if (StringUtils.isEmpty(uri)) return;
        handle(Uri.parse(uri));
    }

    public void handle(Uri uri) {
        if (null == uri || null == schemeProcess) return;

        Logger.w(getClass().getSimpleName(), uri, uri.getScheme(), uri.getHost(), uri.getPath(), uri.getQuery());

        try {

            //未知处理
            schemeProcess.process(uri);

        } catch (Exception e) {
            e.printStackTrace();
            Logger.w(e.getMessage());
            //CrashUtils.log(e);
        }
    }


}
