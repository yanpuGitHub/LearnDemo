package com.yp.networklib.utils;

import android.content.Context;
import android.content.Intent;

import com.yp.baseframworklib.utils.SharePreferencesUtils;
import com.yp.networklib.R;
import com.yp.networklib.application.BaseApplication;

import static com.yp.baseframworklib.comment.Global.DEL;
import static com.yp.networklib.context.BridgeContext.KEY_VAR_1;
import static com.yp.networklib.context.BridgeContext.KEY_VAR_2;
import static com.yp.networklib.context.ypContext.FLAGS_LOGON;
import static com.yp.networklib.context.ypContext.KEY_USER_ID;

/**
 * @author : yanpu
 * @date : 2020-06-23
 * @description:
 */
public class TaskStartUtils extends TaskUtils {

    public void startFollowActivity(Context ctx, int flags, int code, String msg, Class cls) {
        if (null == cls) return;
        Intent intent = new Intent();
        intent.setClass(ctx, cls);
        intent.putExtra(KEY_VAR_1, code);
        intent.putExtra(KEY_VAR_2, msg);
        intent.addFlags(flags);
        ctx.startActivity(intent);
    }


    public void loginTimeout() {
        new SharePreferencesUtils(BaseApplication.getContext()).saveSharedPreferencesValue(KEY_USER_ID, DEL);
        startHome(BaseApplication.getContext(), FLAGS_LOGON, R.string.logon_timeout);
    }
}
