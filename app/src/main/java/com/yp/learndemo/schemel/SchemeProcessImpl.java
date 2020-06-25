package com.yp.learndemo.schemel;

import android.net.Uri;

import com.yp.baseframworklib.scheme.ISchemeProcess;

import static com.yp.learndemo.schemel.SchemeContext.HOST;
import static com.yp.learndemo.schemel.SchemeContext.KEY_ACTION;
import static com.yp.learndemo.schemel.SchemeContext.SCHEMA;

/**
 * @author : yanpu
 * @date : 2020-06-25
 * @description:
 */
public class SchemeProcessImpl implements ISchemeProcess {




    @Override
    public void process(Uri uri) {
//        if (!JyDataContext.isInitThrid) {
//            Jumper.startWelcome();
//
//            new Handler().postDelayed(() -> SchemeInstance.getInstance().handle(uri), 2500);
//            return;
//        }

        //网址
//        if (NetUtils.isUrl(uri.toString())) {
//            Jumper.startBrowser(uri.toString());
//            return;
//        }

        if (SCHEMA.equals(uri.getScheme()) && HOST.equals(uri.getHost())) {
            String target = uri.getQueryParameter(KEY_ACTION);
            process(target, uri);
        }

        //未知处理

    }


    private void process(String target, Uri uri) {
        // 具体页面跳转
        switch (target){

        }

    }


}
