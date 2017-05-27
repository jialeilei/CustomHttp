package com.http.lei.customframwork.imooc;

import com.http.lei.customframwork.imooc.http.HttpMethod;
import com.http.lei.customframwork.imooc.http.HttpRequest;
import com.http.lei.customframwork.imooc.utils.Utills;

import java.io.IOException;
import java.net.URI;

/**
 * @author nate
 */

public class HttpRequestProvider {

    private static boolean OKHTTP_REQUEST = Utills.isExist("okhttp3.OkHttpClient", HttpRequestProvider.class.getClassLoader());

    private HttpRequestFactory mHttpRequestFactory;

    public HttpRequestProvider() {
        if (OKHTTP_REQUEST) {
            mHttpRequestFactory = new OkHttpRequestFactory();
        } else {
            mHttpRequestFactory = new OriginHttpRequestFactory();
        }
    }

    public HttpRequest getHttpRequest(URI uri, HttpMethod httpMethod) throws IOException {
        return mHttpRequestFactory.createHttpRequest(uri, httpMethod);
    }

    public HttpRequestFactory getHttpRequestFactory() {
        return mHttpRequestFactory;
    }

    public void setHttpRequestFactory(HttpRequestFactory httpRequestFactory) {
        mHttpRequestFactory = httpRequestFactory;
    }
}
