package com.yp.learndemo;


/**
 * @author senrsl
 * @ClassName: PublisherInitTask
 * @Package: com.jydata.monitor
 * @CreateTime: 2019/9/6 8:35 PM
 */
public class PublisherInitTask implements IInitTask {

    @Override
    public void initConfig() {
//        JyDataContext.CLS_WELCOME = WelcomeActivity.class.getCanonicalName();
//        JyDataContext.CLS_LOGIN = LogonPublisherActivity.class.getCanonicalName();
//        JyDataContext.CLS_HOME = CombinationActivity.class.getCanonicalName();
//
//        SchemeContext.SCHEMA = PublisherContext.SCHEME_PUBLISHER;
//        SchemeInstance.getInstance().setSchemeProcess(new SchemeProcessImpl());
    }

    @Override
    public void initData() {
//        BrowserContext.BROWSER_AGENT = BrowserUtils.generateAgent(PublisherContext.BROWSER_AGENT);

//        UserContext.URL_PASSWD_RESET = UserContext.URL_PASSWD_RESET_PUBLISHER;
//        ConfigContext.URL_H5_AGREEMENT = ConfigContext.URL_H5_AGREEMENT_PUBLISHER;
//
//        PublisherContext.FLAGS_LOGON = Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP;
//
//        PiaosContext.IS_BAR_DARK = true;
//
//        PermissionInstance.getInstance().savePermissionLast(PermissionContext.PERMISSSION_PUBLISHER);
//
//        PublisherContext.KEY_STAT_TALKINGDATA = PublisherContext.KEY_STAT_TALKINGDATA_PUBLISHER;
//
//        Jumper.setTarget(new TargetImpl());
//
//        RetrofitInstance.getInstance().init(NetContext.DOMAIN_BASE, new PiaosTokenClient().getTokenClient());
        //从这里向下初始化其他
//        JPushUtils.setEnable(JyDataContext.isReport);
//        TalkingDataUtils.setEnable(JyDataContext.isReport);

    }

    @Override
    public void run() {

    }

}
