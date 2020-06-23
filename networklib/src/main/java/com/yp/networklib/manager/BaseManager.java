package com.yp.networklib.manager;


import android.support.annotation.Nullable;

import com.yp.baseframworklib.utils.StringUtils;
import com.yp.networklib.bean.ExtDataBean;
import com.yp.networklib.comment.RetrofitInstance;

/**
 * @author : yanpu
 * @date : 2020-06-20
 * @description:
 */
public class BaseManager {

//    protected SharePreferencesUtils sp;


    protected void initSp() {
//        sp = new SharePreferencesUtils(BaseApplication.getContext());
    }


    protected <T> T getService(Class<T> cls) {
        return RetrofitInstance.getInstance().getRetrofit().create(cls);
    }

    protected String sub(String domain, String method) {
        return StringUtils.sub(domain, method);
    }


    public interface Callback<B> {

        void onSuss(@Nullable B bean, @Nullable ExtDataBean extBean);

        void onError(int code, String str, @Nullable ExtDataBean extBean);
    }

}
