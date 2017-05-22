package com.http.lei.customframwork.http.service;


import com.http.lei.customframwork.http.HttpRequestProvider;
import com.http.lei.customframwork.http.request.HttpRequest;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by lei on 2017/5/16.
 */
public class WorkStation {

    private static final int MAX_REQUEST_SIZE = 20;
    private static final int MAXI_MUM_POOL_SIZE = 5;
    private static final int CORE_POOL_SIZE = 0;

    private static final ThreadPoolExecutor sThreadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXI_MUM_POOL_SIZE, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(), new ThreadFactory() {

        private AtomicInteger integer = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {

            Thread thread = new Thread();
            thread.setName("thread name "+integer.getAndIncrement());
            return thread;

        }
    });

    private Deque<FrameworkRequest> mRunning = new ArrayDeque<>();

    private Deque<FrameworkRequest> mCache = new ArrayDeque<>();

    private HttpRequestProvider mProvider;

    public WorkStation(){
        mProvider = new HttpRequestProvider();
    }

    public void add(FrameworkRequest request){
        if (mRunning.size() > MAX_REQUEST_SIZE){
            mCache.add(request);
        }else {
            doHttpRequest(request);
        }
    }

    private void doHttpRequest(FrameworkRequest request){
        HttpRequest httpRequest = null;//uri、method
        try {
            httpRequest = mProvider.getHttpRequest(URI.create(request.getUrl()),request.getMethod());
            sThreadPool.execute(new FrameworkRunnable(httpRequest,request,this));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void finish(FrameworkRequest request){
        mRunning.remove(request);
        if (mRunning.size() > MAX_REQUEST_SIZE){
            return;
        }

        if (mCache.size() == 0){
            return;
        }

        Iterator<FrameworkRequest> iterator = mCache.iterator();
        while (iterator.hasNext()){
            FrameworkRequest next = iterator.next();
            mRunning.add(next);
            iterator.remove();
            doHttpRequest(next);
        }
    }

}
