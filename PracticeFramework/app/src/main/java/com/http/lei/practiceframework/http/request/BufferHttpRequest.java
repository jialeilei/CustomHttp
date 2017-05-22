package com.http.lei.practiceframework.http.request;


import com.http.lei.practiceframework.http.header.HttpHeader;
import com.http.lei.practiceframework.http.response.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by lei on 2017/5/19.
 */
public abstract class BufferHttpRequest extends AbstractHttpRequest {

    ByteArrayOutputStream stream = new ByteArrayOutputStream();

    @Override
    protected HttpResponse executeInternal(HttpHeader header) throws IOException {
        byte[] data = stream.toByteArray();

        return executeInternal(header,data);
    }

    protected abstract HttpResponse executeInternal(HttpHeader header, byte[] data) throws IOException;

    @Override
    protected OutputStream getBodyOutputStream() {
        return stream;
    }
}
