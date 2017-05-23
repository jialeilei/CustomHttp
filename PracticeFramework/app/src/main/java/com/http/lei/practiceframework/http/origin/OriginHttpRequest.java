package com.http.lei.practiceframework.http.origin;

import com.http.lei.practiceframework.http.Method;
import com.http.lei.practiceframework.http.header.HttpHeader;
import com.http.lei.practiceframework.http.request.BufferHttpRequest;
import com.http.lei.practiceframework.http.response.HttpResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Map;

/**
 * Created by lei on 2017/5/19.
 */
public class OriginHttpRequest extends BufferHttpRequest {

    private String mUri;
    private HttpURLConnection mConnection;
    private Method mMethod;

    public OriginHttpRequest(HttpURLConnection connection,Method method,String uri){
        mConnection = connection;
        mMethod = method;
        mUri = uri;
    }

    @Override
    protected HttpResponse executeInternal(HttpHeader header, byte[] data) throws IOException{
        for (Map.Entry<String, String> entry : header.entrySet()) {
            mConnection.addRequestProperty(entry.getKey(),entry.getValue());
        }
        mConnection.setDoOutput(true);
        mConnection.setDoInput(true);
        mConnection.setRequestMethod(mMethod.name());
        mConnection.connect();
        if (data != null && data.length > 0){
            OutputStream stream = mConnection.getOutputStream();
            stream.write(data,0,data.length);
            stream.close();
        }

        OriginHttpResponse response = new OriginHttpResponse(mConnection);
        return response;
    }


    @Override
    public Method getMethod() {
        return mMethod;
    }


    @Override
    public URI getUri() {
        return URI.create(mUri);
    }

}
