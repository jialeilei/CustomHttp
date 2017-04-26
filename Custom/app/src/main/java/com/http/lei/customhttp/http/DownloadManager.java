package com.http.lei.customhttp.http;

import android.content.Context;
import java.io.IOException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 线程管理类
 * Created by lei on 2017/4/26.
 */
public class DownloadManager {

    private Context mContext;

    public static DownloadManager sManager;

    private final static int CORE_POOL_SIZE = 2;

    private final static int MAX_POOL_SIZE = 2;

    private final static ThreadPoolExecutor sThreadPool = new ThreadPoolExecutor(CORE_POOL_SIZE,MAX_POOL_SIZE,60, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory(){

                private AtomicInteger mInteger = new AtomicInteger(1);
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r,"download thread #" + mInteger.getAndIncrement());
                    return thread;
                }
            });

    public static DownloadManager getInstance(){
         return sManager = new DownloadManager();
    }

    private DownloadManager(){}


    public void download(final String url, final DownloadCallback callback){
        HttpManager.getInstance().asyncRequest(url,new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (!response.isSuccessful() && callback != null){
                    callback.fail(HttpManager.NETWORK_ERROR_CODE," network is error ");
                    return;
                }

                long length = response.body().contentLength();
                if (length == -1){
                    callback.fail(HttpManager.CONTENT_LENGTH_ERROR_CODE," content length IS -1 ");
                    return;
                }

                processDownload(url,length,callback);

            }
        });
    }

    private void processDownload(String url,long length,DownloadCallback callback) {

        long threadDownloadSize = length / MAX_POOL_SIZE;
        for (int i = 0; i < MAX_POOL_SIZE; i++) {
            long startSize = i * threadDownloadSize;
            long endSize = (i + 1) * threadDownloadSize - 1;
            sThreadPool.execute(new DownloadRunnable(startSize,endSize,url,callback));
        }
    }

}
