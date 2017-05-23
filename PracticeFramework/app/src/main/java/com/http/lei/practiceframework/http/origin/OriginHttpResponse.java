package com.http.lei.practiceframework.http.origin;


import com.http.lei.practiceframework.http.HttpStatus;
import com.http.lei.practiceframework.http.header.HttpHeader;
import com.http.lei.practiceframework.http.response.AbstractHttpResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;


/**
 * Created by lei on 2017/5/22.
 */
public class OriginHttpResponse extends AbstractHttpResponse{

    private HttpURLConnection mConnection;

    public OriginHttpResponse(HttpURLConnection connection){
        mConnection = connection;
    }

    @Override
    protected InputStream getBodyInternal() throws IOException{
        return mConnection.getInputStream();
    }

    @Override
    protected void closeInternal() {
        mConnection.disconnect();
    }

    @Override
    public HttpHeader getHeader() {
        HttpHeader header = new HttpHeader();
        for (Map.Entry<String, List<String>> entry : mConnection.getHeaderFields().entrySet()) {
            header.set(entry.getKey(),entry.getValue().get(0));
        }
        return header;
    }

    @Override
    public String getStatusMsg() {
        try {
            return mConnection.getResponseMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HttpStatus getStatus() {
        try {
            return HttpStatus.getValue(mConnection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getContentLength() {
        return mConnection.getContentLength();
    }


}
