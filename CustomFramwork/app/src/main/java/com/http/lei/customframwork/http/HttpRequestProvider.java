package com.http.lei.customframwork.http;


import com.http.lei.customframwork.http.okhttp.OkHttpRequestFactory;
import com.http.lei.customframwork.http.origin.OriginHttpRequestFactory;
import com.http.lei.customframwork.http.request.HttpRequest;
import com.http.lei.customframwork.http.utills.Utills;
import java.io.IOException;
import java.net.URI;

/**
 * Created by lei on 2017/5/16.
 */
public class HttpRequestProvider  {

    private HttpRequestFactory mHttpRequestFactory;
    private static boolean OK_HTTP_REQUEST = Utills.isExist("okhttp3.Request",HttpRequestProvider.class.getClassLoader());

    public HttpRequestProvider(){

        mHttpRequestFactory = new OkHttpRequestFactory();

        /*if (OK_HTTP_REQUEST){
            mHttpRequestFactory = new OkHttpRequestFactory();
        }else {
            mHttpRequestFactory = new OriginHttpRequestFactory();
        }*/
    }

    public HttpRequest getHttpRequest(URI uri,HttpMethod method) throws IOException{
        return mHttpRequestFactory.createHttpRequest(uri, method);
    }



    public HttpRequestFactory getHttpRequestFactory() {
        return mHttpRequestFactory;
    }

    public void setHttpRequestFactory(HttpRequestFactory mHttpRequestFactory) {
        this.mHttpRequestFactory = mHttpRequestFactory;
    }

}
