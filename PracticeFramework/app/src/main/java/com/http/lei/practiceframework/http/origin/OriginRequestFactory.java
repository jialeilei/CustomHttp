package com.http.lei.practiceframework.http.origin;


import com.http.lei.practiceframework.http.HttpRequestFactory;
import com.http.lei.practiceframework.http.Method;
import com.http.lei.practiceframework.http.request.HttpRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

/**
 * Created by lei on 2017/5/19.
 */
public class OriginRequestFactory implements HttpRequestFactory{

    private HttpURLConnection connection;

    @Override
    public HttpRequest createHttpRequest(URI uri, Method method) throws IOException {
        connection = (HttpURLConnection) uri.toURL().openConnection();
        return new OriginHttpRequest(connection,method,uri.toString());
    }

    public void setReadTimeout(int timeout){
        connection.setReadTimeout(timeout);
    }

    public void setConnectionTimeout(int timeout){
        connection.setConnectTimeout(timeout);
    }

}
