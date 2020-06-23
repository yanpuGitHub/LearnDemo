package com.yp.networklib.manager.callback;

import com.yp.baseframworklib.utils.StringUtils;
import com.yp.networklib.application.BaseApplication;
import com.yp.networklib.R;
import com.yp.baseframworklib.bean.BaseBean;
import com.yp.networklib.code.NetRetCode;
import com.yp.networklib.context.ypContext;
import com.yp.networklib.log.Logger;
import com.yp.networklib.manager.BaseManager;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : yanpu
 * @date : 2020-06-23
 * @description:
 */
public abstract class BasicCallback<T extends Object, S extends BaseBean> implements Callback<T> {
    protected BaseManager.Callback cb;

    public BasicCallback(BaseManager.Callback cb) {
        this.cb = cb;
    }

    protected void loggerDetail(Response response) {
        if (ypContext.isDebug){
            Logger.w(getClass(), response, response.isSuccessful(), response.body(), cb);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
//        Logger.w(t.toString());

        try {
            if (t instanceof UnknownHostException) {
                cb.onError(NetRetCode.FAIL_NET_UNKNOWN_HOST, StringUtils.sub(Logger.DONT_SHOW, BaseApplication.getContext().getString(R.string.net_err_unknowhost)), null);
            } else if (t instanceof SocketTimeoutException) {
                cb.onError(NetRetCode.FAIL_NET_TIMEOUT_SOCKET, StringUtils.sub(Logger.DONT_SHOW, BaseApplication.getContext().getString(R.string.net_err_timeout)), null);
            } else if (t instanceof ConnectException) {
                cb.onError(NetRetCode.FAIL_NET_CONNECTION, StringUtils.sub(Logger.DONT_SHOW, BaseApplication.getContext().getString(R.string.net_err_connect)), null);
            } else if (t instanceof RuntimeException) {
                cb.onError(NetRetCode.FAIL_NET_RUNTIME, StringUtils.sub(Logger.DONT_SHOW, BaseApplication.getContext().getString(R.string.net_err_runtime)), null);
            } else {
                cb.onError(NetRetCode.FAIL_NET, StringUtils.sub(Logger.DONT_SHOW, BaseApplication.getContext().getString(R.string.net_err_null), t.getMessage()), null);
            }

//            if (PiaosContext.isReport) robustCache(call);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


//    private void robustCache(Call<T> call) {
//        Request request = call.request();
//        Charset charset = Charset.forName(CandyContext.ENCODE_UTF_8);
//
//        String key = CacheManager.getInstance().getKey(request, charset);
//        String cache = CacheManager.getInstance().getCache(key);
//        Logger.w(cache, getClass().getGenericSuperclass());
//        Logger.w(getClass(), call.getClass(), call.getClass().getGenericSuperclass(), call.getClass().getGenericInterfaces()[0], getClass().getGenericSuperclass(), getClass().getGenericInterfaces());
//
//        Class<T> cls = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//
//        if (null != cache && cache.trim().length() > 0)
//            onSuss(JsonInstance.getInstance().parse(cache, cls));
//
//    }


    public abstract void onSuccess(T response, S bean);
}
