package com.yp.learndemo;

/**
 * @author : yanpu
 * @date : 2020-06-23
 * @description:
 */
public interface IInitTask {

    /**
     * 应用必须配置 初始化
     * 出现崩溃时，应用崩
     */
    void initConfig();

    /**
     * 数据初始化
     * 三方初始化
     */
    void initData();

    /**
     * 线程内执行
     */
    void run();
}
