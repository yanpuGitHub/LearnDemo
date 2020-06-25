package com.yp.networklib.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.yp.networklib.context.BridgeContext;
import com.yp.baseframworklib.log.Logger;

/**
 * @author : yanpu
 * @date : 2020-06-23
 * @description:
 */
public class TaskUtils {

    private static Class getClass(String path) {
        try {
            if (null != path && path.trim().length() > 0) return Class.forName(path);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void startHome(Context ctx, int reason) {
        startSingleActivity(ctx, startSingleFlags, reason, getClass(BridgeContext.CLS_HOME));
    }

    public void startHome(Context ctx, int flags, int reason) {
        startSingleActivity(ctx, flags, reason, getClass(BridgeContext.CLS_HOME));
    }

    public void startLogin(Context ctx, int reason) {
        startSingleActivity(ctx, startSingleFlags, reason, getClass(BridgeContext.CLS_LOGIN));
    }

    public void startLogin(Context ctx, int flags, int reason) {
        startSingleActivity(ctx, flags, reason, getClass(BridgeContext.CLS_LOGIN));
    }

    public void startWelcome(Context ctx, int reason) {
        startSingleActivity(ctx, startSingleFlags, reason, getClass(BridgeContext.CLS_WELCOME));
    }

    private int startSingleFlags = Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP;

    public void startSingleActivity(Context ctx, int flags, int reason, Class cls) {
        startSingleActivity(ctx, flags, reason, BridgeContext.EMPTY, cls);
    }

    public void startSingleActivity(Context ctx, int flags, int reason, String bridge, Class cls) {
        if (null == cls) return;
        Intent intent = new Intent();
        intent.setClass(ctx, cls);
        intent.putExtra(BridgeContext.KEY_LOGOUT, reason);
        intent.putExtra(BridgeContext.KEY_VAR_BRIDGE, bridge);
        intent.addFlags(flags);
        ctx.startActivity(intent);
    }

    public void startLauncher(Context ctx) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        ctx.startActivity(intent);
    }

    public void exit(Context ctx) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
        killProcess();
    }

    public static void restart(Context mContext) {
        try {
            Thread.sleep(1000); // 1秒后重启，可有可无，仅凭个人喜好
//                Intent intent = new Intent(mContext, getTopActivity());
            Intent intent = new Intent(mContext, getClass(BridgeContext.CLS_WELCOME));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        } catch (InterruptedException e) {
            Logger.w(e.getMessage());
        }
        killProcess();
    }

    // 退出程序
    public static void killProcess() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


    /**
     * 只需配置transitionName
     * 与其他模式冲突
     *
     * @param view
     * @param activity
     * @param intent
     */
    public void startExplodeActivity(View view, Activity activity, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Pair squareParticipant = new Pair<>(view, ViewCompat.getTransitionName(view));
            ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, squareParticipant);
            activity.startActivity(intent, transitionActivityOptions.toBundle());
        } else {
            activity.startActivity(intent);
        }
    }

    public void startExplodeActivity(View view, Activity activity, Intent intent, String url) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, url);
            activity.startActivity(intent, transitionActivityOptions.toBundle());
        } else {
            activity.startActivity(intent);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.DONUT)
    public void startMarket(Context ctx, String marketName) throws ActivityNotFoundException {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(String.format(BridgeContext.URI_MARKET, ctx.getPackageName()));//app包名
        intent.setData(uri);
        intent.setPackage(marketName);//应用市场包名
        ctx.startActivity(intent);
        Logger.w(getClass().getSimpleName(), ctx, marketName, uri);
    }
}
