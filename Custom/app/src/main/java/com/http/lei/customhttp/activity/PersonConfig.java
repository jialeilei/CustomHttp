package com.http.lei.customhttp.activity;


import com.http.lei.customhttp.http.DownloadConfig;

/**
 * Created by lei on 2017/5/9.
 */
public class PersonConfig {
    private int mAge;
    private String mName;

    private PersonConfig(PersonConfig.Builder builder){
        mAge = builder.age;
        mName = builder.name;
    }

    public int getmAge() {
        return mAge;
    }

    public String getmName() {
        return mName;
    }

    public static class Builder{
        private int age;
        private String name;

        public PersonConfig build(){
            return new PersonConfig(this);
        }

        public int getAge() {
            return age;
        }

        public PersonConfig.Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public String getName() {
            return name;
        }

        public PersonConfig.Builder setName(String name) {
            this.name = name;
            return this;
        }
    }

}
