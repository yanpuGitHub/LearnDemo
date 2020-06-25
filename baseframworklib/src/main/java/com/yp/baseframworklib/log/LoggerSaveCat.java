package com.yp.baseframworklib.log;

import android.content.Context;
import android.os.Process;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author : yanpu
 * @date : 2020-06-23
 * @description:
 */
public class LoggerSaveCat extends BaseLoggerSave {
    private LoggerSaveCat.LoggerCatDumper lcd;

    public LoggerSaveCat(Context ctx, String logPath, String logName) {
        super(ctx, logPath, logName);
    }

    public void start() throws IOException {
        if (null == this.filepath) {
            this.init();
        }

        if (null == this.lcd) {
            this.lcd = new LoggerSaveCat.LoggerCatDumper(Process.myPid() + "");
        }

        this.lcd.start();
    }

    public void stop() {
        if (null != this.lcd) {
            this.lcd.stopLogs();
            this.lcd = null;
        }

    }

    private class LoggerCatDumper extends Thread {
        private java.lang.Process logcatProc;
        private BufferedReader mReader = null;
        private boolean mRunning = true;
        String cmds = null;
        private String mPID;
        private FileOutputStream fos = null;

        public LoggerCatDumper(String pid) {
            this.mPID = pid;

            try {
                this.fos = new FileOutputStream(LoggerSaveCat.this.filepath);
            } catch (FileNotFoundException var4) {
                var4.printStackTrace();
            }

            this.cmds = "logcat *:e *:i | grep \"(" + this.mPID + ")\"";
        }

        public void stopLogs() {
            this.mRunning = false;
        }

        public void run() {
            try {
                this.logcatProc = Runtime.getRuntime().exec(this.cmds);
                this.mReader = new BufferedReader(new InputStreamReader(this.logcatProc.getInputStream()), 1024);
                String line = null;

                while(this.mRunning && (line = this.mReader.readLine()) != null && this.mRunning) {
                    if (line.length() != 0 && this.fos != null && line.contains(this.mPID)) {
                        this.fos.write(((new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA)).format(new Date()) + "  " + line + "\n").getBytes());
                    }
                }
            } catch (IOException var14) {
                var14.printStackTrace();
            } finally {
                if (this.logcatProc != null) {
                    this.logcatProc.destroy();
                    this.logcatProc = null;
                }

                if (this.mReader != null) {
                    try {
                        this.mReader.close();
                        this.mReader = null;
                    } catch (IOException var13) {
                        var13.printStackTrace();
                    }
                }

                if (this.fos != null) {
                    try {
                        this.fos.close();
                    } catch (IOException var12) {
                        var12.printStackTrace();
                    }

                    this.fos = null;
                }

            }

        }
    }
}
