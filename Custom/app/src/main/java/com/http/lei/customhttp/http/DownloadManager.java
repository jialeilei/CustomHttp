package com.http.lei.customhttp.http;

import android.content.Context;
import android.util.Log;
import com.http.lei.customhttp.file.FileStorageManager;
import com.http.lei.customhttp.utils.Logger;
import download.DownloadTask;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import download.db.DownloadEntity;
import download.db.DownloadHelper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 下载线程管理类
 * Created by lei on 2017/4/26.
 */
public class DownloadManager {

    private Context mContext;

    public static DownloadManager sManager;

    private HashSet<DownloadTask> mHashSet = new HashSet<>();

    private List<DownloadEntity> mCache;

    public final static int CORE_POOL_SIZE = 2;

    public final static int MAX_POOL_SIZE = 2;

    public final static int PROGRESS_POOL_SIZE = 1;

    private long mLength;

    private static final String TAG = "DownloadManager";

    private static ExecutorService sLocalProgressPool;

    private static ThreadPoolExecutor sThreadPool;


    public void init(DownloadConfig config){

        sLocalProgressPool = Executors.newFixedThreadPool(config.getLocalProgressThreadSize());
        sThreadPool = new ThreadPoolExecutor(config.getCoreThreadSize(),config.getMaxThreadSize(),60, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>(), new ThreadFactory(){

            private AtomicInteger mInteger = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r,"download thread #" + mInteger.getAndIncrement());
                Log.i(TAG,"thread name: "+thread.getName());
                return thread;
            }
        });
    }

    /**
     * 单例模式 1
     * double check,会发生原子性错误
     */
    /*public static DownloadManager getInstance(){

        if (sManager == null){
            synchronized (DownloadManager.class){
                if (sManager == null){
                    sManager = new DownloadManager();//(java重排序)1分配内存   2、调用其构造方法进行初始化  3、引用赋值，指向内存分配区域
                }
            }
            return sManager;
        }
        return sManager;
    }*/

    /**
     * 单例模式 2
     * 静态内部类，起到延时加载作用，创建的原子性
     */
    public static class Holder{

        private static DownloadManager sManager = new DownloadManager();

        public static DownloadManager getInstance(){
            return sManager;
        }

    }

    private DownloadManager(){}

    /**
     * 移除任务
     * @param task
     */
    private void finish(DownloadTask task){
        mHashSet.remove(task);
    }

    public void download(final String url, final DownloadCallback callback){

        final DownloadTask task = new DownloadTask(url,callback);
        if (mHashSet.contains(task)){
            callback.fail(HttpManager.TASK_RUNNING_ERROR,"任务已经在下载队列中");
            return;
        }

        mHashSet.add(task);
        mCache = DownloadHelper.getInstance().getAll(url);

        if (mCache == null || mCache.size() == 0){
            Logger.i("Logger","处理未下载过的数据");
            HttpManager.Holder.getInstance().asyncRequest(url,new Callback(){
                @Override
                public void onFailure(Call call, IOException e) {

                    finish(task);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (!response.isSuccessful() && callback != null){
                        callback.fail(HttpManager.NETWORK_ERROR_CODE," network is error ");
                        return;
                    }

                    mLength = response.body().contentLength();
                    if (mLength == -1){
                        callback.fail(HttpManager.CONTENT_LENGTH_ERROR_CODE," content length is -1 ");
                        return;
                    }

                    processDownload(url,mLength,callback,mCache);
                    finish(task);//伪代码

                }
            });
        }else {
            // TODO: 2017/5/8 处理已经下载过的数据
            Logger.i("Logger","处理已经下载过的数据");
            for (int i = 0; i < mCache.size(); i++) {
                DownloadEntity entity = mCache.get(i);
                if (i == mCache.size() - 1){
                    mLength = entity.getEnd_position() + 1;
                }
                long startLength = entity.getProgress_position()+entity.getStart_position();
                sThreadPool.execute(new DownloadRunnable(startLength,entity.getEnd_position(),url,callback,entity));
            }

        }


        sLocalProgressPool.execute(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(300);
                        File file = FileStorageManager.getInstance().getFileByName(url);
                        long fileSize = file.length();

                        int downloadProgress = (int) (fileSize * 100.0 / mLength);
                        //11872444
                        Logger.i("Logger","mLength: " + mLength + " fileSize: " + fileSize + " progress: "+downloadProgress);

                        if (downloadProgress >= 100 && mLength > 0){
                            callback.progress(downloadProgress);
                            return;
                        }
                        callback.progress(downloadProgress);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }




    private void processDownload(String url,long length,DownloadCallback callback,List<DownloadEntity> entities) {

        Logger.i("Logger","mLength: "+length);

        long threadDownloadSize = length / MAX_POOL_SIZE;
        if (entities == null || entities.size() == 0){
            mCache = new ArrayList<>();
        }

        for (int i = 0; i < MAX_POOL_SIZE; i++) {
            DownloadEntity entity = new DownloadEntity();
            long startSize = i * threadDownloadSize;
            long endSize;
            if (i == MAX_POOL_SIZE - 1){
                endSize = length - 1;
            }else {
                endSize = (i + 1) * threadDownloadSize - 1;
            }

            entity.setDownload_url(url);
            entity.setStart_position(startSize);
            entity.setEnd_position(endSize);
            entity.setThread_id(i+1);

            sThreadPool.execute(new DownloadRunnable(startSize,endSize,url,callback,entity));
        }
    }

}
