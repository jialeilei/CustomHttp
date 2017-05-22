package com.http.lei.practiceframework.http.provider;


import com.http.lei.practiceframework.http.HttpRequestFactory;
import com.http.lei.practiceframework.http.Method;
import com.http.lei.practiceframework.http.origin.OriginRequestFactory;
import com.http.lei.practiceframework.http.request.HttpRequest;
import java.io.IOException;
import java.net.URI;

/**
 * 提供http请求
 * Created by lei on 2017/5/19.
 */
public class HttpRequestProvider {


    private HttpRequestFactory factory;

    public HttpRequestProvider(){
        factory = new OriginRequestFactory();
    }

    public HttpRequest getHttpRequest(URI uri,Method method) throws IOException {
        return factory.createHttpRequest(uri,method);
    }
}
