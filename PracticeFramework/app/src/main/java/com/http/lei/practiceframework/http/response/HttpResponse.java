package com.http.lei.practiceframework.http.response;



import com.http.lei.practiceframework.http.HttpStatus;
import com.http.lei.practiceframework.http.header.Header;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lei on 2017/5/22.
 */
public interface HttpResponse extends Closeable,Header {


    InputStream getBody() throws IOException;

    void close() throws IOException;





    String getStatusMsg();

    HttpStatus getStatus();

    long getContentLength();

}
