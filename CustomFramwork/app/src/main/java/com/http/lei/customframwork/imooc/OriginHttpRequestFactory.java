package com.http.lei.customframwork.imooc;

import com.http.lei.customframwork.imooc.http.HttpMethod;
import com.http.lei.customframwork.imooc.http.HttpRequest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

/**
 * @author nate
 */

public class OriginHttpRequestFactory implements HttpRequestFactory {

    private HttpURLConnection mConnection;

    public OriginHttpRequestFactory() {

    }

    public void setReadTimeOut(int readTimeOut) {
        mConnection.setReadTimeout(readTimeOut);
    }

    public void setConnectionTimeOut(int connectionTimeOut) {
        mConnection.setConnectTimeout(connectionTimeOut);
    }

    @Override
    public HttpRequest createHttpRequest(URI uri, HttpMethod method) throws IOException {
        mConnection = (HttpURLConnection) uri.toURL().openConnection();
        return new OriginHttpRequest(mConnection, method, uri.toString());
    }
}
