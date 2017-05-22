package com.http.lei.customframwork.http.origin;


import com.http.lei.customframwork.http.HttpMethod;
import com.http.lei.customframwork.http.header.HttpHeader;
import com.http.lei.customframwork.http.request.BufferHttpRequest;
import com.http.lei.customframwork.http.response.HttpResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Map;


/**
 * Created by lei on 2017/5/16.
 */
public class OriginHttpRequest extends BufferHttpRequest {


    private HttpURLConnection mConnection;
    private HttpMethod mMethod;
    private String mUrl;


    public OriginHttpRequest(HttpURLConnection connection,HttpMethod method,String url){
        this.mConnection = connection;
        this.mMethod = method;
        this.mUrl = url;
    }

    @Override
    protected HttpResponse executeInternal(HttpHeader header, byte[] data) throws IOException {
        for (Map.Entry<String, String> entry : header.entrySet()) {
            mConnection.addRequestProperty(entry.getKey(),entry.getValue());
        }
        mConnection.setDoOutput(true);
        mConnection.setDoInput(true);
        mConnection.setRequestMethod(mMethod.name());
        mConnection.connect();
        if (data!= null && data.length > 0){
            OutputStream stream = mConnection.getOutputStream();
            stream.write(data,0,data.length);
            stream.close();
        }
        OriginHttpResponse response = new OriginHttpResponse(mConnection);

        return response;
    }

    @Override
    public HttpMethod getMethod() {
        return null;
    }

    @Override
    public URI getUri() {
        return null;
    }
}
