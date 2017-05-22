package com.http.lei.practiceframework.http.origin;


import com.http.lei.practiceframework.http.Method;
import com.http.lei.practiceframework.http.request.BufferHttpRequest;
import java.net.HttpURLConnection;
import java.net.URI;

/**
 * Created by lei on 2017/5/19.
 */
public class OriginHttpRequest extends BufferHttpRequest {

    private String mUrl;
    private HttpURLConnection mConnection;
    private Method mMethod;


    public OriginHttpRequest(HttpURLConnection connection,Method method,String url){
        mConnection = connection;
        mMethod = method;
        mUrl = url;
    }



    @Override
    public Method getMethod() {
        return null;
    }

    @Override
    public URI getUri() {
        return null;
    }

}
