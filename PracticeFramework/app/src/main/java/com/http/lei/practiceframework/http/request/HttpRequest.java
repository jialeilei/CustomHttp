package com.http.lei.practiceframework.http.request;

import com.http.lei.practiceframework.http.Method;
import com.http.lei.practiceframework.http.response.HttpResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

/**
 * Created by lei on 2017/5/19.
 */
public interface HttpRequest {

    Method getMethod();

    OutputStream getBody();

    URI getUri();

    HttpResponse execute() throws IOException;

}
