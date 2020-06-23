package com.yp.learndemo;


/**
 * @author senrsl
 * @ClassName: InitTaskExecute
 * @Package: com.piaoshen.bridge.task
 * @CreateTime: 2019/9/6 3:21 PM
 */
public class InitTaskExecute {

    private IInitTask[] arrTask;

    public InitTaskExecute(IInitTask[] tasks) {
        arrTask = tasks;
    }

    public void execute() {
        if (null == arrTask) return;

        initConfig();
        initData();
        initRun();
    }

    private void initConfig() {
        for (IInitTask task : arrTask) {
            task.initConfig();
        }
    }

    private void initData() {
        for (IInitTask task : arrTask) {
            task.initData();
        }
    }

    private void initRun() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (IInitTask task : arrTask) task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
