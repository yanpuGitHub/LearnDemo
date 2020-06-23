package com.yp.networklib.manager.callback;



import com.yp.networklib.bean.BaseBean;
import com.yp.networklib.code.NetRetCode;
import com.yp.networklib.manager.BaseManager;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @author senrsl
 * @ClassName: BaseRawCallback
 * @Package: com.piaoshen.ticket.manager.callback
 * @CreateTime: 2018/5/29 下午7:49
 */
public class BaseRawCallback<T extends ResponseBody, S extends BaseBean> extends BasicCallback<T, S> {

    public BaseRawCallback(BaseManager.Callback cb) {
        super(cb);
    }

    @Override
    public void onSuccess(T response, S bean) {
        cb.onSuccess(response, null);
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        loggerDetail(response);
        if (response.isSuccessful())
            onSuccess(response.body(), null);
        else
            cb.onError(NetRetCode.ERROR_SERVER, response.message(), null);
    }

}
