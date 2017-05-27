package com.http.lei.customframwork.http.service;


import com.http.lei.customframwork.http.HttpRequestProvider;
import com.http.lei.customframwork.http.request.HttpRequest;
import com.http.lei.customframwork.imooc.service.HttpRunnable;
import com.http.lei.customframwork.imooc.service.MoocRequest;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lei on 2017/5/16.
 */
public class WorkStation {

    private static final int MAX_REQUEST_SIZE = 60;
    private static final int MAXI_MUM_POOL_SIZE = 5;
    private static final int CORE_POOL_SIZE = 1;

    private Deque<FrameworkRequest> mRunning = new ArrayDeque<>();
    private Deque<FrameworkRequest> mCache = new ArrayDeque<>();
    private HttpRequestProvider mProvider;

    private static final ThreadPoolExecutor sThreadPool =
            new ThreadPoolExecutor(CORE_POOL_SIZE, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>(), new ThreadFactory() {
        private AtomicInteger integer = new AtomicInteger();
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("thread name "+integer.getAndIncrement());
            return thread;
        }
    });

   /* private static final ThreadPoolExecutor sThreadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory() {

        private AtomicInteger index = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("http thread name is " + index.getAndIncrement());
            return thread;
        }
    });*/

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
        System.out.println("doHttpRequest:" + 2);
        try {
            System.out.println("doHttpRequest:" + 3);
            httpRequest = mProvider.getHttpRequest(URI.create(request.getUrl()),request.getMethod());
            System.out.print("1: uri:"+request.getUrl()+" method:"+request.getMethod());
            System.out.print("2: uri:" + httpRequest.getUri() + " method:" + httpRequest.getMethod());
        } catch (IOException e) {
            System.out.println("doHttpRequest:" + 4);
            e.printStackTrace();
        }
        sThreadPool.execute(new FrameworkRunnable(httpRequest,request,this));
      /*  com.http.lei.customframwork.imooc.http.HttpRequest httpRequest2 = null;
        MoocRequest moocRequest = null;
        com.http.lei.customframwork.imooc.service.WorkStation moocWorkstation = new com.http.lei.customframwork.imooc.service.WorkStation();
        sThreadPool.execute(new HttpRunnable(httpRequest2, moocRequest, moocWorkstation));*/
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
