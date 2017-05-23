package com.http.lei.customframwork.http.origin;


import com.http.lei.customframwork.http.HttpMethod;
import com.http.lei.customframwork.http.HttpRequestFactory;
import com.http.lei.customframwork.http.request.HttpRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;


/**
 * Created by lei on 2017/5/16.
 */
public class OriginHttpRequestFactory implements HttpRequestFactory {


    private HttpURLConnection mConnection;

    public OriginHttpRequestFactory(){

    }

    public void setReadTimeOut(int timeOut){
        mConnection.setReadTimeout(timeOut);
    }

    public void setConnectionTimeOut(int timeOut){
        mConnection.setConnectTimeout(timeOut);
    }


    @Override
    public HttpRequest createHttpRequest(URI uri, HttpMethod method) throws IOException{
        mConnection = (HttpURLConnection) uri.toURL().openConnection();
        return new OriginHttpRequest(mConnection,method,uri.toString());
    }


}
