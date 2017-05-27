package com.http.lei.customframwork.util;

import android.support.compat.BuildConfig;
import android.util.Log;

/**
 * Created by lei on 2017/5/25.
 */
public class LogTool {

    private static String className = "";
    private static String methodName = "";
    private static int lineNumber;

    public static void d(String message){
        if (isDebug()){
            return;
        }
        getMethodNames(new Throwable().getStackTrace());
        Log.d(className, createLog(message));
    }

    public static void i(String message){
        if (isDebug()){
            return;
        }
        getMethodNames(new Throwable().getStackTrace());
        Log.i(className, createLog(message));
    }

    public static void w(String message){
        if (isDebug()){
            return;
        }
        getMethodNames(new Throwable().getStackTrace());
        Log.w(className, createLog(message));
    }

    public static void e(String message){
        if (isDebug()){
            return;
        }
        getMethodNames(new Throwable().getStackTrace());
        Log.e(className, createLog(message));
    }


    private static boolean isDebug(){
        return BuildConfig.DEBUG;
    }

    private static void getMethodNames(StackTraceElement[] trace) {
        className = trace[1].getClassName();
        methodName = trace[1].getMethodName();
        lineNumber = trace[1].getLineNumber();
    }

    private static String createLog(String message) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(methodName).
                append("(").
                append(className).
                append(":").
                append(lineNumber).
                append(")").
                append(message);
        return buffer.toString();
    }

}
