package com.http.lei.practiceframework.http;

import com.http.lei.practiceframework.http.provider.HttpRequestProvider;
import com.http.lei.practiceframework.http.request.HttpRequest;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lei on 2017/5/19.
 */
public class WorkStation {

    private static final int CORE_POOL_SIZE = 1;
    private static final int MAXI_MUM_POOL_SIZE = 6;
    private static final int MAX_REQUEST_SIZE = 20;
    private HttpRequestProvider httpRequestProvider;
    private Deque<Request> mRunning = new ArrayDeque<>();
    private Deque<Request> mCache = new ArrayDeque<>();

    public WorkStation(){
        httpRequestProvider = new HttpRequestProvider();
    }

    private ThreadPoolExecutor sThreadPool =
            new ThreadPoolExecutor(CORE_POOL_SIZE, MAXI_MUM_POOL_SIZE, 60, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
        AtomicInteger integer = new AtomicInteger();
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread();
            thread.setName("Framework "+integer.getAndIncrement());
            return thread;
        }
    });

    public void add(Request request){
        if (mRunning.size() > MAX_REQUEST_SIZE){
            mCache.add(request);
        }else {
            doHttpRequest(request);
        }
    }

    private void doHttpRequest(Request request) {
        HttpRequest httpRequest = null;
        try {
            httpRequest = httpRequestProvider.getHttpRequest(URI.create(request.getUrl()),request.getMethod());
        } catch (IOException e) {
            e.printStackTrace();
        }
        sThreadPool.execute(new FrameworkRunnable(request, httpRequest, this));
    }

    public void finish(Request request){
        mRunning.remove(request);
        if (mRunning.size() > MAX_REQUEST_SIZE){
            return;
        }
        if (mCache.size() == 0){
            return;
        }
        Iterator<Request> iterator = mCache.iterator();
        while (iterator.hasNext()){
            Request next = iterator.next();
            mRunning.add(next);
            iterator.remove();
            doHttpRequest(next);
        }
    }


}
