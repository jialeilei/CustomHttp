package http.response;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

import http.HttpStatus;
import http.header.Header;

/**
 * Created by lei on 2017/5/11.
 */
public interface HttpResponse extends Header,Closeable {

    HttpStatus getStatus();

    String getStatusMsg();

    InputStream getBody() throws IOException;

    void close() throws IOException;




}
