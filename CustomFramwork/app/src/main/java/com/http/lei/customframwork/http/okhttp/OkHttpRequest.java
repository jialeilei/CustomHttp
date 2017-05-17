package com.http.lei.customframwork.http.okhttp;


import com.http.lei.customframwork.http.HttpMethod;
import com.http.lei.customframwork.http.header.HttpHeader;
import com.http.lei.customframwork.http.request.BufferHttpRequest;
import com.http.lei.customframwork.http.response.HttpResponse;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lei on 2017/5/11.
 */
public class OkHttpRequest extends BufferHttpRequest {

    private OkHttpClient mClient;
    private HttpMethod mMethod;
    private String mUrl;


    private OkHttpRequest(OkHttpRequest.Builder builder){
        this.mClient = builder.client;
        this.mMethod = builder.method;
        this.mUrl = builder.url;
    }

    public static class Builder{
        private OkHttpClient client;
        private HttpMethod method;
        private String url;

        public OkHttpRequest build(){
            return new OkHttpRequest(this);
        }

        public OkHttpRequest.Builder client(OkHttpClient client) {
            this.client = client;
            return this;
        }

        public OkHttpRequest.Builder method(HttpMethod method) {
            this.method = method;
            return this;
        }

        public OkHttpRequest.Builder url(String url) {
            this.url = url;
            return this;
        }
    }

    @Override
    protected HttpResponse executeInternal(HttpHeader header, byte[] data) throws IOException {

        boolean isBody = (mMethod == HttpMethod.POST);
        RequestBody requestBody = null;
        if (isBody){
            requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),data);
        }
        Request.Builder builder = new Request.Builder().url(mUrl).method(mMethod.name(),requestBody);

        for (Map.Entry<String,String> entry : header.entrySet()){
            builder.addHeader(entry.getKey(),entry.getValue());
        }

        Response response = mClient.newCall(builder.build()).execute();

        return new OkHttpResponse(response);
    }

    @Override
    public HttpMethod getMethod() {
        return mMethod;
    }

    @Override
    public URI getUri() {
        return URI.create(mUrl);
    }

}
