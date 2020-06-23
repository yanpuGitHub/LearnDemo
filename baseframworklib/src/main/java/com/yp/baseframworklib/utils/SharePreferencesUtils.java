package com.yp.baseframworklib.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.yp.baseframworklib.CoreContext;
import com.yp.baseframworklib.comment.Global;

/**
 * @author : yanpu
 * @date : 2020-06-23
 * @description:
 */
public class SharePreferencesUtils {
    private Context mContext;

    private static final String SP_NAME = CoreContext.DC_SERVICE_DATAMANAGER_SP;

    public SharePreferencesUtils(Context mContext) {
        super();
        this.mContext = mContext;
    }

    public String getSharedPreferencesValue(String key, String defValue) {
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    public Long getSharedPreferencesValue(String key, long defValue) {
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getLong(key, defValue);
    }

    public int getSharedPreferencesValue(String key, int defValue) {
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    public boolean getSharedPreferencesValue(String key) {
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    /**
     *
     * @param key  值为Global.DEL时删除,否则存储
     * @param value
     * @return
     */
    public boolean saveSharedPreferencesValue(String key, String value) {
        // Logger.w(
        // mContext.getBasePackageName()+"----"+mContext.getOpPackageName()+"----"+mContext.getPackageCodePath()+"----"+mContext.getPackageResourcePath());
        SharedPreferences preferences = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (Global.DEL.equals(value))
            editor.remove(key);
        else
            editor.putString(key, value);
        return editor.commit();
    }

    public boolean saveSharedPreferencesValue(String key, boolean value) {
        // Logger.w(
        // mContext.getBasePackageName()+"----"+mContext.getOpPackageName()+"----"+mContext.getPackageCodePath()+"----"+mContext.getPackageResourcePath());
        SharedPreferences preferences = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (Global.DEL.equals(value))
            editor.remove(key);
        else
            editor.putBoolean(key, value);
        return editor.commit();
    }

    public boolean saveSharedPreferencesValue(String key, int value) {
        SharedPreferences preferences = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public boolean saveSharedPreferencesValue(String key, long value) {
        SharedPreferences preferences = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        return editor.commit();
    }
}
