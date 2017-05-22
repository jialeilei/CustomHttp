package com.http.lei.practiceframework.http.response;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lei on 2017/5/22.
 */
public abstract class AbstractHttpResponse implements HttpResponse {

    @Override
    public void close() throws IOException {

    }

    @Override
    public InputStream getBody() throws IOException {
        return null;
    }
}
