package com.http.lei.customframwork.imooc;



import com.http.lei.customframwork.imooc.http.HttpMethod;
import com.http.lei.customframwork.imooc.http.HttpRequest;

import java.io.IOException;
import java.net.URI;

/**
 * @author nate
 */

public interface HttpRequestFactory {

    HttpRequest createHttpRequest(URI uri, HttpMethod method) throws IOException;
}
