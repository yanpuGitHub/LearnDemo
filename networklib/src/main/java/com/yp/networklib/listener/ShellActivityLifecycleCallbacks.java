package com.yp.networklib.listener;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.yp.networklib.log.Logger;

/**
 * @author : yanpu
 * @date : 2020-06-23
 * @description:
 */
public class ShellActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    private Activity lastActivity;

    public ShellActivityLifecycleCallbacks() {
    }

    public Activity getLastActivity() {
        return this.lastActivity;
    }

    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Logger.w(new Object[]{this.getClass().getSimpleName(), activity, savedInstanceState, this.lastActivity});
    }

    public void onActivityStarted(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
        this.lastActivity = activity;
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivityStopped(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    public void onActivityDestroyed(Activity activity) {
    }
}
