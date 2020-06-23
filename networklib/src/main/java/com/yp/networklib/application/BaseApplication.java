package com.yp.networklib.application;

import android.app.Application;
import android.content.Context;

/**
 * @author : yanpu
 * @date : 2020-06-20
 * @description:
 */
public class BaseApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext(){
        return context;
    }
}
