package com.http.lei.customhttp.utils;

import android.util.Log;
import java.util.Locale;

/**
 * Created by lei on 2017/4/26.
 * 日志工具
 */
public class Logger {

    public static boolean DEBUG = true;

    public static void i(String tag,String msg){
        if (DEBUG){
            Log.i(tag,msg);
        }
    }

    public static void d(String tag,String msg){
        if (DEBUG){
            Log.d(tag, msg);
        }
    }

    /**
     * @param tag
     * @param msg
     * @param args
     */
    public static void d(String tag,String msg,Object... args){
        if (DEBUG){
            Log.d(tag,String.format(Locale.getDefault(),msg,args));
        }
    }

    public static void w(String tag,String msg){
        if (DEBUG){
            Log.w(tag, msg);
        }
    }

    public static void e(String tag,String msg){
        if (DEBUG){
            Log.e(tag,msg);
        }
    }

}
