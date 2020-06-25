package com.yp.baseframworklib.log;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author : yanpu
 * @date : 2020-06-23
 * @description:
 */
public class LoggerSave extends BaseLoggerSave {

    public LoggerSave(Context ctx, String logPath, String logName) {
        super(ctx, logPath, logName);
    }

    public void save(String str) throws IOException {
        if (null == this.filepath) {
            this.init();
        }

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(this.filepath, true);
            fos.write(String.format("%s\n", str).getBytes());
        } finally {
            if (null != fos) {
                fos.close();
            }

        }

    }
}
