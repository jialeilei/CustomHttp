package com.http.lei.customhttp.http;


import android.os.*;
import android.os.Process;

import com.http.lei.customhttp.file.FileStorageManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import download.db.DownloadEntity;
import download.db.DownloadHelper;
import okhttp3.Response;


/**
 * Created by lei on 2017/4/26.
 */
public class DownloadRunnable implements Runnable {

    private long mStart;

    private long mEnd;

    private String mUrl;

    private DownloadCallback mCallback;

    private DownloadEntity mEntity;

    public DownloadRunnable(long start,long end,String mUrl,DownloadCallback mCallback,DownloadEntity entity){
        this.mStart = start;
        this.mEnd = end;
        this.mUrl = mUrl;
        this.mCallback = mCallback;
        this.mEntity = entity;
    }

    public DownloadRunnable(long start,long end,String mUrl,DownloadCallback mCallback){
        this.mStart = start;
        this.mEnd = end;
        this.mUrl = mUrl;
        this.mCallback = mCallback;
    }


    @Override
    public void run() {

        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        Response response = HttpManager.Holder.getInstance().syncRequestByRange(mUrl, mStart, mEnd);
        if (response == null && mCallback != null){
            mCallback.fail(HttpManager.NETWORK_ERROR_CODE,"network is error");
            return;
        }

        File file = FileStorageManager.getInstance().getFileByName(mUrl);
        long progress = 0;

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rwd");
            randomAccessFile.seek(mStart);
            byte[] buffer = new byte[1024*500];//500kb
            int len;
            InputStream inputStream = response.body().byteStream();

            while ((len = inputStream.read(buffer,0,buffer.length)) != -1){

                randomAccessFile.write(buffer, 0, len);
                progress = progress + len;
                mEntity.setProgress_position(progress);

            }

            mCallback.success(file);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            DownloadHelper.getInstance().insert(mEntity);
        }
    }
}
