package com.http.lei.customframwork.http.utills;

/**
 * Created by lei on 2017/5/16.
 */
public class Utills {

    public static boolean isExist(String className,ClassLoader loader){
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
