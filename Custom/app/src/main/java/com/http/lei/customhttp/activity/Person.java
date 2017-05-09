package com.http.lei.customhttp.activity;

/**
 * Created by lei on 2017/5/9.
 */
public class Person {

    private static int age;
    private static String name;

    private volatile static Person sPerson;//禁止虚拟机重新排序

    private Person(){}

    public static Person getInstance(){

        if (sPerson == null){
            synchronized (Person.class){
                if (sPerson == null){
                    sPerson = new Person();
                }
            }
            return sPerson;
        }
        return sPerson;
    }

    public void init(PersonConfig config){
        age = config.getmAge();
        name = config.getmName();
    }
}
