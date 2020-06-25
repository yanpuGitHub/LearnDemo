package com.yp.baseframworklib.log;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author : yanpu
 * @date : 2020-06-23
 * @description:
 */
public class BaseLoggerSave {

    protected Context ctx;
    protected final String DIR_NAME = "SENRSL";
    protected String filepath;
    protected String logPath;
    protected String logName;

    public BaseLoggerSave(Context ctx, String logPath, String logName) {
        this.ctx = ctx;
        this.logPath = logPath;
        this.logName = logName;
    }

    public void init() throws IOException {
        if (null == this.logPath || "".equals(this.logPath)) {
            this.getLogPath();
        }

        File dir = new File(this.logPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        if (null == this.logName || "".equals(this.logName)) {
            this.getLogName();
        }

        this.filepath = this.logPath + File.separator + this.logName;
        System.out.println("log save2 :" + this.filepath);
        File file = new File(this.filepath);
        if (!file.exists()) {
            file.createNewFile();
        }

    }

    private void getLogName() {
        this.logName = (new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.CHINA)).format(new Date());
    }

    private void getLogPath() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            this.logPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "SENRSL";
        } else {
            this.logPath = this.ctx.getFilesDir().getAbsolutePath() + File.separator + "SENRSL";
        }

    }
}
