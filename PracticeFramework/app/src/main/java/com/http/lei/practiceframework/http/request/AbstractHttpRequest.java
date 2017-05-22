package com.http.lei.practiceframework.http.request;


import com.http.lei.practiceframework.http.header.HttpHeader;
import com.http.lei.practiceframework.http.response.HttpResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by lei on 2017/5/22.
 */
public abstract class AbstractHttpRequest implements HttpRequest {


    private HttpHeader mHeader = new HttpHeader();

    public HttpHeader getHeader(){
        return mHeader;
    }


    @Override
    public OutputStream getBody() {

        return null;
    }

    @Override
    public HttpResponse execute() throws IOException {
        return null;
    }

}
