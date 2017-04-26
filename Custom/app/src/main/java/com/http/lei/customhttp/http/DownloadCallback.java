package com.http.lei.customhttp.http;

import java.io.File;

/**
 * Created by lei on 2017/4/26.
 */
public interface DownloadCallback {

    void success(File file);

    void fail(int errorCode,String errorMessage);

    void progress(int progress);

}
