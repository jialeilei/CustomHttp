package com.http.lei.practiceframework.http;


import com.http.lei.practiceframework.http.Method;
import com.http.lei.practiceframework.http.request.HttpRequest;
import java.io.IOException;
import java.net.URI;

/**
 * Created by lei on 2017/5/19.
 */
public interface HttpRequestFactory {

    HttpRequest createHttpRequest(URI uri,Method method) throws IOException;
}
