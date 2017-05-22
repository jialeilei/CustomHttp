package com.http.lei.customframwork.http.response;

import com.http.lei.customframwork.http.HttpStatus;
import com.http.lei.customframwork.http.header.Header;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lei on 2017/5/11.
 */
public interface HttpResponse extends Header,Closeable {

    InputStream getBody() throws IOException;

    void close() throws IOException;



    HttpStatus getStatus();

    String getStatusMsg();

    long getContentLength();

}
