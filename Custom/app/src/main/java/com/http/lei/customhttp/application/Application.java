package com.http.lei.customhttp.application;

import com.facebook.stetho.Stetho;
import com.http.lei.customhttp.activity.Person;
import com.http.lei.customhttp.activity.PersonConfig;
import com.http.lei.customhttp.file.FileStorageManager;
import com.http.lei.customhttp.http.DownloadConfig;
import com.http.lei.customhttp.http.DownloadManager;
import com.http.lei.customhttp.http.HttpManager;
import download.db.DownloadHelper;

/**
 * Created by lei on 2017/4/26.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FileStorageManager.getInstance().init(this);
        HttpManager.Holder.getInstance().init(this);

        DownloadConfig config = new DownloadConfig.Builder()
                .setCoreThreadSize(2)
                .setMaxThreadSize(2)
                .setLocalProgressThreadSize(1).build();
        DownloadManager.Holder.getInstance().init(config);

        DownloadHelper.getInstance().init(this);
        Stetho.initializeWithDefaults(this);


        PersonConfig config1 = new PersonConfig.Builder()
                .setAge(15)
                .setName("lei")
                .build();
        Person.getInstance().init(config1);

    }
}
