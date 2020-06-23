package com.yp.baseframworklib.utils;

/**
 * @author : yanpu
 * @date : 2020-06-20
 * @description:
 */
public class StringUtils {

    public static boolean isEmpty(String str) {
        return null == str || str.trim().length() == 0;
    }

    public static int toInteger(String str){
        int i = -1;
        try {
            i = Integer.parseInt(str);
        }catch (Exception e){
            e.printStackTrace();
        }
        return i;
    }

    public static long toLong(String str) {
        long i = -1;
        try {
            i = Long.parseLong(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return i;
    }


    public static String sub(Object... strArr) {
        StringBuffer sb = new StringBuffer();
        for (Object str : strArr) {
            sb.append(str);
        }
        return sb.toString();
    }

}
