package com.http.lei.customhttp.application;

import com.http.lei.customhttp.file.FileStorageManager;
import com.http.lei.customhttp.http.DownloadManager;
import com.http.lei.customhttp.http.HttpManager;

/**
 * Created by lei on 2017/4/26.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FileStorageManager.getInstance().init(this);
        HttpManager.getInstance().init(this);
        //DownloadManager.getInstance().init(this);
    }
}
