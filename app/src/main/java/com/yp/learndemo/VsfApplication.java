package com.yp.learndemo;

import com.yp.baseframworklib.scheme.SchemeInstance;
import com.yp.learndemo.schemel.SchemeProcessImpl;
import com.yp.networklib.interceptor.PiaosTokenClient;
import com.yp.networklib.comment.RetrofitInstance;
import com.yp.networklib.context.NetContext;

/**
 * @author senrsl
 * @ClassName: VsfApplication
 * @Package: com.jydata.monitor
 * @CreateTime: 2019/9/6 5:56 PM
 */
public class VsfApplication extends PrimaryApplication {


    @Override
    protected void initData() {
        new InitTaskExecute(getInitTask()).execute();
        RetrofitInstance.getInstance().init(NetContext.DOMAIN_BASE, new PiaosTokenClient().getTokenClient());

        registerActivityLifecycleCallbacks(lifecycleCallbacks);
        SchemeInstance.getInstance().setSchemeProcess(new SchemeProcessImpl());
//        registerActivityLifecycleCallbacks(new StatActivityLifecycleCallbacks());

    }

    protected IInitTask[] getInitTask() {
        return new IInitTask[]{
//                new PrimaryInitTask(APPLICATION_ID, VERSION_NAME, VERSION_CODE, FLAVOR, DEBUG, !DEBUG),
                new PublisherInitTask(),
//                new ConfigInitTask()
        };
    }
}