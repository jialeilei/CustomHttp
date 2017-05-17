package com.http.lei.customframwork.http.okhttp;


import com.http.lei.customframwork.http.HttpMethod;
import com.http.lei.customframwork.http.HttpRequestFactory;
import com.http.lei.customframwork.http.request.HttpRequest;
import java.net.URI;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;

/**
 * Created by lei on 2017/5/16.
 */
public class OkHttpRequestFactory implements HttpRequestFactory {

    private OkHttpClient mClient;

    public OkHttpRequestFactory(){
        this.mClient = new OkHttpClient();
    }

    private void setReadTimeOut(long readTimeOut){
        this.mClient.newBuilder().
                readTimeout(readTimeOut, TimeUnit.MILLISECONDS).
                build();
    }

    public void setWriteTiemOut(long writeTimeOut){
        this.mClient.newBuilder().
                writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS).
                build();
    }

    public void setConnectionTimeOut(int timeOut){
        this.mClient.newBuilder().
                connectTimeout(timeOut, TimeUnit.MILLISECONDS).
                build();
    }

    @Override
    public HttpRequest createHttpRequest(URI uri, HttpMethod method) {
        return new OkHttpRequest.Builder().url(uri.toString()).method(method).client(mClient).build();
    }

}
