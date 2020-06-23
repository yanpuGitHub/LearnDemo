package com.yp.networklib.interceptor;


import com.yp.baseframworklib.utils.SharePreferencesUtils;
import com.yp.networklib.R;
import com.yp.networklib.application.BaseApplication;
import com.yp.networklib.utils.TaskUtils;

import static com.yp.baseframworklib.comment.Global.DEL;
import static com.yp.networklib.context.ypContext.KEY_USER_ID;

/**
 * @author senrsl
 * @ClassName: PiaosTokenInterceptor
 * @Package: com.piaoshen.common.net.interceptor
 * @CreateTime: 2019/8/5 5:29 PM
 */
public class PiaosTokenInterceptor extends TokenInterceptor {

    @Override
    protected void unauthorized() {
        super.unauthorized();

        new SharePreferencesUtils(BaseApplication.getContext()).saveSharedPreferencesValue(KEY_USER_ID, DEL);
//        new TokenManager().remove();
//        new TaskUtils().startLogin(BaseApplication.getContext(), FLAGS_LOGON, R.string.logon_timeout);
        new TaskUtils().startHome(BaseApplication.getContext(), R.string.logon_timeout);

    }
}
