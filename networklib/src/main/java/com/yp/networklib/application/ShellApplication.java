package com.yp.networklib.application;

import android.app.Activity;

import com.yp.networklib.context.ShellContext;
import com.yp.networklib.listener.ShellActivityLifecycleCallbacks;

import java.lang.reflect.Field;

/**
 * @author : yanpu
 * @date : 2020-06-23
 * @description:
 */
public class ShellApplication extends BaseApplication {
    protected static ShellActivityLifecycleCallbacks lifecycleCallbacks;

    public ShellApplication() {
    }

    public static Activity getLastActivity() {
        return lifecycleCallbacks.getLastActivity();
    }

    public void onCreate() {
        super.onCreate();
        if (!ShellContext.isDebug) {
            this.antiXposedInject();
        }

    }

    private void antiXposedInject() {
        try {
            Field xpdisableHooks = ClassLoader.getSystemClassLoader().loadClass("de.robv.android.xposed.XposedBridge").getDeclaredField("disableHooks");
            xpdisableHooks.setAccessible(true);
            xpdisableHooks.set((Object) null, Boolean.TRUE);
        } catch (NoSuchFieldException var3) {
        } catch (ClassNotFoundException var4) {
        } catch (IllegalAccessException var5) {
            System.exit(1);
        }

    }
}