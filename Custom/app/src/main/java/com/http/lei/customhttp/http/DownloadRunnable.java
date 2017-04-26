package com.http.lei.customhttp.http;


import com.http.lei.customhttp.file.FileStorageManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import okhttp3.Response;


/**
 * Created by lei on 2017/4/26.
 */
public class DownloadRunnable implements Runnable {

    private long mStart;

    private long mEnd;

    private String mUrl;

    private DownloadCallback mCallback;

    public DownloadRunnable(long start,long end,String mUrl,DownloadCallback mCallback){
        this.mStart = start;
        this.mEnd = end;
        this.mUrl = mUrl;
        this.mCallback = mCallback;
    }

    @Override
    public void run() {
        Response response = HttpManager.getInstance().syncRequestByRange(mUrl, mStart, mEnd);
        if (response == null && mCallback != null){
            mCallback.fail(HttpManager.NETWORK_ERROR_CODE,"network is error");
            return;
        }

        File file = FileStorageManager.getInstance().getFileByName(mUrl);

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rwd");
            try {
                randomAccessFile.seek(mStart);
            } catch (IOException e) {
                e.printStackTrace();
            }

            byte[] buffer = new byte[1024*500];//500kb
            int len;

            InputStream inputStream = response.body().byteStream();
            try {
                while ((len = inputStream.read(buffer,0,buffer.length)) != -1){

                    randomAccessFile.write(buffer, 0, len);
                }
                mCallback.success(file);
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
