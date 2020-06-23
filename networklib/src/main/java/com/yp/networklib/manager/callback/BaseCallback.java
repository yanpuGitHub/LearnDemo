package com.yp.networklib.manager.callback;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yp.baseframworklib.utils.StringUtils;
import com.yp.networklib.application.BaseApplication;
import com.yp.networklib.R;
import com.yp.networklib.application.BridgeApplication;
import com.yp.baseframworklib.bean.BaseDataBean;
import com.yp.networklib.bean.ExtDataBean;
import com.yp.networklib.code.NetRetCode;
import com.yp.networklib.log.Logger;
import com.yp.networklib.manager.BaseManager;
import com.yp.networklib.utils.DelayUtils;
import com.yp.networklib.utils.TaskStartUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Response;

import static com.yp.baseframworklib.comment.Global.EMPTY;
import static com.yp.networklib.code.ypRetCode.ERR_PERMISSION_BUSINESS;
import static com.yp.networklib.code.ypRetCode.LOGON_OTHER;
import static com.yp.networklib.code.ypRetCode.LOGON_RE;
import static com.yp.networklib.code.ypRetCode.LOGON_TIMEOUT;
import static com.yp.networklib.code.ypRetCode.OPER_ERR;
import static com.yp.networklib.code.ypRetCode.OPER_SUSS;
import static com.yp.networklib.code.ypRetCode.UNAUTHORIZED;
import static com.yp.networklib.context.ypContext.CLS_ERR_PERMISSION_BUSINESS;
import static com.yp.networklib.context.ypContext.CLS_ERR_PERMISSION_BUSINESS_STOP;
import static com.yp.networklib.context.ypContext.KEY_CODE;
import static com.yp.networklib.context.ypContext.KEY_DATA;
import static com.yp.networklib.context.ypContext.KEY_MESSAGE;

/**
 * @author : yanpu
 * @date : 2020-06-23
 * @description:
 */
public class BaseCallback<T extends String, S extends BaseDataBean> extends BasicCallback<T, S> {

    public BaseCallback(BaseManager.Callback cb) {
        super(cb);
    }

    @Override
    public void onSuccess(T response, S bean) {
        Logger.w(bean);
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        loggerDetail(response);
        try {
            if (response.isSuccessful())
                convert(response);
//                if (response.body().getCode() == PiaosRetCode.OPER_SUSS)
//                    onSuss(response.body());
//                else if (response.body().getCode() == PiaosRetCode.OPER_ERR)
//                    if (null != response.body().getErr())
//                        cb.err(response.body().getErr().getErr_code(), response.body().getErr().getMessage());
//                    else cb.err(NetRetCode.ERROR_PROTOCOL, response.body().getMessage());
//                else
//                    cb.err(NetRetCode.ERROR_PROTOCOL, String.valueOf(response.body()));
            else
                switch (response.code()) {
                    case NetRetCode.HTTP_401:
                        break;
                    default:
                        cb.onError(NetRetCode.ERROR_SERVER, StringUtils.sub(Logger.DONT_SHOW, BaseApplication.getContext().getString(R.string.net_err_null), String.valueOf(response.code())), null);
                }
        } catch (Exception e) {
            e.printStackTrace();
            cb.onError(NetRetCode.FAIL_NET_RESPONSE, e.getMessage(), null);
        }
    }


    private void convert(Response<T> response) {

        JsonElement je = new JsonParser().parse(response.body());
        if (je.isJsonObject()) {

            JsonObject jo = je.getAsJsonObject();
            Logger.w(getClass().getName(), jo.get(KEY_CODE).getAsInt(), jo.get(KEY_MESSAGE), jo.get(KEY_DATA));

            int code = jo.get(KEY_CODE).getAsInt();
            String msg = null == jo.get(KEY_MESSAGE) || jo.get(KEY_MESSAGE).isJsonNull() ? EMPTY : jo.get(KEY_MESSAGE).getAsString();

            ExtDataBean extBean = new ExtDataBean(code, msg, String.valueOf(jo.get(KEY_DATA)));
            switch (code) {
                case OPER_SUSS:
                    Type type = ((ParameterizedType) cb.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
                    S bean = new Gson().fromJson(jo.get(KEY_DATA), type);

                    onSuccess(response.body(), bean);
                    cb.onSuccess(bean, extBean);
                    break;
                case OPER_ERR:
                    cb.onError(NetRetCode.FAIL_NET_RESPONSE_CHECK, StringUtils.sub(Logger.DONT_SHOW, msg), extBean);
                    break;
                case UNAUTHORIZED:
                case LOGON_TIMEOUT:
                case LOGON_OTHER:
                case LOGON_RE:
                    loginTimeout();
                    break;
                case ERR_PERMISSION_BUSINESS:
                    blockFollow(code, msg, extBean);
                    break;
                default:
                    cb.onError(code, msg, extBean);
                    break;
            }

        } else {
            cb.onError(NetRetCode.ERROR_PROTOCOL, String.valueOf(response.body()), null);
        }
    }

    private static DelayUtils delayUtils = new DelayUtils(3000l);

    private void blockFollow(int code, String msg, @Nullable ExtDataBean extBean) {
        Activity activity = BridgeApplication.getLastActivity();
        Logger.w(getClass().getSimpleName(), activity);//大写的bug
        if (null == activity) {
            cb.onError(code, msg, extBean);
            return;
        }
        if (null != CLS_ERR_PERMISSION_BUSINESS && null != CLS_ERR_PERMISSION_BUSINESS_STOP
                && CLS_ERR_PERMISSION_BUSINESS_STOP.indexOf(activity.getClass().getCanonicalName()) == -1 && delayUtils.getFlag()) {
            try {
                new TaskStartUtils().startFollowActivity(activity, Intent.FLAG_ACTIVITY_SINGLE_TOP, code, msg, Class.forName(CLS_ERR_PERMISSION_BUSINESS));
                activity.finish();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                cb.onError(code, StringUtils.sub(msg, e.getMessage()), extBean);
            }
        } else {
            cb.onError(code, msg, extBean);
        }
    }

    private void loginTimeout() {
        new TaskStartUtils().loginTimeout();
    }
}
