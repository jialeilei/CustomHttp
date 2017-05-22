package com.http.lei.practiceframework.http.origin;

import com.http.lei.practiceframework.http.HttpStatus;
import com.http.lei.practiceframework.http.header.HttpHeader;
import com.http.lei.practiceframework.http.response.AbstractHttpResponse;

import java.net.URLConnection;

/**
 * Created by lei on 2017/5/22.
 */
public class OriginHttpResponse extends AbstractHttpResponse{

    private URLConnection mConnection;

    public OriginHttpResponse(URLConnection connection){
        mConnection = connection;
    }


    @Override
    public HttpHeader getHeader() {
        return null;
    }

    @Override
    public String getStatusMsg() {
        return null;
    }

    @Override
    public HttpStatus getStatus() {
        return null;
    }

    @Override
    public long getContentLength() {
        return 0;
    }
}
