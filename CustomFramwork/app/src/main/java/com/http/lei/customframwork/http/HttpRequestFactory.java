package com.http.lei.customframwork.http;


import com.http.lei.customframwork.http.request.HttpRequest;
import java.io.IOException;
import java.net.URI;

/**
 * Created by lei on 2017/5/16.
 */
public interface HttpRequestFactory {

    HttpRequest createHttpRequest(URI uri,HttpMethod method) throws IOException;

}
