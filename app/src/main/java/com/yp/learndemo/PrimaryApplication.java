package com.yp.learndemo;

import com.yp.networklib.application.ShellApplication;
import com.yp.networklib.listener.ShellActivityLifecycleCallbacks;
import com.yp.baseframworklib.log.Logger;


/**
 * @author senrsl
 * @ClassName: PrimaryApplication
 * @Package: com.jydata.monitor
 * @CreateTime: 2019/8/26 7:44 PM
 */
public class PrimaryApplication extends ShellApplication {


    public PrimaryApplication() {
        lifecycleCallbacks = new ShellActivityLifecycleCallbacks();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        try {
//            String currentProcessName = ProcessUtils.getProcessName(this, android.os.Process.myPid());
//            if (!getPackageName().equals(currentProcessName)) return;

            initData();
        } catch (Exception e) {
            Logger.w(e.getMessage());
            e.printStackTrace();
        }
    }


    protected void initData() {

    }
}
