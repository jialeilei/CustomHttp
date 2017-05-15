package com.http.lei.customframwork.http.request;


import com.http.lei.customframwork.http.HttpMethod;
import com.http.lei.customframwork.http.response.HttpResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;



/**
 * Created by lei on 2017/5/11.
 */
public interface HttpRequest {

    HttpMethod getMethod();

    URI getUri();

    OutputStream getBody();

    HttpResponse execute() throws IOException;

}
