//package com.yp.learndemo;
//
//import android.os.Environment;
//import android.os.StrictMode;
//
//import com.jydata.common.JyDataContext;
//import com.jydata.common.utils.FileUtils;
//import com.jydata.common.utils.StringUtils;
//import com.piaoshen.common.PiaosContext;
//import com.piaoshen.flavor.FlavorInfo;
//
//import org.json.JSONObject;
//
//import java.util.UUID;
//
//import dc.android.common.handler.CrashHandler;
//import dc.android.common.utils.GetDeviceInfoUtils;
//import dc.android.common.utils.SharePreferencesUtils;
//import dc.common.Logger;
//
//import static com.jydata.common.JyDataContext.DIR_STORAGE;
//import static com.jydata.common.JyDataContext.DIR_STORAGE_SD;
//import static com.jydata.common.JyDataContext.NAME_COMP;
//import static com.piaoshen.common.PiaosContext.KEY_UDID;
//import static com.piaoshen.common.PiaosContext.TIME_INSTALL;
//import static com.piaoshen.common.PiaosContext.UDID;
//import static com.piaoshen.common.PiaosContext.UDID_FORMAT;
//import static dc.android.common.BaseApplication.getContext;
//import static dc.android.common.CoreContext.URL_SPEC;
//
///**
// * @author senrsl
// * @ClassName: PrimaryInitTask
// * @Package: com.jydata.monitor
// * @CreateTime: 2019/9/6 3:54 PM
// */
//public class PrimaryInitTask implements IInitTask {
//
//    private String packageName, versionName, channel;
//    private int versionCode;
//    private boolean isDebug, isReport;
//
//    private PrimaryInitTask() {
//    }
//
//    public PrimaryInitTask(String packageName, String versionName, int versionCode, String channel, boolean isDebug, boolean isReport) {
//        this.packageName = packageName;
//        this.versionName = versionName;
//        this.versionCode = versionCode;
//        this.channel = channel;
//        this.isDebug = isDebug;
//        this.isReport = isReport;
//    }
//
//    @Override
//    public void initConfig() {
//        JyDataContext.PACKAGE_NAME = packageName;
//        JyDataContext.VERSION_NAME = versionName;
//        JyDataContext.VERSION_CODE = versionCode;
//        FlavorInfo.CHANNEL = channel;
//        JyDataContext.isDebug = isDebug;
//        JyDataContext.isReport = isReport;
//    }
//
//    @Override
//    public void initData() {
////        JyDataContext.isReport = JyDataContext.isDebug;
//        if (JyDataContext.isDebug) Logger.start(getContext());//日0,7
////        JyDataContext.isDebug = true;
//        Logger.setLog(JyDataContext.isDebug);
////		Logger.startCat(this);
//        Logger.w("POWER START");
//
//        initUdid();
//        initTimeInstall();
//        initCacheDir();
//
//        fixCamera();
//
//        if (JyDataContext.isReport) initEnv();
//        if (!JyDataContext.isDebug) CrashHandler.getInstance().init(getContext());
//    }
//
//    @Override
//    public void run() {
//
//    }
//
//
//    // 修复部分7.0系统拍照问题
//    private void fixCamera() {
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//        builder.detectFileUriExposure();
//    }
//
//    //初始设置缓存目录
//    private void initCacheDir() {
//        String cacheUri = FileUtils.getCacheDirectory(getContext());
//        DIR_STORAGE_SD = StringUtils.sub(Environment.getExternalStorageDirectory().getAbsolutePath(), URL_SPEC, NAME_COMP, URL_SPEC);
//        if (null != cacheUri)
//            DIR_STORAGE = StringUtils.sub(cacheUri, URL_SPEC);
//        else
//            DIR_STORAGE = DIR_STORAGE_SD;
//
//        Logger.w(DIR_STORAGE + "    " + DIR_STORAGE_SD);
//    }
//
//    private void initEnv() {
//        try {
//            SharePreferencesUtils sp = new SharePreferencesUtils(getContext());
//            JSONObject jo = new JSONObject(sp.getSharedPreferencesValue(PiaosContext.KEY_ENV, "{}"));
//            jo.put(FlavorInfo.FLAVOR, FlavorInfo.CHANNEL);
//            jo.put(UDID, TIME_INSTALL);
//            sp.saveSharedPreferencesValue(PiaosContext.KEY_ENV, jo.toString());
//        } catch (Exception e) {
//        }
//    }
//
//
//    private void initTimeInstall() {
//        TIME_INSTALL = new GetDeviceInfoUtils(getContext()).getInstallTime(packageName);
//    }
//
//    private void initUdid() {
//        SharePreferencesUtils sp = new SharePreferencesUtils(getContext());
//        UDID = sp.getSharedPreferencesValue(KEY_UDID, null);
//        if (StringUtils.isEmpty(UDID)) {
//            UDID = String.format(UDID_FORMAT, UUID.randomUUID().toString());
//            sp.saveSharedPreferencesValue(KEY_UDID, UDID);
//        }
//    }
//
//}
